import matplotlib.pyplot as plt
from pydub import AudioSegment
from python_speech_features import mfcc
from python_speech_features import logfbank
from python_speech_features import delta
import soundfile as sf
from sklearn import preprocessing
import librosa
import numpy as np
import sys
import csv
import DBConnection as db

# Class that generates a set of training data from raw audio samples
class TrainingDataGenerator:
	# Instance variables
	classifications = ""

	# Constructor
	def __init__(self, classifications):
		self.classifications = classifications

	# Function that determines the classifications for audio samples
	def getClassifierArray(self):
		 count = 0
		 arr = []
		 with open(self.classifications) as cl:
			 for line in cl:
				 line = line.rstrip()
				 arr.append(line)
				 count = count + 1
		 return arr

	# Function to perform various audio analysises on audio file to get classifying features for decision tree
	# @Param audio   .mp3 audio file
	def getFeatures(self, audio):
		# extract signal and frame rate from audio sample
		sound = AudioSegment.from_mp3(audio)
		sig = np.frombuffer(sound.raw_data, dtype=np.int16)
		rate = sound.frame_rate

		# perform analysis on audio segment
		mfccs = mfcc(sig,rate,winlen=0.025,winstep=0.01,numcep=13, nfilt=26,nfft=1106,lowfreq=0,highfreq=None,preemph=0.97, ceplifter=22,appendEnergy=True)
		chroma_stft = librosa.feature.chroma_stft(y=sig.astype("float32"), sr=rate)
		spec_cent = librosa.feature.spectral_centroid(y=sig.astype("float32"), sr=rate)
		spec_bw = librosa.feature.spectral_bandwidth(y=sig.astype("float32"), sr=rate)
		rolloff = librosa.feature.spectral_rolloff(y=sig.astype("float32"), sr=rate)
		zcr = librosa.feature.zero_crossing_rate(y=sig.astype("float32"))

		# return list of extracted features
		return mfccs.mean(axis=1), np.mean(chroma_stft), np.mean(spec_cent), np.mean(spec_bw), np.mean(rolloff), np.mean(zcr)

	# Function used to import dataset into DB
	# @Param el    feature classifier
	# @Param mfccs    mean array of mfcc features
	# @Param chroma_stft   mean chroma short-time fourier transform value
	# @Param spec_cent   mean spectral centroid value
	# @Param spec_bw    mean spectral bandwidth value
	# @Param rolloff    mean spectral rolloff value
	# @Param zcr     mean zero crossing rate
	# @Param db_connection   MySQL database connection object
	def importData(self, el, mfccs, chroma_stft, spec_cent, spec_bw, rolloff, zcr, db_connection):
		cursor = db_connection.cursor()
		sql_stmt = "INSERT INTO test_table(`sound`, `mfcc 1`, `mfcc 2`, `mfcc 3`, `mfcc 4`, `mfcc 5`, `mfcc 6`, `mfcc 7`, `mfcc 8`, `mfcc 9`, `mfcc 10`, `mfcc 11`, `mfcc 12`, `chroma_stft`,`spec_cent`, `spec_bw`, `rolloff`, `zcr`) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"
		values = (int(el), '{:0.4f}'.format(mfccs[0]), '{:0.4f}'.format(mfccs[1]), '{:0.4f}'.format(mfccs[2]), '{:0.4f}'.format(mfccs[3]), '{:0.4f}'.format(mfccs[4]), '{:0.4f}'.format(mfccs[5]),
		'{:0.4f}'.format(mfccs[6]), '{:0.4f}'.format(mfccs[7]), '{:0.4f}'.format(mfccs[8]), '{:0.4f}'.format(mfccs[9]), '{:0.4f}'.format(mfccs[10]), '{:0.4f}'.format(mfccs[11]), '{:0.4f}'.format(chroma_stft), '{:0.4f}'.format(spec_cent), '{:0.4f}'.format(spec_bw), '{:0.4f}'.format(rolloff), '{:0.4f}'.format(zcr))
		cursor.execute(sql_stmt, values)
		db_connection.commit()

	# Function that converts training data from MySQL database to a .csv file
	def exportTrainingData(self):
		db_connection = db.connectDB()
		cursor = db_connection.cursor()
		sql_stmt = "SELECT * FROM test_table"
		cursor.execute(sql_stmt)
		res = cursor.fetchall()
		c = csv.writer(open('training_data.csv', 'w'))
		header = ["sound", "mfcc 1",  "mfcc 2", "mfcc 3", "mfcc 4", "mfcc 5", "mfcc 6", "mfcc 7", "mfcc 8", "mfcc 9", "mfcc 10", "mfcc 11", "mfcc 12", "chroma_stft", "spec_cent", "spec_bw", "rolloff", "zcr", "ID"]
		c.writerow(header)
		for x in res:
			c.writerow(x)
		db_connection.close()

	# generate training data from a list of classifiers and their corresponding audio samples
	def genTrainingData(self):
		count = 0
		folder_no = 0
		arr = self.getClassifierArray()

		try:
			db_connection = db.connectDB()
		except:
			print("Connection Failed...")
			return -1

		for el in arr:
			if (el != "-"):
				mffcs, chroma_stft, spec_cent, spec_bw, rolloff, zcr = self.getFeatures("../girl_sing_sample/%s-%s/sample_%s.mp3" % (folder_no, folder_no + 49, str(count).zfill(3)))
				try:
					self.importData(el, mffcs, chroma_stft, spec_cent, spec_bw, rolloff, zcr, db_connection)
				except:
					continue
			count = count + 1
			if (count % 50 == 0):
				folder_no = folder_no + 50

		db_connection.close()



def main():
	TDG = TrainingDataGenerator('classifications.txt')
	#TDG.genTrainingData()
	#TDG.exportTrainingData()

# Calling function for Main
if __name__=="__main__":
	main()
