package bl;

import java.util.concurrent.BlockingQueue;

public class CleaningTeamDispatcher extends Thread{
	private BlockingQueue<Car> autoWashQueue;
	private int autoWashTimeToClean;
	
	public CleaningTeamDispatcher(){
		
	}

	public synchronized Car getCarFromQueue(){
		return null;
		
	}
	
}
