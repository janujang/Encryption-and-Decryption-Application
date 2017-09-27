import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/*
   Author: Janujan Gathieswaran
   Date: October 1 2016
   Description: This program allows the user to encrypt and decrypt text given a number of phrases as input and an encryption key between -32767 and 32768. In particular,
                the encryption key is used to shift the letter of the alphabet to the right or left. Lower case and upper case remains the same and no changes are made to non-letters. 
	Method List: 
	String phraseSingleOutput (String phrase, int encryptKey, String mode) //method to loop through the one phrase, decode or encode and return a String of the final result
  	String phraseMultiOutput (String phrases[], int encryptKey, String mode) //method to loop through an array of phrases, decode or encode and return a String of the final result
	boolean isALetter (char character) //method to check if the character is a letter
	char encode (char letter, int encryptKey) //method to encode character (shift the character based on specified encryptKey) and return an encrypted character
	char decode (char letter, int encryptKey)//method to decode character and return decrypted character
 */
public class Encryption {



	//method to loop through the one phrase, decode or encode and return a String of the final result
	public String phraseSingleOutput (String phrase, int encryptKey, String mode)
	{
		String output = ""; //variable to hold the output

		//loop through the phrases array (different lines of the text file)
		for (int i = 0; i < phrase.length(); i++)
		{
			//if it's a letter, encrypt or decrypt depending on mode
			if (isALetter(phrase.charAt(i)) == true)
			{
				if (mode.equals("Encrypt")) //encrypt
				{
					output += encode(phrase.charAt(i), encryptKey);
				}
				else //decode
				{
					output += decode(phrase.charAt(i), encryptKey);
				}
			}
			else //otherwise, don't make any changes to letter
			{
				output += phrase.charAt(i);
			}
		}
		return output; //return the output
	}

	//method to loop through an array of phrases, decode or encode and return a String of the final result
	public String phraseMultiOutput (String phrases[], int encryptKey, String mode)
	{
		String output = ""; //variable to hold the output

		//loop through the phrases array (different lines of the text file)
		for (int i = 0; i < phrases.length; i++)
		{
			//loop through the characters of each line
			for (int j=0; j<phrases[i].length();j++)
			{
				//if it's a letter, encrypt or decrypt depending on mode
				if (isALetter(phrases[i].charAt(j)) == true)
				{
					if (mode.equals("Encrypt")) //encrypt
					{
						output += encode(phrases[i].charAt(j), encryptKey);
					}
					else //decode
					{
						output += decode(phrases[i].charAt(j), encryptKey);
					}
				}
				else //otherwise, don't make any changes to letter
				{
					output += phrases[i].charAt(j);
				}
			}
			output += "\n"; //add a new line
		}
		return output; //return the output
	}

	//method to check if the character is a letter
	public boolean isALetter (char character)
	{
		int ascii; //variable for the ascii code

		ascii = (int)character; //convert character into ascii code

		//check if the letter is between A-Z or a-z
		if (ascii>=65 && ascii<=90 || ascii>=97 && ascii<=122)
		{
			return true; //return true if it is a letter
		}
		else 
		{
			return false; //return false if it is anything else (punctuation)
		}
	}

	//method to encode character (shift the character based on specified encryptKey) and return an encrypted character
	public char encode (char letter, int encryptKey)
	{
		//Reference for main algorithm 
		//http://stackoverflow.com/questions/4698290/promoting-letters-in-a-string-to-the-next-letter-in-java
		//http://stackoverflow.com/questions/28395830/java-cipher-shifting-letters
		//http://www.dreamincode.net/forums/topic/326746-question-shifting-characters-to-the-next-letter-in-the-alphabet/

		if (letter >='A' && letter<='Z')  //capital letters
		{
			/*
			  subtract char code of 'A' from letter, giving a value of 0 to 25, add the encryptKey, perform modulus 26 on value, 
			  and then add the char code of 'A'
			 */
			return (char) (((letter-'A'+encryptKey)%26)+'A');
		}
		else //lower case letters
		{
			/*
		     subtract char code of 'a' from letter, giving a value of 0 to 25, add the encryptKey, perform modulus 26 on value, 
		     and then add the char code of 'a'
			 */
			return (char) (((letter-'a'+encryptKey)%26)+'a');
		}
	}

	//method to decode character and return decrypted character
	public char decode (char letter, int decryptKey)
	{
		decryptKey *= -1; //flip the sign (opposite polarity) of the decryptKey when decoding, but same magnitude 

		decryptKey = LiveMode.putKeyInRange(decryptKey); //call putKeyInRange method from LiveMode to get a key within 0-25

		return encode (letter, decryptKey); //call the encode method and return character
	}

