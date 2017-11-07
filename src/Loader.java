import java.io.*;

import javax.swing.JOptionPane;

/**
 * Authors: Janujan Gathieswaran
 * Date: October 1 2016
 * Description: This program loads phrases from a file into an array of Strings
 */
public class Loader {
	/**
	 * 
	 * @param fileName
	 * @return String array
	 * @throws IOException
	 */
	public String [] loadFile (String fileName) throws IOException{

		//----[Variable Declaration]-------
		String line = ""; //variable to store the lines 
		String phrases [];  //declare a string array for the phrases
		int lineCount = 0; //variable to store line count
		//---------------------------------

		//open file for reading
		FileReader fr = new FileReader (fileName);
		BufferedReader inputFile = new BufferedReader (fr);

		line = inputFile.readLine();		//reads first line.

		// loop to count the number of lines in the file
		while (line != null)		// while not a null character
		{
			lineCount++;
			line = inputFile.readLine();
		}
		inputFile.close(); //close input file

		// re-open the file to read
		fr = new FileReader (fileName);
		inputFile = new BufferedReader (fr);

		phrases = new String [lineCount]; //create the phrases array

		//loop through array to store phrases
		for (int j = 0; j < phrases.length; j++) {
			phrases[j] = inputFile.readLine();
		}
		inputFile.close(); //close input file

		return phrases; //return the phrases array
	}

	/**
	 * @param args
	 * @throws IOException 
	 * Description: Self-testing main method
	 */
	public static void main(String[] args) throws IOException {

		//---[Variable Declaration]------
		String output[]; //declare an output array

		//create a Loader object 
		Loader loader = new Loader(); 

		//variable for file name
		String fileN; 

		//create an Encryption object
		Encryption encrypt = new Encryption();
		//-------------------------------

		//prompt user for file name
		fileN = JOptionPane.showInputDialog(null, "file", "TestPhrase.txt");

		//call loadFile method and store to output
		output = loader.loadFile(fileN); 
	
		//loop through the output array to display contents
		System.out.println("Input: ");
		for (int i = 0; i < output.length; i++) {
			System.out.println(output[i]);
		}
		//display output
		System.out.println("\nOutput: " + "\n" + encrypt.phraseMultiOutput(output, 4, "Encrypt"));
	}
}
