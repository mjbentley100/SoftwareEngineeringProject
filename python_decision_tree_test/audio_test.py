import matplotlib.pyplot as plt
from pydub import AudioSegment
import mysql.connector as sql
from python_speech_features import mfcc
from sklearn import preprocessing
import numpy as np

# Function for connecting to MySQL Database
def connectDB():
	db_connection = sql.connect(host='localhost', database='test_schema', user='test_user', password='test123')
	return db_connection

# Function used to import dataset into DB
def importdata():
	db_connection = connectDB()
	#cursor = db_connection.cursor()
	#sql_stmt = "INSERT INTO ___ VALUES ()"
	#values = ();
	#cursor.execute(sql_stmt, values)
	#db_connection.commit()
	db_connection.close();

# Function to perform MFCC analysis on audio file to get classifying features for decision tree
def preformMFCC(audio):
	sound = AudioSegment.from_mp3(audio)
	data = np.frombuffer(sound.raw_data, dtype=np.int16)
	mfccs = mfcc(data, sound.frame_rate, 0.025, 0.01, 20, nfft=1200, appendEnergy=True)

	#extract classifying features form mfcc
	mfcc_feature = preprocessing.scale(mfccs, axis=1);
	print(mfcc_feature.mean(axis=1))


# Main function
def main():
	preformMFCC('sample_000.mp3')

# Calling function for Main
if __name__=="__main__":
	main()
