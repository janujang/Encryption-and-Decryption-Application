// The "RandomWordsForEnglish" class.
/*
Date: May 2016
Author: Janujan
Description: This program gets a random English word or phrase from an array of text (words or phrases) and returns them to the user
 */

import java.util.Random; //used for the random class
import java.io.*; //the file access package

/**
 * Author: Janujan Gathieswaran
 * Date: October 1 2016
 * Description: This program generates random decrypted (normal) phrases given a text file with the phrases. 
 * Method List: 
 * String randomWord () throws IOException //method to generate random decrypted phrases 
 */

public class RandomDecryptedPhrases
{
	public static String randomWord () throws IOException
	{
		//Reference
		//http://stackoverflow.com/questions/12264619/loading-a-text-file-into-an-array-then-getting-random-words-out-of-they-array
		//http://stackoverflow.com/questions/20358980/random-word-from-array-list

		//-------[Variable Declaration]-------
		//create a loader object
		Loader loader = new Loader();

		//declare array for listOfWords
		String [] listOfWords;

		//variable for file name
		String fileName = "Files/DecryptedPhrases.txt";

		//call loadFile method and store to listOfWords
		listOfWords = loader.loadFile(fileName); 
		//----------------------------------
		//get a random word from the array and return to user
		Random r = new Random ();
		String randomWord = listOfWords [r.nextInt (listOfWords.length)];
		return randomWord;
	}


	//self-testing method
	public static void main (String[] args) throws IOException
	{
		String newWord = randomWord (); //call randomWord method and store word into newWord

		System.out.println (newWord); //display newWord

	} // main method
} // RandomWordsForEnglish class
