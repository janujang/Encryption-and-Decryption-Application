import java.awt.*; //used for font and graphical interface
import java.awt.event.*; //for listening to buttons
import javax.swing.*; //whole Java swing package

/**
 * Author: Janujan Gathieswaran
 * Date: October 1 2016
 * Description: This program displays the start/splash screen for the encrypter/decrypter.
 */

public class StartScreen extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L; //used to prevent a warning

	//----[Variable Declaration]----
	JButton btnStart; //variable for start button

	JButton image; //variable for image

	java.awt.Container frame; 
	//------------------------------

	public StartScreen()
	{
		//Reference
		//http://stackoverflow.com/questions/18777893/jframe-background-image

		//set the background of the frame as an image
		setContentPane(new JLabel(new ImageIcon("Images/SplashScreenBackground.png")));

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

		//create start button, make button listen to clicks, and add to frame
		btnStart = new JButton("Start");
		btnStart.setBounds(380, 520, 117, 40);
		btnStart.addActionListener(this);
		frame.add(btnStart);

		//Reference to hide button outline and shape
		//http://stackoverflow.com/questions/8367500/how-to-hide-background-of-jbutton-which-containt-icon-image

		//create image button, make button listen to clicks, and add to frame
		image = new JButton();
		image.setBounds(299, 130, 308, 282);
		image.setIcon(new ImageIcon("Images/Locks1.png")); //set an icon for the button
		image.setBorderPainted(false);
		image.setContentAreaFilled(false); 		//hide outline and background of button
		image.setFocusPainted(false); 
		image.setOpaque(false);
		image.addActionListener (this);
		frame.add(image);

		//mouseover, release for image button
		image.addMouseListener (new MouseAdapter ()
		{
			public void mouseEntered (MouseEvent evt)  //when the mouse is over the picture, change the picture
			{
				image.setIcon (new ImageIcon ("Images/Locks2.png"));

			}
			public void mouseExited (MouseEvent evt)  //when the mouse is released from the picture, change the picture
			{
				image.setIcon (new ImageIcon ("Images/Locks1.png"));
			}
		}
				);

	}
	public void actionPerformed(ActionEvent e) {
		//Reference to solution to opening a new jframe
		//http://stackoverflow.com/questions/15412467/opening-a-new-jframe-from-a-button

		//when a button is pressed, open the Instruction class
		Instruction main = new Instruction(); 
		main.setVisible(true);

		dispose (); //get rid of this program window
	}


	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					StartScreen frame = new StartScreen();
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
