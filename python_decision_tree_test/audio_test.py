import matplotlib.pyplot as plt
from scipy.io import wavfile as wav
from python_speech_features import mfcc
from python_speech_features import logfbank
from sklearn import preprocessing
import numpy as np

# Function to perform MFCC analysis on audio file
def preformMFCC():
	rate, data = wav.read('hmm.wav')
	mfcc_feature = mfcc(data, rate, 0.025, 0.01, 20, nfft=1200, appendEnergy=True)
	mfcc_feature = preprocessing.scale(mfcc_feature)
	#delta = calculate_delta(mfcc_feature)
	#data = data[:15000]
	#mfcc_feat = mfcc(data, rate)
	#fbank_feat = logfbank(data,rate)
	#print(fbank_feat[1:3,:])
	plt.matshow(mfcc_feature.T)
	#plt.matshow(fbank_feat.T)
	plt.show()
	#mask = freq>0
	#plt.plot(freq[mask],np.abs(spectre[mask]))
	#plt.show()


# Main function
def main():
	preformMFCC()

# Calling function for Main
if __name__=="__main__":
	main()
