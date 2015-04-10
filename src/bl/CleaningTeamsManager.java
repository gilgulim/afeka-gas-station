package bl;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public class CleaningTeamsManager implements Runnable{
	private static Logger logger = Logger.getLogger("logger");
	private CleaningServices cleaningServices;
	private LinkedBlockingQueue<WashingTeam> teamsQueue;
	private Thread manualWashQueueThread;
	private boolean isActive;
	
	public CleaningTeamsManager(CleaningServices cleaningServices) {
		this.cleaningServices = cleaningServices;
		manualWashQueueThread = new Thread(this);
		teamsQueue = new LinkedBlockingQueue<WashingTeam>();
	}
	
	@Override
	public void run() {
		
		WashingTeam washingTeam;
		Car car;
		try {
			while(isActive){
				washingTeam = teamsQueue.take();
				logger.log(Level.INFO, String.format("WashingTeam %d removed from washing teams queue and waiting for next car.", washingTeam.getId()),washingTeam);
				
				car = cleaningServices.getCarFromQueue();
				logger.log(Level.INFO, String.format("car %d removed from manual wash queue.", car.getId()),car);
				
				if(car != null && washingTeam != null){
					sendCarToManualWash(car,washingTeam);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			teamsQueue.notifyAll();
		}
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
