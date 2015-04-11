package bl;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public class CleaningTeamsManager implements Runnable{
	private static Logger logger = Logger.getLogger("logger");
	private final int QUEUE_POLL_TIMEOUT = 2000;
	private LinkedBlockingQueue<WashingTeam> teamsQueue;
	private LinkedBlockingQueue<Car> manualWascarsQueue;
	private Thread manualWashQueueThread;
	private boolean isActive;
	
	public CleaningTeamsManager(CleaningServices cleaningServices) {
		isActive = false;
		manualWashQueueThread = new Thread(this);
		teamsQueue = new LinkedBlockingQueue<WashingTeam>();
		manualWascarsQueue = new LinkedBlockingQueue<Car>();
	}
	
	@Override
	public void run() {
		
		WashingTeam washingTeam;
		Car car;
		boolean threadActive = true;

		while(threadActive){
			try {				
				
				washingTeam = teamsQueue.poll(QUEUE_POLL_TIMEOUT, TimeUnit.MILLISECONDS);
				if(washingTeam != null){

					car = manualWascarsQueue.poll(QUEUE_POLL_TIMEOUT, TimeUnit.MILLISECONDS);
				
					if(car != null){
						
						logger.log(Level.INFO, String.format("WashingTeam %d removed from washing teams queue and waiting for next car.", washingTeam.getId()),washingTeam);						
						logger.log(Level.INFO, String.format("car %d removed from manual wash queue.", car.getId()),car);
						sendCarToManualWash(car,washingTeam);
						
					}else{ 
						if(!isActive){
							threadActive = false;
						}else{
							//Pushing back the washingTeam that has not been in use.
							teamsQueue.put(washingTeam);
						}
							
					}
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		logger.log(Level.INFO, "Cleaning teams manager queue closed.");
	}

	public void startCleaningTeam(){
		if(!isActive){
			isActive = true;
			manualWashQueueThread.start();
		}
	}
	
	public void stopCleaningTeam(){
		if(isActive){
			isActive = false;
		}
	}
	
	protected void addCarToQueue(Car car) throws InterruptedException{
		manualWascarsQueue.put(car);
	}
	
	private void sendCarToManualWash(Car car, WashingTeam washingTeam) throws InterruptedException {
		ManualCarWash manualCarWash = new ManualCarWash(car, washingTeam, this);
		manualCarWash.start();
	}
	
	protected synchronized void addTeamToQueue(WashingTeam washingTeam) throws InterruptedException{
		
		logger.log(Level.INFO, String.format("WashingTeam %d added to washing teams queue.", washingTeam.getId()),washingTeam);
		teamsQueue.put(washingTeam);
	}

	public static int getManualWashTime() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(1000)+1000;
	}

	public LinkedBlockingQueue<WashingTeam> getTeamsQueue() {
		return teamsQueue;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
}
