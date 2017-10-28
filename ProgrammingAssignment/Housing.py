""" Name : Sharon Luc
Intro to Machine Learning
Programming Assignment: Housing Prices
Date : 10/27/17

Using 30/70 test/train split(30% test and 70% train):
1) Implement a gradient descent algorithm that can do regression and predicts the price.
2) Report the mean squared error for train and test. 
3) Choose a threshold T and assume all the houses with a price larger than T are labeled as expensive and the rest are labeled as non-expensive. Implement a simple perceptron that can classify the houses as expensive/non-expensive for you. Report accuracy for train and test.
""

import numpy as np
import random
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
from sklearn import datasets, linear_model

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state = random)

def gradientDescent(x, y, w, learningRate, mse, count):
    xTrans = x.transpose() 												# for matrices
    for i in range(0, count):
        result = np.dot(x, w) 											# result
        error = result - y 												# mean squared error
        mean_squared_error = np.sum(error ** 2) / (2 * mse)				# mean squared error
        gradient = np.dot(xTrans, error) / mse 							# gradient
        theta = theta - learningRate * gradient 						# update
    return theta
