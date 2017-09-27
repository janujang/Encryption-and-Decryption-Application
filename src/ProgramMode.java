import java.awt.*; //used for font and graphical interface
import java.awt.event.*; //for listening to buttons
import javax.swing.*; //whole Java swing package

/**
 * Author: Janujan Gathieswaran
 * Date: October 1 2016
 * Description: This program displays the program modes (live mode and file mode) for the encrypter/decrypter.
 */

public class ProgramMode extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L; //used to prevent a warning

	//----[Variable Declaration]----

	//variable for file mode, live mode, and back  button
	JButton btnFileMode, btnLiveMode, btnBack; 

	java.awt.Container frame;
	//------------------------------

	public ProgramMode() 
	{
		//Reference
		//http://stackoverflow.com/questions/18777893/jframe-background-image

		//set the background of the frame as an image
		setContentPane(new JLabel(new ImageIcon("Images/ProgramMode.png")));

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

		//create live mode button, make button listen to clicks and add to frame
		btnLiveMode = new JButton("Live Mode");
		btnLiveMode.setBounds(338, 205, 229, 55);
		btnLiveMode.addActionListener(this);
		frame.add(btnLiveMode);

		//create file mode button, make button listen to clicks and add to frame
		btnFileMode = new JButton("File Mode");
		btnFileMode.setBounds(338, 328, 230, 55);
		btnFileMode.addActionListener(this);
		frame.add(btnFileMode);

		//create back button, make button yellow, make button listen to clicks and add to frame
		btnBack = new JButton("Back");
		btnBack.setBounds(387, 520, 117, 40);
		btnBack.setBackground(Color.YELLOW);
		btnBack.addActionListener(this);
		frame.add(btnBack);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource () == btnLiveMode) //if the live mode button is pressed
		{
			//Reference to solution to opening a new jframe
			//http://stackoverflow.com/questions/15412467/opening-a-new-jframe-from-a-button

			//open the LiveMode class
			LiveMode main = new LiveMode (); //open the program mode window
			main.setVisible(true);

			dispose (); //get rid of this program window
		}
		if (e.getSource () == btnFileMode) //if the file mode button is pressed
		{
			//open the FileMode class
			FileMode main = new FileMode (); //open the program mode window
			main.setVisible(true);

			dispose (); //get rid of this program window
		}
		if (e.getSource () == btnBack) //if the back button is pressed
		{
			//open the Instruction class
			Instruction main = new Instruction();
			main.setVisible(true);

			dispose (); //get rid of window completely
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
					ProgramMode frame = new ProgramMode();
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
