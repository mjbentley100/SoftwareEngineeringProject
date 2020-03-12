import matplotlib.pyplot as plt
from pydub import AudioSegment
import mysql.connector as sql
from python_speech_features import mfcc
from sklearn import preprocessing
import numpy as np

#########################################################################
# TO DO:
#	1) Place methods inside of a single class. Make connectDB own class.
#	2) Document code && clean up
#########################################################################

# Function for connecting to MySQL Database
def connectDB():
	db_connection = sql.connect(host='localhost', database='test_schema', user='test_user', password='test123')
	return db_connection

def getClassifierArray(classifications):
	 count = 0
	 arr = []
	 with open(classifications) as cl:
		 for line in cl:
			 line = line.rstrip()
			 arr.append(line)
			 count = count + 1
	 return arr


# Function to perform MFCC analysis on audio file to get classifying features for decision tree
def performMFCC(audio):
	sound = AudioSegment.from_mp3(audio)
	data = np.frombuffer(sound.raw_data, dtype=np.int16)
	mfccs = mfcc(data, sound.frame_rate, 0.025, 0.01, 20, nfft=1200, appendEnergy=True)

	#extract classifying features form mfcc
	mfcc_feature = preprocessing.scale(mfccs, axis=1);
	return mfcc_feature.mean(axis=1)

# Function used to import dataset into DB
def importData(el, mfccs):
	db_connection = connectDB()
	cursor = db_connection.cursor()
	#sql_stmt = "INSERT INTO `test_schema`.`test_table`(`sound`) VALUES(`%d`);"
	#values = 1;
	if (len(mfccs) == 12):
		sql_stmt = "INSERT INTO test_table(`sound`, `mfcc 1`, `mfcc 2`, `mfcc 3`, `mfcc 4`, `mfcc 5`, `mfcc 6`, `mfcc 7`, `mfcc 8`, `mfcc 9`, `mfcc 10`, `mfcc 11`, `mfcc 12`) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"
		values = (int(el), '{:0.4f}'.format(mfccs[0] *  (10**17)), '{:0.4f}'.format(mfccs[1] *  (10**17)), '{:0.4f}'.format(mfccs[2] *  (10**17)), '{:0.4f}'.format(mfccs[3] *  (10**17)), '{:0.4f}'.format(mfccs[4] *  (10**17)), '{:0.4f}'.format(mfccs[5] *  (10**17)),
		'{:0.4f}'.format(mfccs[6] *  (10**17)), '{:0.4f}'.format(mfccs[7] *  (10**17)), '{:0.4f}'.format(mfccs[8] *  (10**17)), '{:0.4f}'.format(mfccs[9] *  (10**17)), '{:0.4f}'.format(mfccs[10] *  (10**17)), '{:0.4f}'.format(mfccs[11] *  (10**17)))
	if (len(mfccs) > 12):
		sql_stmt = "INSERT INTO test_table(`sound`, `mfcc 1`, `mfcc 2`, `mfcc 3`, `mfcc 4`, `mfcc 5`, `mfcc 6`, `mfcc 7`, `mfcc 8`, `mfcc 9`, `mfcc 10`, `mfcc 11`, `mfcc 12`, `mfcc 13`, `mfcc 14`, `mfcc 15`) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"
		values = (int(el), '{:0.4f}'.format(mfccs[0] *  (10**17)), '{:0.4f}'.format(mfccs[1] *  (10**17)), '{:0.4f}'.format(mfccs[2] *  (10**17)), '{:0.4f}'.format(mfccs[3] *  (10**17)), '{:0.4f}'.format(mfccs[4] *  (10**17)), '{:0.4f}'.format(mfccs[5] *  (10**17)),
		'{:0.4f}'.format(mfccs[6] *  (10**17)), '{:0.4f}'.format(mfccs[7] *  (10**17)), '{:0.4f}'.format(mfccs[8] *  (10**17)), '{:0.4f}'.format(mfccs[9] *  (10**17)), '{:0.4f}'.format(mfccs[10] *  (10**17)), '{:0.4f}'.format(mfccs[11] *  (10**17)), '{:0.4f}'.format(mfccs[12] *  (10**17)), '{:0.4f}'.format(mfccs[13] *  (10**17)), '{:0.4f}'.format(mfccs[14] *  (10**17)))
	#if (len(mfccs) == 17):
	cursor.execute(sql_stmt, values)
	#cursor.execute("INSERT INTO test_schema.test_table(`sound`, `mfcc 1`) VALUES (%s, %s);" % (int(1), float(mfccs[0])))
	db_connection.commit()
	db_connection.close();

# Main function
def main():
	count = 0
	folder_no = 0
	arr = getClassifierArray('classifications.txt')
	for el in arr:
		if (el != "-" and el != '0'):
			mffcs = performMFCC("../girl_sing_sample/%s-%s/sample_%s.mp3" % (folder_no, folder_no + 49, str(count).zfill(3)))
			#print(len(mffcs))
			try:
				importData(el, mffcs)
			except:
				continue
			#print("../girl_sing_sample/%s-%s/sample_%s.mp3" % (folder_no, folder_no + 49, str(count).zfill(3)))
		count = count + 1
		if (count % 50 == 0):
			folder_no = folder_no + 50



# Calling function for Main
if __name__=="__main__":
	main()
