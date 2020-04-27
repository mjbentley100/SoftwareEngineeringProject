import librosa
from pydub import AudioSegment
import numpy as np
from python_speech_features import mfcc
import pickle
from sklearn.tree import DecisionTreeClassifier
from sklearn import tree
from scipy.io import wavfile as wav
import time
import os, time

i = 0
init_moddate = os.stat("../SEProject_2/current.wav")[8]
while(1):
    file = open("../SEProject_2/jam.txt","w")
    file.write("I'm jamming 8)")
    file.close()
    moddate = os.stat("../SEProject_2/current.wav")[8]
    if (moddate != init_moddate):
        init_moddate = moddate
        i = i + 1
        # extract signal and frame rate from audio sample
        try:
            file = open("../SEProject_2/check.txt","w")
            file.write("I'm jamming")
            file.close()
            
            sound = AudioSegment.from_wav("../SEProject_2/current.wav")
            sig = np.frombuffer(sound.raw_data, dtype=np.int16)
            rate = sound.frame_rate
            #rate, sig = wav.read('./hmm.wav')
            arr = []

            # perform analysis on audio segment
            mfccs = mfcc(sig,rate,winlen=0.025,winstep=0.01,numcep=13, nfilt=26,nfft=1106,lowfreq=0,highfreq=None,preemph=0.97, ceplifter=22,appendEnergy=True)
            chroma_stft = librosa.feature.chroma_stft(y=sig.astype("float32"), sr=rate)
            spec_cent = librosa.feature.spectral_centroid(y=sig.astype("float32"), sr=rate)
            spec_bw = librosa.feature.spectral_bandwidth(y=sig.astype("float32"), sr=rate)
            rolloff = librosa.feature.spectral_rolloff(y=sig.astype("float32"), sr=rate)
            zcr = librosa.feature.zero_crossing_rate(y=sig.astype("float32"))
            mfcc_features = mfccs.mean(axis=1)

            count = 0
            for feat in mfcc_features:
                if (count < 12):
                    arr.append(feat)
                count = count + 1

            arr.append(np.mean(chroma_stft))
            arr.append(np.mean(spec_cent))
            arr.append(np.mean(spec_bw))
            arr.append(np.mean(rolloff))
            arr.append(np.mean(zcr))
            #print("hi")
            file = open("../SEProject_2/check.txt","w")
            file.write(str(arr) + "\n" + str(i))
            file.close()
        except:
            #print("failed")
            #file = open("../SEProject_2/check.txt","w")
            #file.write("I'm dying :(")
            #file.close()
            pass
