import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;
 
public class TestnTrain {
	public static void readDataFile() {
		// locate file
    	File file = new File("data.csv");
        
        // set up buffered reader to read file
        BufferedReader br = null;
        
        // set up empty line string
        String line = "";
        
        // read the file
        try {
			br = new BufferedReader(new FileReader(file));
		}

        // signals that file does not exist or is inaccessible
        catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        
        try {
            // while line is not empty
			while ((line = br.readLine()) != null) {

				// parse the line of data using commas and add the data into a two dimensional arrayList
				List<String> dataArray = new ArrayList<String>(Arrays.asList(line.split(",")));
			        
				// print the arrayList
				System.out.println(dataArray);
			}
		}
        
        // signals that there has been an input or output error
        catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	// evaluate model
	public static evaluateModel(Classifier model, Instances trainData, Instances testData) throws Exception {
		evaluateModel eval = new evaluateModel(trainData);
 
		model.buildClassifier(trainData);
		eval.evaluateModel(model, testData);
 
		return eval;
	}
 
	// determines accuracy
	public static double Accuracy(FastVector predict) {
		double correct = 0;
 
		for (int i = 0; i < predict.size(); i++) {
			NominalPrediction np = (NominalPrediction) predict.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
 
		return 100 * correct / predict.size();
	}
 
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
 
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
 
		return split;
	}
 
	public static void main(String[] args) throws Exception {
		BufferedReader datafile = readDataFile("weather.txt");
 
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
 
		// Do 10-split cross validation
		Instances[][] split = crossValidationSplit(data, 10);
 
		// Separate split into training and testing arrays
		Instances[] trainingSplits = split[0];
		Instances[] testingSplits = split[1];
 
		// Use a set of classifiers
		Classifier[] models = { 
				new J48(), // a decision tree
				new PART(), 
				new DecisionTable(),//decision table majority classifier
				new DecisionStump() //one-level decision tree
		};
 
		// Run for each model
		for (int j = 0; j < models.length; j++) {
 
			// Collect every group of predictions for current model in a FastVector
			FastVector predictions = new FastVector();
 
			// For each training-testing split pair, train and test the classifier
			for (int i = 0; i < trainingSplits.length; i++) {
				Evaluation validation = classify(models[j], trainingSplits[i], testingSplits[i]);
 
				predictions.appendElements(validation.predictions());
 
				// Uncomment to see the summary for each training-testing pair.
				//System.out.println(models[j].toString());
			}
 
			// Calculate overall accuracy of current classifier on all splits
			double accuracy = calculateAccuracy(predictions);
 
			// Print current classifier's name and accuracy in a complicated,
			// but nice-looking way.
			System.out.println("Accuracy of " + models[j].getClass().getSimpleName() + ": "
					+ String.format("%.2f%%", accuracy)
					+ "\n---------------------------------");
		}
 
	}
}
