package bl;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import loging.CustomFilter;
import loging.CustomLogFormatter;

public class CleaningServices implements Runnable{
	private static Logger logger = Logger.getLogger("logger");
	private static int idGenerator = 1;
	private int id;
	private CleaningTeamsManager cleanTeamMngr;
	private LinkedBlockingQueue<Car> autoWashCarsQueue;
	private LinkedBlockingQueue<Car> manualWascarsQueue;
	private int carWashPrice;
	private int autoWashTime;
	private Thread autoWashQueueThread;
	private boolean isActive;
	
	
	public CleaningServices(int carWashPrice, int autoWashTime){
		this.id = idGenerator++;
		cleanTeamMngr = new CleaningTeamsManager(this);
		autoWashQueueThread = new Thread(this);
		
		this.carWashPrice = carWashPrice;
		this.autoWashTime = autoWashTime;

		autoWashCarsQueue = new LinkedBlockingQueue<Car>();
		manualWascarsQueue = new LinkedBlockingQueue<Car>();
		
		//Init logger
		FileHandler theFileHandler;
		try {
			
			theFileHandler = new FileHandler("CleaningServices.txt", true);
			theFileHandler.setFormatter(new CustomLogFormatter());
			theFileHandler.setFilter(new CustomFilter(this, "id", this.id));
			logger.addHandler(theFileHandler);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		Car car;
		
		try {
			while(isActive){
				car = autoWashCarsQueue.take();
				logger.log(Level.INFO, String.format("car %d removed from auto wash queue.", car.getId()),car);
				logger.log(Level.INFO, String.format("car %d about to start auto wash.", car.getId()),this);
				if(car != null){
					sendCarToAutoWash(car);		
					manualWascarsQueue.put(car);
					logger.log(Level.INFO, String.format("car %d done auto wash and added to manual wash queue.", car.getId()),car);
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
			logger.log(Level.INFO, "cleaning services started.",this);
			autoWashQueueThread.start();
			cleanTeamMngr.startCleaningTeam();
		}
	}
	
	public void stopCleaningSrv(){
		if(isActive){
			isActive = false;
			logger.log(Level.INFO, "cleaning services stopped.",this);
			autoWashCarsQueue.notifyAll();
		}
	}
	
	public void addWashTeam(WashingTeam washingTeam) throws InterruptedException{
		cleanTeamMngr.addTeamToQueue(washingTeam);
	}
	

	private void sendCarToAutoWash(Car car) throws InterruptedException{
		Thread.sleep(autoWashTime);
		logger.log(Level.INFO, String.format("car %d began auto wash.", car.getId()),car);
		logger.log(Level.INFO, String.format("car %d finished auto wash.", car.getId()),this);
	}
		
	public void addCarToAutoWashQueue(Car car) throws InterruptedException{
		autoWashCarsQueue.put(car);
		logger.log(Level.INFO, String.format("car %d added to auto wash queue.", car.getId()),car);
	}
	
	public int getCurrentWaitingTime(){
		int result, autoWashQueueSize, autoWashTime;
		
		autoWashQueueSize = autoWashCarsQueue.size();
		autoWashTime = this.autoWashTime;
	
		result = autoWashQueueSize * autoWashTime;
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
