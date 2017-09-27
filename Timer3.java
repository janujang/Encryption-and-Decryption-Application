import java.util.TimerTask;
import java.util.Timer;



public class Timer3 {
	boolean timerDone =false;
	int seconds = 1;
	Timer myTimer = new Timer();
	TimerTask task = new TimerTask (){
		public void run()
		{
			if (seconds <= 2)
			{
				System.out.println ("Seconds = " + seconds);
				seconds++;
			}
			else
			{
				cancel ();
				timerDone=true;
			}

		}
	};
	public void start ()
	{
		myTimer.scheduleAtFixedRate(task, 1000, 1000);
	}
	public static void main (String [] args)
	{
		Timer3 timer = new Timer3();
		timer.start();
	}
}


