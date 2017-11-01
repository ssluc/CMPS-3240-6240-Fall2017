import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ML library in JAVA
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.Instance;
import weka.classifiers.lazy.IBk;
 
public class TestnTrain {
	// reads data
	public static BufferedReader dataReader(String nameOfFile) {
		// locate file
    	File file = new File("nameOfFile");
        
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
		return br;
	}
 
	// evaluate model
	public static Evaluation evaluateModel(Classifier model, Instances trainData, Instances testData) throws Exception {
		Evaluation eval = new Evaluation(trainData);
 
		model.buildClassifier(trainData);
		eval.evaluateModel(model, testData);
 
		return eval;
	}
 
	// determine accuracy
	public static double Accuracy(FastVector predict) {
		double accuracy = 0;
 
		for (int i = 0; i < predict.size(); i++) {
			NominalPrediction np = (NominalPrediction) predict.elementAt(i);
			if (np.predicted() == np.actual()) {
				accuracy++;
			}
		}
 
		return 100 * accuracy / predict.size();
	}
 
	public static void main(String[] args) throws Exception {
		
		// read file
		BufferedReader leFile = dataReader("data.csv");
		Instances data = new Instances(leFile);
		data.setClassIndex(data.numAttributes() - 1);
 
		// separate data into test and train data
		Instances[] train = split[0];
		Instances[] test = split[1];
 
		// linear regression classifier
		Classifier linear = new weka.classifiers.functions.LinearRegression();
		linear.buildClassifier(train);
		linear.buildClassifier(test);
 
		// evaluate model
		Evaluation eval = new Evaluation(data);
		System.out.println("The evluation is" + eval);
 
		// accuracy
		double accu = Accuracy(eval);
		System.out.println("The accuracy is" + accuracy);
		}
 
	}
}
