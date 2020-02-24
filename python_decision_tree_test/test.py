# Import the required packages
import numpy as np
import pandas as pd
import mysql.connector as sql
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn import tree
from sklearn.tree import export_graphviz
from sklearn.metrics import accuracy_score
from sklearn.metrics import classification_report
#from sklearn.datasets import load_iris

# Function for connecting to MySQL Database
def connectDB():
	db_connection = sql.connect(host='localhost', database='test_schema', user='test_user', password='test123')
	return db_connection

# Function used to import dataset from DB
def importdata():
	db_connection = connectDB()
	balance_data = pd.read_sql('SELECT * FROM test_table', con=db_connection);
	db_connection.close();
	return balance_data

# Function to split the dataset into train/test data using cross-validation
def splitdataset(balance_data):
	# Separate the target variable from features
	X = balance_data.values[:, 1:3] # Features
	Y = balance_data.values[:, 0] # Target variable

	# Split the dataset into train/test
	X_train, X_test, y_train, y_test = train_test_split(
	X, Y)

	# Decision tree generator test
	#clf = tree.DecisionTreeClassifier()
	#clf = clf.fit(X, Y)
	#tree.export_graphviz(clf, out_file='test.dot') # create graph of decision tree

	return X, Y, X_train, X_test, y_train, y_test

# Function to perform training using GINI index
def train_using_gini(X_train, X_test, y_train):
	# Create the classifier object
	clf_gini = DecisionTreeClassifier(criterion = "gini",
			random_state = 100, max_depth = 2, min_samples_leaf = 4)

	# Perform training
	clf_gini.fit(X_train, y_train)
	return clf_gini

# Function to perform training using Entropy
def tarin_using_entropy(X_train, X_test, y_train):
	# Construct decision tree with entropy
	clf_entropy = DecisionTreeClassifier(
			criterion = "entropy", random_state = 100,
			max_depth = 2, min_samples_leaf = 4)

	# Perform training
	clf_entropy.fit(X_train, y_train)
	return clf_entropy


# Function to make predictions
def prediction(X_test, clf_object):

	# Predicton on test with giniIndex
	y_pred = clf_object.predict(X_test)
	print("Predicted values:")
	print(y_pred)
	return y_pred

# Function to calculate accuracy
def cal_accuracy(y_test, y_pred):

	print("Confusion Matrix: ",
		confusion_matrix(y_test, y_pred))

	print ("Accuracy : ",
	accuracy_score(y_test,y_pred)*100)

	print("Report : ",
	classification_report(y_test, y_pred))

# Main function
def main():
	# Build dataset with train/test split
	data = importdata()
	print(data)
	X, Y, X_train, X_test, y_train, y_test = splitdataset(data)

	clf_gini = train_using_gini(X_train, X_test, y_train)
	clf_entropy = tarin_using_entropy(X_train, X_test, y_train)

	#Perform tests using Gini Index and Entropy
	print("Results Using Gini Index:")

	# Prediction using gini
	y_pred_gini = prediction(X_test, clf_gini)
	cal_accuracy(y_test, y_pred_gini)

	print("Results Using Entropy:")
	# Prediction using entropy
	y_pred_entropy = prediction(X_test, clf_entropy)
	cal_accuracy(y_test, y_pred_entropy)


# Calling function for Main
if __name__=="__main__":
	main()
