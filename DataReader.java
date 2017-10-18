package com.sluc.csv;

// imports to read and work with files, arrays, lists, and arrayLists
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// reads data from CSV file into a two dimensional array
public class DataReader {
    public static void main(String[] args) {
    	
    	// locate file
    	File file = new File("sampledata.csv");
        
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
}