# Import the required packages
import numpy as np
import pandas as pd
import DBConnection as db
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn import tree
from sklearn.tree import export_graphviz
from sklearn.tree import export_text
from sklearn.metrics import accuracy_score
from sklearn.metrics import classification_report

#########################################################################
# TO DO:
#	1) Document && Clean up code
#	2) Connect tree object to user interface
#########################################################################

# Function used to import dataset from DB
class TreeGenerator:
	csv_file = ""

	def __init__(self, file):
		self.csv_file = file

	def importdata(self):
		balance_data = pd.read_csv(self.csv_file, header=0);
		return balance_data

	# Function to split the dataset into train/test data using cross-validation
	def splitdataset(self, balance_data):
		# Separate the target variable from features
		X = balance_data.values[:, 1:18] # Features
		Y = balance_data.values[:, 0] # Target variable

		# Split the dataset into train/test
		X_train, X_test, y_train, y_test = train_test_split(X, Y, test_size = 0.1, random_state = 100)
		print(X_train)
		print(y_train)

		# Decision tree generator test
		#clf = tree.DecisionTreeClassifier(criterion = "gini",
		#		random_state = 100, max_depth = 6, min_samples_leaf = 2, max_features="log2", min_impurity_decrease=0.5)
		#clf = clf.fit(X, Y)
		#tree.export_graphviz(clf, out_file='test.dot') # create graph of decision tree

		return X, Y, X_train, X_test, y_train, y_test

	# Function to perform training using GINI index
	def train_using_gini(self, X_train, X_test, y_train):
		# Create the classifier object
		clf_gini = DecisionTreeClassifier(criterion = "gini",
				random_state = 100, max_depth = 6, min_samples_leaf = 2, max_features="auto")

		# Perform training
		clf_gini.fit(X_train, y_train)
		#tree.export_graphviz(clf_gini, out_file='test.dot', class_names=["nn", "ah", "eh", "ee", "oh", "oo", "nn", "oo", "ee"]) # create graph of decision tree
		return clf_gini

	# Function to perform training using Entropy
	def train_using_entropy(self, X_train, X_test, y_train):
		# Construct decision tree with entropy
		clf_entropy = DecisionTreeClassifier(criterion = "entropy",
						random_state = 100, max_depth = 6, min_samples_leaf = 2, max_features="auto")
		# Perform training
		clf_entropy.fit(X_train, y_train)
		return clf_entropy


	# Function to make predictions
	def prediction(self, X_test, clf_object):

		# Predicton on test with giniIndex
		y_pred = clf_object.predict(X_test)
		print("Predicted values:")
		print(y_pred)
		return y_pred

	# Function to calculate accuracy
	def cal_accuracy(self, y_test, y_pred):

		print("Confusion Matrix: ",
			confusion_matrix(y_test, y_pred))

		accu = accuracy_score(y_test,y_pred)*100
		print ("Accuracy : ",
		accuracy_score(y_test,y_pred)*100)

		print("Report : ",
		classification_report(y_test, y_pred, zero_division=0))

		return accu

# Main function
def main():
	tree = TreeGenerator("training_data.csv")
	# Build dataset with train/test split
	data = tree.importdata()
	print(data)
	X, Y, X_train, X_test, y_train, y_test = tree.splitdataset(data)

	clf_gini = tree.train_using_gini(X_train, X_test, y_train)
	clf_entropy = tree.train_using_entropy(X_train, X_test, y_train)

	#Perform tests using Gini Index and Entropy
	print("Results Using Gini Index:")

	# Prediction using gini
	y_pred_gini = tree.prediction(X_test, clf_gini)
	gini_accu = tree.cal_accuracy(y_test, y_pred_gini)

	print("Results Using Entropy:")
	# Prediction using entropy
	y_pred_entropy = tree.prediction(X_test, clf_entropy)
	entrop_accu = tree.cal_accuracy(y_test, y_pred_entropy)

	# Write accurate tree to text file
	trtxt = open("trtxt.txt", "w")
	if(gini_accu > entrop_accu):
		trtxt.write(export_text(clf_gini, feature_names=["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"]))
	else:
		trtxt.write(export_text(clf_entropy, feature_names=["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"]))
	trtxt.close()


# Calling function for Main
if __name__=="__main__":
	main()