	/**
	 * 
	 * @param args
	 * Description: Self-testing method (tested with several phrases)
	 */
	public static void main(String[] args) 
	{
		//-----[Variable Declaration]---------
		String input = "", output = "", display = "", mode = "", fileN = ""; //variables for phrase, output, display, fileName, and  mode

		String outputArray[] = new String [0]; //declare and intialize an output array

		int key = 0; //variable for the key

		String [] buttons = {"Encrypt", "Decrypt"}; //array for encrypt or decrypt buttons

		int modeOption = 0; //variable to choose between encrypt and decrypt options

		char character = ' '; //variable for character

		//create text area to display output and no editing
		JTextArea outputArea = new JTextArea(); 
		outputArea.setEditable(false);

		//create an object of the class
		Encryption encrypt = new Encryption(); 

		//create a Loader object 
		Loader loader = new Loader(); 
		//------------------------------------

		//prompt for the word
		input = JOptionPane.showInputDialog(null, "Enter the string"); 

		//prompt for the encrypt key
		key = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter a key")); 

		//if the encrypt key is out of range, prompt until it is within the range
		while (key <= -32767 || key >=32768)
		{
			key = Integer.parseInt(JOptionPane.showInputDialog(null, "Error. Enter a key within range of -32767 and "
					+ "+32768 Please enter an encryption key"));
		}

		key = LiveMode.putKeyInRange(key); //call putKeyInRange method from LiveMode to determine the key from 0-25

		//prompt for mode
		modeOption = JOptionPane.showOptionDialog(null, "Encrypt or Decrypt?", "Encrypt or Decrypt", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);

		if (modeOption==0) //encrypt
		{
			//call phraseSingleOutput method for output
			output = encrypt.phraseSingleOutput(input, key, "Encrypt"); 
		}
		else //decrypt
		{
			//call phraseSingleOutput method for output
			output = encrypt.phraseSingleOutput(input, key, "Decrypt"); 
		}


		//add the input and output strings to display
		display += "Input:\n " + input + "\n\n";
		display += "Output:\n " + output + "\n";

		//set text to display content 
		outputArea.setText(display);

		//display output
		JOptionPane.showMessageDialog(null, outputArea, "Output", JOptionPane.PLAIN_MESSAGE);

		//-----------------------------------------------------------------------------------
		//Encode Testing with positive encrypt key
		input = "Macs are the best computers on earth?!?!?! 23123DFSD#$asd"; //phrase input
		key = 4000; //key of 4000
		mode = "Encrypt"; //set mode to encrypt

		output = encrypt.phraseSingleOutput(input, key, mode); //call phraseSingleOutput method for output (testing for phraseSingleOutput as well)

		//display results
		System.out.println("Input: " + input);
		System.out.println("Output: " + output + "\n");
		//---------------------------------------------
		//Encode Testing with negative encrypt key
		input = "Logitech speakers are great. Bose is just OVERpriced!"; //phrase input
		key = -2234; // key of -2234
		key = LiveMode.putKeyInRange(key); //call putKeyInRange method from LiveMode class to put key in range
		mode = "Encrypt";//set mode to encrypt

		output = encrypt.phraseSingleOutput(input, key, mode); //call phraseSingleOutput method for output

		//display results
		System.out.println("Input: " + input);
		System.out.println("Output: " + output + "\n");
		//-----------------------------------------------------------------------------------

		//Decode Testing with positive decrypt key
		input = "Khoor"; //phrase input
		key = 224; // key of 224
		mode = "Encrypt"; //set mode to encrypt

		output = encrypt.phraseSingleOutput(input, key, mode); //call phraseSingleOutput method for output

		//display results
		System.out.println("Input: " + input);
		System.out.println("Output: " + output + "\n");
		//---------------------------------------------
		//Decode Testing with negative decrypt key
		input = "Khoor"; //phrase input
		key = 3; // key of 3
		mode = "Decrypt";//set mode to encrypt

		output = encrypt.phraseSingleOutput(input, key, mode); //call phraseSingleOutput method for output 

		//display results
		System.out.println("Input: " + input);
		System.out.println("Output: " + output + "\n");
		//-----------------------------------------------------------------------------------

		//isALetter Testing
		character = 'b'; //set character to 'b'

		//display the result
		System.out.println("Input: " + character);
		System.out.println("Output: " + encrypt.isALetter(character) + "\n"); 

		character = '3'; //set character to '3'

		//display the result
		System.out.println("Input: " + character);
		System.out.println("Output: " + encrypt.isALetter(character) + "\n" ); 
		//-----------------------------------------------------------------------------------

		//PhraseMultiOutput Testing
		//-------------------------------
		fileN = "Files/TestPhrase.txt"; //set file name to "TestPhrase.txt"

		try //try - catch used to throw IOException
		{
			//call method loadFile method 
			outputArray = loader.loadFile(fileN);
		} 
		catch (IOException e) {} 

		//loop through the output array to display contents
		System.out.println("Input: ");
		for (int i = 0; i < outputArray.length; i++) {
			System.out.println(outputArray[i]);
		}
		//display output
		System.out.println("\nOutput: " + "\n" + encrypt.phraseMultiOutput(outputArray, 4, "Encrypt"));
	}

	//--[Excess Code]-------------------------
	/*
		//Another approach to encrypting or decrypting phrases with split

		//Reference 
		//Use the split method to split the characters and go through every character
	    //http://stackoverflow.com/questions/4674850/converting-a-sentence-string-to-a-string-array-of-words-in-java		

	    //create an array called words by splitting the phrase wherever there are spaces
		 chars = phrases[i].split (""); //split the phrase between every character

		 for (int j = 0; j<chars.length;j++)
		 {
			if (isALetter(chars[j].charAt(0)) == true)
			{
			   output += encode(chars[j].charAt(0), encryptKey);
			}
		    else
			{
			   output += chars[j].charAt(0);
			}
		}
	    output += "\n"; //add a new line
	 */		
}
