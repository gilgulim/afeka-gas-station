package bl;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import loging.CustomFilter;
import loging.CustomLogFormatter;

public class CleaningServices implements Runnable{
	private static Logger logger = Logger.getLogger("logger");
	private CleaningTeamsManager cleanTeamMngr;
	private LinkedBlockingQueue<Car> autoWashCarsQueue;
	private LinkedBlockingQueue<Car> manualWascarsQueue;
	private int carWashPrice;
	private int autoWashTime;
	private Thread autoWashQueueThread;
	private boolean isActive;
	
	
	public CleaningServices(int carWashPrice, int autoWashTime){
		cleanTeamMngr = new CleaningTeamsManager(this);
		autoWashQueueThread = new Thread(this);
		
		this.carWashPrice = carWashPrice;
		this.autoWashTime = autoWashTime;

		autoWashCarsQueue = new LinkedBlockingQueue<Car>();
		manualWascarsQueue = new LinkedBlockingQueue<Car>();
		
	}
	
	@Override
	public void run(){
		Car car;
		
		try {
			while(isActive){
				car = autoWashCarsQueue.take();
				if(car != null){
					sendCarToAutoWash(car);
					manualWascarsQueue.put(car);
				}
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startCleaningSrv(){
		if(!isActive){
			isActive = true;
			autoWashQueueThread.start();
			
			cleanTeamMngr.startCleaningTeam();
		}
	}
	
	public void stopCleaningSrv(){
		if(isActive){
			isActive = false;
			autoWashCarsQueue.notifyAll();
		}
	}
	
	public void addWashTeam(WashingTeam washingTeam) throws InterruptedException{
		cleanTeamMngr.addTeamToQueue(washingTeam);
	}
	

	private void sendCarToAutoWash(Car car) throws InterruptedException{
		Thread.sleep(autoWashTime);
	}
		
	public void addCarToAutoWashQueue(Car car) throws InterruptedException{
		autoWashCarsQueue.put(car);
	}
	
	public int getCurrentWaitingTime(){
		int result, autoWashQueueSize, manualWashQueueSize, autoWashTime, manualWashTime, teamsQueueSize;
		
		autoWashQueueSize = autoWashCarsQueue.size();
		manualWashQueueSize = getCarsQueue().size();
		teamsQueueSize = cleanTeamMngr.getTeamsQueue().size();
		autoWashTime = this.autoWashTime;
		manualWashTime = CleaningTeamsManager.getManualWashTime();
		
		result = (autoWashQueueSize * autoWashTime) + (manualWashQueueSize / teamsQueueSize)*manualWashTime;
		return result;
	}

	protected Car getCarFromQueue() throws InterruptedException{
		return manualWascarsQueue.take();
	}
	
	protected void addCarToQueue(Car car) throws InterruptedException{
		manualWascarsQueue.put(car);
	}

	public LinkedBlockingQueue<Car> getCarsQueue() {
		return manualWascarsQueue;
	}

	public CleaningTeamsManager getCleanTeamMngr() {
		return cleanTeamMngr;
	}
	
	public boolean isActive() {
		return isActive;
	}
}
