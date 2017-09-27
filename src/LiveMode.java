import java.awt.*; //used for font and graphical interface
import java.awt.event.*; //for listening to buttons
import javax.swing.*; //whole Java swing package


/*
 * Author: Janujan Gathieswaran
 * Date: October 1 2016
 * Description: This program allows the user to encrypt and decrypt text given a phrase as input and an encryption key between -32767 and 32768. In particular,
                the encryption key is used to shift the letter of the alphabet to the right or left. Lower case and upper case remains the same and no changes 
                are made to non-letters. Notably, the user is able to switch back and forth between encrypt and decypt and view them together in live mode.
 * Method List: 
 * static boolean checkKey (int encryptKey) // used to check if the key entered is within -32767 and 32768
 * int putKeyInRange (int encryptKey) //used to put the key within the range of 0 to 25
 */

public class LiveMode extends JFrame implements ActionListener 
{

	private static final long serialVersionUID = 1L; //used to prevent warning

	//-----------[Variable Declaration]---------------

	Color LIGHTGRAY = new Color (200, 200, 200); //new light gray colour for the text area backgrounds

	//variables for encrypt, decrypt, error, enter a key and key in range labels above text fields
	JLabel lblEncrypt, lblDecrypt, lblError, lblEnterAKey, lblKeyInRange;

	//variable for key text and key in range text fields
	JTextField keyTextField, keyInRangeField;

	//variable for encrypt and decrypt text areas
	JTextArea encryptArea, decryptArea;

	//variables for scroll panes for the encrypt and decrypt text area
	JScrollPane encryptScroll, decryptScroll;

	//variables to store input text and output text
	String phraseIn = "", phraseOut = "";

	//variables for switch, clear, exit, exit, execute, generateText, generateKey, home, OK and back buttons
	JButton btnSwitch, btnClear, btnExit, btnExecute, btnGenerateText, btnGenerateKey, btnHome, btnOK, btnBack;

	//variable for counting the number of clicks of a button
	int btnCounter = 1;

	//variable for the encrypt/decrypt key and key in range
	int key = 0;
	
	int keyInRange = 0;
			
	//variables for storing random encrypt an decrypt text
	String randomEncryptText = "", randomDecryptText = "";

	//variables for small, medium, and big fonts 
	Font smallFont = new Font ("Arial", Font.BOLD, 14);
	Font mediumFont = new Font ("Arial", Font.BOLD, 18);
	Font bigFont = new Font ("Arial", Font.BOLD, 25);

	//create Encryption object 
	Encryption encrypt = new Encryption ();

	//variable required for older IDE's
	Container frame;

	//---------------------------------

