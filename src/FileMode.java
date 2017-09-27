import java.awt.*; //used for font and graphical interface
import java.awt.event.*; //for listening to buttons
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

/*
 * Author: Janujan Gathieswaran
 * Date: October 1 2016
 * Description: This program allows the user to encrypt and decrypt text given a phrase as input and an encryption key between -32767 and 32768. In particular,
                the encryption key is used to shift the letter of the alphabet to the right or left. Lower case and upper case remains the same and no changes 
                are made to non-letters. Notably, the user is able to load in a file with phrases, encrypt or decrypt, save the output file, and view it. 
 * Method List: 
 * static boolean checkKey (int encryptKey) // used to check if the key entered is within -32767 and 32768
 * int putKeyInRange (int encryptKey) //used to put the key within the range of 0 to 25
 * 
 */

public class FileMode extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //used to prevent warning

	//----[Variable Declaration]----
	//declare input and output string arrays
	String[] input; 
	String[] output;

	//declare error boolean and set to false
	boolean error = false; 

	//array to hold the different options for the drop down menu
	String[] modeTypes = {"Encrypt", "Decrypt"}; 

	//variables to store input and output text
	String phraseIn = "", phraseOut = ""; 

	//variables for input file, output file, file saved, error, output file name, encryption key, key in range and mode labels
	JLabel lblInputFile, lblOutputFile, lblFileSaved, lblError, lblInputFileName, lblOutputFileName,lblEncryptionKey,lblKeyInRange, lblMode;

	//variables for back, clear, view file, exit, execute, generate key and home button
	JButton btnBack, btnClear, btnViewFile, btnExit, btnExecute, btnGenerateKey, btnHome; 

	//variables for input file, output file, key input and key in range text fields
	JTextField inputFileInput, outputFileInput, keyInput, keyInRangeInput;

	//variable for mode selection JComboBox (used for drop down menu)
	JComboBox modeSelection;

	//variables for medium and big sized fonts
	Font mediumFont = new Font ("Arial", Font.BOLD, 15);
	Font bigFont = new Font ("Arial", Font.BOLD, 25);

	//variables for input file name and output file name
	String inputFileName = "", outputFileName = "";

	//set modeSelected to encrypt (default option for JComboBox)
	String modeSelected = "Encrypt";

	//variable for the encrypt/decrypt key and key in range
	int key = 0;
	
	int keyInRange = 0;

	//variable for input and ouput text areas
	JTextArea inputArea, outputArea;

	//variables for scroll panes for the input and output text area
	JScrollPane inputScroll, outputScroll;

	//creating objects for Loader and Encryption classes
	Loader load = new Loader();
	Encryption encrypt = new Encryption();

	//variable required for older IDE's
	java.awt.Container frame;
	//---------------------------------


	public FileMode() {
		//Reference
		//http://stackoverflow.com/questions/18777893/jframe-background-image

		//set the background of the frame as an image	
		setContentPane(new JLabel(new ImageIcon("Images/FileMode.png")));

		frame = getContentPane (); //get the container's frame
		frame.setLayout (null); //null layout in order to place items using set bounds below
		setSize (900, 600);  //Set the frame's size

		//http://stackoverflow.com/questions/8849063/adding-a-scrollable-jtextarea-java
		//prevent resizing of window
		setResizable (false);

		//http://stackoverflow.com/questions/10177183/java-add-scroll-into-text-area
		//center window
		setLocationRelativeTo (null);

		//when window is closed, exit the program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//create home button, make button pink, make it listen to clicks and add to frame
		btnHome = new JButton("Home");
		btnHome.setBounds(32, 520, 117, 40);
		btnHome.setBackground(Color.PINK);
		btnHome.addActionListener(this);
		frame.add(btnHome);

		//create execute button, make button green, make it listen to clicks and add to frame
		btnExecute = new JButton("Execute");
		btnExecute.setBounds(339, 520, 117, 40);
		btnExecute.addActionListener(this);
		btnExecute.setBackground(Color.GREEN);
		frame.add(btnExecute);

		//create clear button, make button yellow, make it listen to clicks and add to frame
		btnClear = new JButton("Clear");
		btnClear.setBounds(183, 520, 117, 40);
		btnClear.setBackground(Color.YELLOW);
		btnClear.addActionListener(this);
		frame.add(btnClear);

		//create view file button, make button blue, make it listen to clicks, disable button and add to frame
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(478, 520, 117, 40);
		btnViewFile.setBackground(Color.BLUE);
		btnViewFile.addActionListener(this);
		btnViewFile.setEnabled(false);
		frame.add(btnViewFile);

		//create exit button, make button red, make it listen to clicks, and add to frame
		btnExit = new JButton("Exit");
		btnExit.setBounds(759, 520, 117, 40);
		btnExit.setBackground(Color.RED);
		btnExit.addActionListener(this);
		frame.add(btnExit);

		//create generate key button, make button yellow, make it listen to clicks, and add to frame
		btnGenerateKey = new JButton("Generate Key");
		btnGenerateKey.setBounds(52, 307, 117, 26);
		btnGenerateKey.addActionListener(this);
		frame.add(btnGenerateKey);

		//create back button, make button orange, make it listen to clicks, and add to frame
		btnBack = new JButton("Back");
		btnBack.setBounds(619, 520, 117, 40);
		btnBack.setBackground(Color.ORANGE);
		btnBack.addActionListener(this);
		frame.add(btnBack);

		//create input file label, set font to white, and add to frame
		lblInputFileName = new JLabel("Input File Name:");
		lblInputFileName.setBounds(32, 130, 117, 16);
		lblInputFileName.setForeground(Color.WHITE);
		frame.add(lblInputFileName);

		//create output file label, set font to white, and add to frame
		lblOutputFileName = new JLabel("Output File Name:");
		lblOutputFileName.setBounds(32, 189, 117, 16);
		lblOutputFileName.setForeground(Color.WHITE);
		frame.add(lblOutputFileName);

		//create encryption key label, set font to white, and add to frame
		lblEncryptionKey = new JLabel("Key (-32767 to +32768):");
		lblEncryptionKey.setBounds(32, 246, 163, 16);
		lblEncryptionKey.setForeground(Color.WHITE);
		frame.add(lblEncryptionKey);

		//create key in range label, set font to white, and add to frame
		lblKeyInRange = new JLabel("Key Within Range (0-25):");
		lblKeyInRange.setBounds(32, 355, 185, 16);
		lblKeyInRange.setForeground(Color.WHITE);
		frame.add(lblKeyInRange);

		//create mode label, set font to white, and add to frame
		lblMode = new JLabel("Mode:");
		lblMode.setBounds(32, 433, 59, 16);
		lblMode.setForeground(Color.WHITE);
		frame.add(lblMode);

		//create input file label, set font to white, and add to frame
		lblInputFile = new JLabel("Input File");
		lblInputFile.setBounds(384, 102, 61, 16);
		lblInputFile.setForeground(Color.WHITE);
		frame.add(lblInputFile);

		//create output file label, set font to white, and add to frame
		lblOutputFile = new JLabel("Output File");
		lblOutputFile.setBounds(682, 102, 78, 16);
		lblOutputFile.setForeground(Color.WHITE);
		frame.add(lblOutputFile);

		//create error label, set font to red and medium, and add to frame
		lblError = new JLabel ();
		lblError.setBounds (520, 100, 150, 25);
		lblError.setForeground (Color.RED);
		lblError.setFont (mediumFont);
		frame.add (lblError);

		//create file saved label, set font to green and medium, and add to font
		lblFileSaved = new JLabel ();
		lblFileSaved.setBounds (520, 100, 150, 25);
		lblFileSaved.setForeground (Color.GREEN);
		lblFileSaved.setFont (mediumFont);
		frame.add (lblFileSaved);

		//create input file text field, set columns to 10, and add to frame
		inputFileInput = new JTextField("Files/TheThreeLittlePigs.txt");
		inputFileInput.setBounds(32, 151, 154, 26);
		inputFileInput.setColumns(10);
		frame.add(inputFileInput);

		//create output file text field, set columns to 10, and add to frame
		outputFileInput = new JTextField("output.txt");
		outputFileInput.setBounds(32, 208, 154, 26);
		outputFileInput.setColumns(10);
		frame.add(outputFileInput);

		//create key input text field, set columns to 10, and add to frame
		keyInput = new JTextField();
		keyInput.setBounds(32, 269, 154, 26);
		keyInput.setColumns(10);
		frame.add(keyInput);

		//create key in range text field, set columns to 10, disable editing and add to frame
		keyInRangeInput = new JTextField();
		keyInRangeInput.setBounds(32, 376, 154, 26);
		keyInRangeInput.setColumns(10);
		keyInRangeInput.setEditable(false);
		frame.add(keyInRangeInput);

		//Reference for combo box
		//https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html

		//Reference for adding an action listener to a JComboBox
		//https://kodejava.org/how-do-i-add-an-action-listener-to-jcombobox/

		//create a combo box with the modeTypes array and add to frame 
		modeSelection = new JComboBox (modeTypes);
		modeSelection.setBounds(82, 429, 104, 27);
		frame.add(modeSelection);

		//add action listener to this particular combo box and get the item that is selected
		modeSelection.addActionListener (new ActionListener ()
		{
			public void actionPerformed (ActionEvent e)
			{
				JComboBox combo = (JComboBox) e.getSource ();
				modeSelected = (String) combo.getSelectedItem (); //store item into the modeSelected variable
				//System.out.println (selectedSortType);
			}
		}
				);
		//create input area, disable editing, format line wrapping, make border gray and background light gray
		inputArea = new JTextArea();
		inputArea.setEditable(false);
		inputArea.setLineWrap (true); //allows text to continue in new line
		inputArea.setWrapStyleWord (true); //allow the text to use multiple lines
		inputArea.setBorder (BorderFactory.createLineBorder (Color.gray)); 

		//setting up scrollpane as a parent of input text area, disable the scroll pane, and add to frame
		inputScroll = new JScrollPane(inputArea);
		inputScroll.setEnabled(false);
		inputScroll.setBounds(264, 130, 300, 338);
		frame.add(inputScroll);

		//create output area, disable editing, format line wrapping, make border gray and background light gray
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setLineWrap (true); //allows text to continue in new line
		outputArea.setWrapStyleWord (true); //allow the text to use multiple lines
		outputArea.setBorder (BorderFactory.createLineBorder (Color.gray)); 

		//setting up scrollpane as a parent of input text area, disable the scroll pane, and add to frame
		outputScroll = new JScrollPane(outputArea);
		outputScroll.setEnabled(false);
		outputScroll.setBounds(576, 130, 300, 338);
		frame.add(outputScroll);
	}

	public static boolean checkKey (int encryptKey) // used to check if the key entered is within -32767 and 32768
	{
		if (encryptKey >= -32767 && encryptKey <=32768) //check if the key is within -32767 and 32768
		{
			return true;
		}
		return false;
	}
	public static int putKeyInRange (int encryptKey) //used to put the key within the range 0 to 25
	{
		int newKey = 0; //variable for new key
		newKey = encryptKey % 26; //find the remainder of encryptKey /26

		//if the newKey is less than 0, add 26 (negative numbers can be explained as shift to right)
		if (newKey <0)
		{
			newKey+=26;
		}
		return newKey; //return the new key
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == btnGenerateKey) //if the generate key button is pressed
		{
			int randomValue = 0; //variable for random number

			//Reference
			//http://stackoverflow.com/questions/3938992/how-to-generate-random-positive-and-negative-numbers-in-java

			//generate random values from -32767 to 32768
			randomValue = -32767 + (int)(Math.random()*((32768-(-32767))+1));

			//set the key text field to the random value
			keyInput.setText(randomValue + "");

			//call putKeyInRange method to find the value within 0 to 25
			keyInRange = putKeyInRange(randomValue);
			
			//set text field to the key in range value
			keyInRangeInput.setText(keyInRange+"");
		}
		if(e.getSource()==btnViewFile) //if the view file button is pressed
		{
			btnViewFile.setEnabled(false); //disable button
			
			try  //try - catch used to throw IOException
			{
				//call loadFile method to process the input file
				input = load.loadFile(inputFileName);
				output = load.loadFile(outputFileName);
			} 
			catch (IOException e1) 
			{
			} 	
			//loop through input array and add each line to phraseIn 
			for (int j = 0; j < input.length; j++) {
				phraseIn += input[j] + "\n";
			}
			inputArea.setText(phraseIn); //set the text of inputArea to phraseIn

			phraseOut = ""; //clear phrase out variable
			 
			//loop through input array and add each line to phraseOUt 
			for (int j = 0; j < output.length; j++) {
				phraseOut += output[j] + "\n";
			}
			outputArea.setText(phraseOut);//set the text of inputArea to phraseOut
		}
		if (e.getSource()==btnExecute) //if the execute button is pressed
		{ 
			//disable buttons
			btnExecute.setEnabled(false); 
			btnGenerateKey.setEnabled(false); 

			//disable the drop down menu
			modeSelection.setEnabled (false);

			//disable the text fields for input file, output file, key Input 
			inputFileInput.setEditable (false);
			outputFileInput.setEditable (false);
			keyInput.setEditable (false);

			//---------------------------------------------
			try //try-catch to prevent error
			{
				//if the text fields are empty display invalid input
				if (inputFileInput.getText().equals ("") || outputFileInput.getText().equals ("") || keyInput.getText().equals(""))
				{
					lblError.setText ("Invalid Input");
					error = true;
				}
				else if (checkKey(Integer.parseInt(keyInput.getText())) == false) //if the key is outside of range
				{
					lblError.setText ("Invalid Input"); //display invalid input
					error = true;
				}
				else
				{
					//enable the view file button
					btnViewFile.setEnabled(true);
					
					//set the input file name to the text entered in the input file text field
					inputFileName = inputFileInput.getText ();

					//set the output file name to the text entered in the output file text field
					outputFileName = outputFileInput.getText ();

					//get the key from the text field and convert to integer				
					key = Integer.parseInt((keyInput.getText()));
					
					//call putKeyInRange method to find the value within 0 to 25
					keyInRange = putKeyInRange(key);
					
					//set text field to the key in range value
					keyInRangeInput.setText(keyInRange+"");

					//clear the contents of the error label
					lblError.setText (""); 

					//call loadFile method to process the input file
					input = load.loadFile(inputFileName); 

					//call the phraseMultiOutput method from the Encryption class and store output in phraseOut
					phraseOut = encrypt.phraseMultiOutput(input, keyInRange, modeSelected);

					//setup output file
					FileWriter outFile = new FileWriter (outputFileName);
					PrintWriter output = new PrintWriter (outFile);

					output.println(phraseOut); //print the results to the output file

					output.close(); //close the file
				}
			} //display an error message depending on the possible exceptions
			catch (FileNotFoundException f)
			{
				lblError.setText ("File not found");
				error = true;
			}
			catch (NumberFormatException f)
			{
				lblError.setText ("Number Format");
				error = true;
			}
			catch (NullPointerException f)
			{
				lblError.setText ("Cancelled");
				error = true;
			}
			catch (IOException f)
			{
				lblError.setText ("Unknown Error");
				error = true;
			}
			catch (Exception f)
			{
				lblError.setText ("Unknown Error");
				error = true;
			}

			if (error==false) //display "File Saved" if there are no errors
			{ 
				lblFileSaved.setText ("File Saved"); 
			}
		}

		if (e.getSource () == btnClear) //if the clear button is pressed
		{
			//clear all text fields
			outputArea.setText ("");
			inputArea.setText ("");
			lblError.setText ("");
			lblFileSaved.setText("");
			keyInput.setText("");
			keyInRangeInput.setText("");

			//set error boolean to false
			error = false;
			
			//set the modeSelected to Encrypt
			modeSelected = "Encrypt";

			//clear phrase in and out strings
			phraseIn = "";
			phraseOut = "";

			//make the input, output, key input, and view file text fields editable
			inputFileInput.setEditable (true);
			outputFileInput.setEditable (true);
			keyInput.setEditable(true);

			//disable the view file button and enable execute and generate key buttons
			btnViewFile.setEnabled(false);
			btnExecute.setEnabled(true);
			btnGenerateKey.setEnabled(true);

			//set the text of the file input text fields to default files (TheThreeLittlePigs.txt)
			inputFileInput.setText ("Files/TheThreeLittlePigs.txt");
			outputFileInput.setText ("outputFile.txt");
			
			//clear input and output file name strings
			inputFileName = "";
			outputFileName = "";
			
			//set the selected index of the drop down menus to 0 (first item)
			modeSelection.setSelectedIndex (0);
			modeSelection.setEnabled (true);
		}
		if (e.getSource () == btnExit) //if exit button is pressed, close application
		{
			setVisible (false);
			System.exit (0); //close application
		}
		if (e.getSource () == btnHome) //if home button is pressed, return to splash screen
		{
			//Reference to solution to opening a new jframe
			//http://stackoverflow.com/questions/15412467/opening-a-new-jframe-from-a-button

			//open the StartScreen class
			StartScreen main = new StartScreen();
			main.setVisible(true);

			dispose (); //get rid of window completely
		}
		if (e.getSource () == btnBack) //if the back button is pressed
		{
			//open the ProgramMode class
			ProgramMode main = new ProgramMode (); //open the program mode screen
			main.setVisible(true);

			dispose (); //get rid of this program window
		}
	}

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					FileMode frame = new FileMode();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
}
