package bl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import bl.NotificationsHandler.NotifyType;
import bl.Exceptions.FillingRepositoryMaxLimitException;
import bl.Exceptions.FuelRepositoryEmptyException;

public class FuelRepository implements Runnable {
	
	private static Logger logger = Logger.getLogger("logger");
	
	private final int FILLING_REPOSITORY_PACE = 800;
	private final float LOW_BORDER_PERCENTAGE = 20;
	private boolean isAvailable;
	private int currentCapacity;
	private int maxCapacity;
	private int pumpingPacePerLiter;
	private Lock fillingRepositoryLock;
	private Thread fillRepositoryThread;
	private int fillFuelAmount;
	private boolean warningFlag = true;
	private NotificationsHandler notifyHandler;
	
	public FuelRepository(int currentCapacity, int maxCapacity, int pumpingPacePerLiter){
		this.maxCapacity = maxCapacity;
		this.currentCapacity = currentCapacity;
		this.pumpingPacePerLiter = pumpingPacePerLiter;
		fillingRepositoryLock = new ReentrantLock();
		fillRepositoryThread = new Thread(this);
		fillRepositoryThread.setName("FillRepositoryThread");
		
		if(maxCapacity > 0){
			this.isAvailable = true;
		}else{
			this.isAvailable = false;
		}
		
		logger.info("Fuel-Repository started.");
	}
	public void getOneLitterOfFuel() throws FuelRepositoryEmptyException, InterruptedException
	{
		
		
		fillingRepositoryLock.lock();
		try{
			float lowCapacityBorder = (float)(LOW_BORDER_PERCENTAGE / 100 * maxCapacity);
			if(currentCapacity > 0){
				if(lowCapacityBorder >= currentCapacity){
					
					if(warningFlag){
						logger.warning("Fuel amount in fuel repository is lower than: " + lowCapacityBorder);
						warningFlag = false;
						if(notifyHandler!= null){
							notifyHandler.notificationHandle(NotifyType.warnLowFuel, null);
						}
					}
					
					
					currentCapacity--;
					
					//Waiting here to simulate pumping one litter of fuel
					Thread.sleep(this.pumpingPacePerLiter);
					
				}else{
					warningFlag=true;
				}
				currentCapacity--;
			}else{
				logger.warning("Fuel repository is empty!");
				if(notifyHandler!= null){
					notifyHandler.notificationHandle(NotifyType.errFuelRepositoryEmpty, null);
				}
				throw new FuelRepositoryEmptyException();
			}
		}
		finally{
			fillingRepositoryLock.unlock();
		}
			
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public int getCurrentCapacity() {
		return currentCapacity;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public int getPumpingPacePerLiter(){
		return pumpingPacePerLiter;
	}
	public void fillRepository(int fuelAmount) throws FillingRepositoryMaxLimitException{
		fillFuelAmount = fuelAmount;
		if(fuelAmount + currentCapacity > maxCapacity)
		{
			throw new FillingRepositoryMaxLimitException();
			
		}else{
			
			fillRepositoryThread.start();
		}
	}
	public void setErrorHandler(NotificationsHandler errorHandler){
		this.notifyHandler = errorHandler; 
	}
	
	@Override
	public void run() {
		
		fillingRepositoryLock.lock();
		
		int fuelAmount = 0;
		try {
			while(fuelAmount < fillFuelAmount){
				Thread.sleep(FILLING_REPOSITORY_PACE);		
				fuelAmount++;
				currentCapacity++;
				
				logger.log(Level.INFO, String.format("Filling fuel repository: %d", currentCapacity));
				if(notifyHandler != null){
					notifyHandler.notificationHandle(NotifyType.infoFuelingRepStatus, currentCapacity);
				}
			}
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(notifyHandler != null){
			notifyHandler.notificationHandle(NotifyType.infoFinishedFuelRep, null);
		}
		
		fillingRepositoryLock.unlock();;
	}
	
}