	public LiveMode() {

		//Reference
		//http://stackoverflow.com/questions/18777893/jframe-background-image

		//set the background of the frame as an image		
		setContentPane (new JLabel (new ImageIcon ("Images/BackgroundSuperSpy.png")));

		frame = getContentPane (); //get the container's frame
		frame.setLayout (null); //null layout in order to place items using set bounds below
		setSize(900, 600);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//http://stackoverflow.com/questions/10177183/java-add-scroll-into-text-area
		//center window
		setLocationRelativeTo (null);

		//http://stackoverflow.com/questions/8849063/adding-a-scrollable-jtextarea-java
		//prevent resizing of window
		setResizable(false);

		//create error label and add to frame
		lblError = new JLabel ();
		lblError.setBounds (418, 185, 150, 25);
		frame.add(lblError);

		//create enter a key label, set font to white, and add to frame
		lblEnterAKey = new JLabel("Enter a key within -32767 and +32768");
		lblEnterAKey.setBounds(105, 91, 268, 16);
		lblEnterAKey.setForeground(Color.WHITE);
		frame.add(lblEnterAKey);

		//create key within range label, set font to white, and add to frame
		lblKeyInRange = new JLabel("Key within range of 0-25");
		lblKeyInRange.setBounds(640, 91, 158, 16);
		lblKeyInRange.setForeground(Color.WHITE);
		frame.add(lblKeyInRange);

		//create encrypt label, set font to Bebas Neue 17pt, and add to frame
		lblEncrypt = new JLabel("Encrypt");
		lblEncrypt.setBounds(230, 185, 61, 16);
		lblEncrypt.setFont(new Font("Bebas Neue", Font.PLAIN, 17));
		frame.add(lblEncrypt);

		//create decrypt label, set font to Bebas Neue 17pt, and add to frame
		lblDecrypt = new JLabel("Decrypt");
		lblDecrypt.setBounds(625, 185, 61, 16);
		lblDecrypt.setFont(new Font("Bebas Neue", Font.PLAIN, 17));
		frame.add(lblDecrypt);

		//create key text field, set columns to 10 and add to frame
		keyTextField = new JTextField ();
		keyTextField.setBounds(105, 111, 122, 26);
		keyTextField.setColumns(10);
		frame.add(keyTextField);

		//create key in range text field, set columns to 10, disable editing and add to frame
		keyInRangeField = new JTextField();
		keyInRangeField.setBounds(676, 111, 122, 26);
		keyInRangeField.setEditable(false);
		keyInRangeField.setColumns(10);
		frame.add(keyInRangeField);

		//create encrypt area, disable editing, format line wrapping, make border gray and background light gray
		encryptArea = new JTextArea();
		encryptArea.setEditable(false);
		encryptArea.setLineWrap (true); //allows text to continue in new line
		encryptArea.setWrapStyleWord (true); //allow the text to use multiple lines
		encryptArea.setBorder (BorderFactory.createLineBorder (Color.gray)); 
		encryptArea.setBackground (LIGHTGRAY);

		//setting up scrollpane as a parent of encrypt text area, disable the scroll pane, and add to frame
		encryptScroll = new JScrollPane(encryptArea);
		encryptScroll.setBounds(134, 203, 239, 163);
		encryptScroll.setEnabled(false);
		frame.add(encryptScroll);

		//create decrypt area, disable editing, format line wrapping, make border gray and background light gray
		decryptArea = new JTextArea();
		decryptArea.setEditable(false);
		decryptArea.setLineWrap (true); //allows text to continue in new line
		decryptArea.setWrapStyleWord (true); //allow the text to use multiple lines
		decryptArea.setBorder (BorderFactory.createLineBorder (Color.gray)); 
		decryptArea.setBackground (LIGHTGRAY);

		//setting up scrollpane as a parent of decrypt text area, disable the scroll pane, and add to frame
		decryptScroll = new JScrollPane(decryptArea);
		decryptScroll.setBounds(527, 203, 239, 163);
		decryptScroll.setEnabled(false);
		frame.add(decryptScroll);

		//create switch button, make button orange, set icon for button, make button listen to clicks, disable button and add to frame
		btnSwitch = new JButton("");
		btnSwitch.setBounds(418, 250, 61, 56);
		btnSwitch.setBackground(Color.ORANGE);
		btnSwitch.setIcon(new ImageIcon("Images/Reverse.png"));
		btnSwitch.addActionListener(this);
		btnSwitch.setEnabled(false);
		frame.add(btnSwitch);

		//create back button, make button cyan, make button listen to clicks and add to frame
		btnBack = new JButton("Back");
		btnBack.setBounds(579, 506, 117, 42);
		btnBack.setBackground(Color.CYAN);
		btnBack.addActionListener(this);
		frame.add(btnBack);

		//create clear button, make button yellow, make button listen to clicks and add to frame
		btnClear = new JButton("Clear");
		btnClear.setBounds(223, 506, 117, 42);
		btnClear.setBackground(Color.YELLOW);
		btnClear.addActionListener(this);
		frame.add(btnClear);

		//create home button, make button pink, make button listen to clicks and add to frame
		btnHome = new JButton("Home");
		btnHome.setBounds(396, 506, 117, 42);
		btnHome.setBackground(Color.PINK);
		btnHome.addActionListener(this);
		frame.add(btnHome);

		//create exit button, make button red, make button listen to clicks and add to frame
		btnExit = new JButton("Exit");
		btnExit.setBounds(742, 506, 117, 42);
		btnExit.setBackground(Color.RED);
		btnExit.addActionListener(this);
		frame.add(btnExit);

		//create execute button, make button green and text black, make button listen to clicks, disable button and add to frame
		btnExecute = new JButton("Execute");
		btnExecute.setBounds(391, 408, 122, 56);
		btnExecute.setForeground(Color.BLACK);
		btnExecute.setBackground(Color.GREEN);
		btnExecute.addActionListener(this);
		btnExecute.setEnabled(false);
		frame.add(btnExecute);

		//create OK button, make button listen to clicks and add to frame
		btnOK = new JButton("OK");
		btnOK.setBounds(343,108,45,32);
		btnOK.addActionListener(this);
		frame.add(btnOK);

		//create generate key button, make button listen to clicks and add to frame
		btnGenerateKey = new JButton("Generate Key");
		btnGenerateKey.setBounds(234, 108, 106, 32);
		btnGenerateKey.addActionListener(this);
		frame.add(btnGenerateKey);

		//create generate text button, make button cyan, make button listen to clicks, disable button and add to frame
		btnGenerateText = new JButton("Generate Text");
		btnGenerateText.setBounds(56, 506, 117, 42);
		btnGenerateText.setBackground(Color.CYAN);
		btnGenerateText.addActionListener(this);
		btnGenerateText.setEnabled(false);
		frame.add(btnGenerateText);
		
		setVisible (true);

	}
	public static boolean checkKey (int encryptKey)  // used to check if the key entered is within -32767 and 32768
	{
		if (encryptKey >= -32767 && encryptKey <=32768) //check if the key is within --32767 and 32768
		{
			return true;
		}
		return false;
	}
	public static int putKeyInRange (int encryptKey) //used to put the key within the range of 0-25
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

	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource()==btnOK) //if the OK button is pressed
		{
			try
			{
				if (keyTextField.getText().equals(""))
				{
					lblError.setText ("Invalid"); //display invalid
					lblError.setFont (mediumFont); //set font size to medium font
					lblError.setForeground (Color.RED); //set font colour to red
				}
				else
				{
					lblError.setText (""); //clear the error label

					key = Integer.parseInt(keyTextField.getText()); //get the key from the text field and convert to integer

					//call putKeyInRange method to find the value within 1-26 (alphabet) 
					keyInRange = putKeyInRange(key);
					
					//set text field to the key in range value
					keyInRangeField.setText(keyInRange +"");

					//--------------------------------
					//enable editing for decryptArea and set background to white
					decryptArea.setEditable(true);
					decryptArea.setBackground (Color.WHITE);

					//enable switch, execute, generate text and generate key buttons
					btnSwitch.setEnabled(true);
					btnExecute.setEnabled(true);
					btnGenerateText.setEnabled(true);

					//disable generate key and OK buttons
					btnGenerateKey.setEnabled(false);
					btnOK.setEnabled(false);
				}
			}
			catch (NumberFormatException f)
			{
				lblError.setText ("Invalid"); //display invalid
				lblError.setFont (mediumFont); //set font size to medium font
				lblError.setForeground (Color.RED); //set font colour to red
			}
		}
		if (e.getSource() == btnGenerateKey) //if the generate key button is pressed
		{
			int randomValue = 0; //variable for random number

			//Reference
			//http://stackoverflow.com/questions/3938992/how-to-generate-random-positive-and-negative-numbers-in-java

			//generate random values from -32767 to 32768
			randomValue = -32767 + (int)(Math.random()*((32768-(-32767))+1));

			//set the key text field to the random value
			keyTextField.setText(randomValue + "");

			//call putKeyInRange method to find the value within 0-25 
			keyInRange = putKeyInRange(randomValue);
			
			//set text field to the key in range value
			keyInRangeField.setText(keyInRange +"");
		}

		if (e.getSource () == btnSwitch) //if the switch button is clicked
		{
			btnCounter++; //add one to the button counter

			if (btnCounter % 2 == 0) //if the button counter number is even
			{
				//enable encrypt text area editing and disable decrypt text area editing
				encryptArea.setEditable (true); 
				decryptArea.setEditable (false); 

				//set the colour of the encrypt text area as white and the colour of the decrypt text as light gray
				encryptArea.setBackground (Color.WHITE); 
				decryptArea.setBackground (LIGHTGRAY); 
			}
			else //when button counter number is odd
			{
				//disable encrypt text area editing and enable decrypt text area editing
				encryptArea.setEditable (false);
				decryptArea.setEditable (true);  

				//set the colour of the encrypt text area as light gray and the colour of the decrypt text as white
				encryptArea.setBackground (LIGHTGRAY); 
				decryptArea.setBackground (Color.WHITE); 
			}
		}
		if (e.getSource () == btnGenerateText) //if the generateText button is pressed
		{
			try //try catch used to throw IO Exception error
			{
				//call randomWord method from RandomEncryptedPhrases class and store to randomEncryptText
				randomEncryptText = RandomEncryptedPhrases.randomWord (); 

				//call randomWord method from RandomDecryptedPhrases class and store to randomDecryptText
				randomDecryptText = RandomDecryptedPhrases.randomWord (); 
			}
			catch (Exception f)
			{
			}
			if (btnCounter % 2 == 0) //if the button counter number is even
			{
				encryptArea.setText (randomEncryptText); //insert the random encrypt text into the encrypt text area
			}
			else //if the button counter is odd
			{
				decryptArea.setText (randomDecryptText); //insert the random decrypt text into the decrypt text area
			}
		}
		if (e.getSource () == btnExecute) //if the execute button is pressed
		{
			btnGenerateKey.setEnabled(false); //disable generate key button
			keyTextField.setEditable(false); //disable editing for key text field

			if (btnCounter % 2 == 0)  //if the button counter number is even, decrypt
			{
				phraseIn = encryptArea.getText (); //get the text from the encrypt text area and store into phraseIn

				key = Integer.parseInt(keyTextField.getText()); //get the key from the text field and convert to integer

				//call putKeyInRange method to find the value within 0-25
				keyInRange = putKeyInRange(key);
				
				//set text field to the key in range value
				keyInRangeField.setText(keyInRange +"");

				//if nothing is entered or the key is outside of range, display invalid
				if (encryptArea.getText ().equals ("") || (checkKey(key) == false)) 
				{
					lblError.setText ("Invalid");
					lblError.setFont (mediumFont); //set font size to medium font
					lblError.setForeground (Color.RED); //set font colour to red
				}
				else 
				{
					phraseOut = ""; //empty the phraseOut string

					lblError.setText (""); //clear the error label

					//call phraseSingleOutput method from Encryption class to determine the output
					phraseOut = encrypt.phraseSingleOutput(phraseIn, keyInRange, "Decrypt");

					//set decrypt area text to phraseOut
					decryptArea.setText(phraseOut); 
				}
			}
			else //if  the button counter is odd, encrypt
			{
				phraseIn = decryptArea.getText ();  //get the text from the decrypt text area and store into phraseIn

				key = Integer.parseInt(keyTextField.getText()); //get the key

				//call putKeyInRange method to find the value within 0-25
				keyInRange = putKeyInRange(key);
				
				//set text field to the key in range value
				keyInRangeField.setText(keyInRange +"");

				//if nothing is entered or the key is outside of range, display invalid
				if (decryptArea.getText ().equals ("") || (checkKey(key) == false))
				{
					lblError.setText ("Invalid");
					lblError.setFont (mediumFont); //set font size to medium font
					lblError.setForeground (Color.RED); //set font colour to red
				}
				else
				{
					phraseOut = ""; //empty the phraseOut string

					lblError.setText (""); //clear the error label

					//call phraseSingleOutput method from Encryption class to determine the output
					phraseOut = encrypt.phraseSingleOutput(phraseIn, keyInRange, "Encrypt");

					//set encrypt area text to phraseOut
					encryptArea.setText(phraseOut);
				}
			}
		}
		if (e.getSource () == btnClear) //if the clear button is pressed
		{
			//clear all text fields
			lblError.setText ("");
			encryptArea.setText ("");
			decryptArea.setText ("");
			keyTextField.setText("");
			keyInRangeField.setText("");

			//disable switch, generate text and execute and enable generate key and OK buttons
			btnSwitch.setEnabled(false);
			btnGenerateText.setEnabled(false);
			btnExecute.setEnabled(false);
			btnGenerateKey.setEnabled(true); //enable generate key button
			btnOK.setEnabled(true);

			//enable editing for key text field and disable editing for encryptArea and decryptArea
			keyTextField.setEditable(true); 
			encryptArea.setEditable(false);
			decryptArea.setEditable(false);

			//set the background of encryptArea and decryptArea to light gray
			encryptArea.setBackground(LIGHTGRAY);
			decryptArea.setBackground (LIGHTGRAY);

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
		LiveMode g = new LiveMode ();
	}
}
