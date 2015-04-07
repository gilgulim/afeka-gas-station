package bl;

import java.util.concurrent.LinkedBlockingQueue;

public class ManualWashManager implements Runnable{

	private CleaningServices cleaningServices;
	private CleaningTeamsManager cleanTeamsMngr;
	private LinkedBlockingQueue<Car> carsQueue;
	
	
	public ManualWashManager(CleaningServices cleaningServices, CleaningTeamsManager cleanTeamsMngr) {
		this.cleaningServices = cleaningServices; 
		this.cleanTeamsMngr = cleanTeamsMngr;
		
		carsQueue = new LinkedBlockingQueue<Car>();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	private Car getCarFromQueue() throws InterruptedException{
		return carsQueue.take();
	}
	
	protected void addCarToQueue(Car car) throws InterruptedException{
		carsQueue.put(car);
	}
	

	
}
