/*
 * Author: Janujan Gathieswaran
 * Date: October 1 2016
 * Description: This program is used to test the GUI methods which includes checkKey and putKeyInRange.
 * Method List: 
 * static boolean checkKey (int encryptKey) // used to check if the key entered is within -32767 and 32768
 * int putKeyInRange (int encryptKey) //used to put the key within the range of 0 to 25
 */

public class TestingForGUIMethods {

	public static boolean checkKey (int encryptKey)  // used to check if the key entered is within -32767 and 32768
	{
		if (encryptKey >= -32767 && encryptKey <=32768) //check if the key is within --32767 and 32768
		{
			return true;
		}
		return false;
	}
	public static int putKeyInRange (int encryptKey) //used to put the key within the range of 0 to 25
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


	//self-testing main method
	public static void main(String[] args) 
	{
		//variable for key
		int key =0;

		//--[checkKey testing]---------------------
		//in range
		key =  -324;

		//display result
		System.out.println("Input: " + key + "\nOutput: " + checkKey(key) + "\n");

		//---------------
		//out of range
		key = 2324233;

		//display result
		System.out.println("Input: " + key + "\nOutput: " + checkKey(key) + "\n");

		//--[putKeyInRange testing]---------------------
		key =  -324;

		//display result
		System.out.println("Input: " + key + "\nOutput: " + putKeyInRange(key) + "\n");

	}

}
