import java.awt.*; //used for font and graphical interface
import java.awt.event.*; //for listening to buttons
import javax.swing.*; //whole Java swing package

/**
 * Author: Janujan Gathieswaran
 * Date: October 1 2016
 * Description: This program displays the instructions screen for the encrypter/decrypter.
 */

public class Instruction extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L; //used to prevent a warning


	//----[Variable Declaration]----
	//variables for next and back buttons
	JButton btnNext, btnBack; 

	java.awt.Container frame;
	//------------------------------

	public Instruction() 
	{
		//Reference
		//http://stackoverflow.com/questions/18777893/jframe-background-image

		//set the background of the frame as an image
		setContentPane(new JLabel(new ImageIcon("Images/Instructions.png")));

		frame = getContentPane (); //get the container's frame
		frame.setLayout (null); //null layout in order to place items using set bounds below
		setSize (900, 600);  //Set the frame's size

		//http://stackoverflow.com/questions/8849063/adding-a-scrollable-jtextarea-java
		//prevent resizing of window
		setResizable (false);

		//http://stackoverflow.com/questions/10177183/java-add-scroll-into-text-area
		//center window
		setLocationRelativeTo (null);

		//stop application when window is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//create next button, make the button green, make button listen to clicks, and add to frame
		btnNext = new JButton("Next");
		btnNext.setBounds(450, 520, 117, 40);
		btnNext.setBackground(Color.GREEN);
		btnNext.addActionListener(this);
		frame.add(btnNext);

		//create back button, make the button yellow, make button listen to clicks, and add to frame
		btnBack = new JButton("Back");
		btnBack.setBounds(300, 520, 117, 40);
		btnBack.setBackground(Color.YELLOW);
		btnBack.addActionListener(this);
		frame.add(btnBack);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource () == btnNext) //if the next button is pressed
		{
			//Reference to solution to opening a new jframe
			//http://stackoverflow.com/questions/15412467/opening-a-new-jframe-from-a-button

			//open the ProgramMode class
			ProgramMode main = new ProgramMode(); 
			main.setVisible(true);

			dispose (); //get rid of this program window
		}
		if (e.getSource () == btnBack) //if the back button is pressed
		{
			//open the StartScreen class
			StartScreen main = new StartScreen(); 
			main.setVisible(true);

			dispose (); //get rid of this program window
		}
	}

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run() {
				try {
					Instruction frame = new Instruction();
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
