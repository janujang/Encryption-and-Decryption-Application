import java.util.TimerTask;
import java.util.Timer;



public class Timer2 {

	int secondsPassed = 0;
	Timer myTimer = new Timer();
	TimerTask task = new TimerTask (){
		public void run()
		{
			secondsPassed++;
			System.out.println("Seconds passed: " + secondsPassed);
		}
	};
	public void start ()
	{
		myTimer.scheduleAtFixedRate(task, 1000, 1000);
	}
	public static void main (String [] args)
	{
		Timer2 timer = new Timer2();
		timer.start();
	}
}
