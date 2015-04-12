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
	
	private final int FILLING_REPOSITORY_PACE = 80;
	private final float LOW_BORDER_PERCENTAGE = 20;
	private boolean isAvailable;
	private int currentCapacity;
	private int maxCapacity;
	private int pumpingPacePerLiter;
	private Lock fillingRepositoryLock;
	private Thread fillRepositoryThread;
	private int fillFuelAmount;
	private boolean lowFuelwarningFlag = true;
	private boolean emptyFuelwarningFlag = true;
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
	public boolean getOneLitterOfFuel() throws FuelRepositoryEmptyException, InterruptedException
	{
		
		
		fillingRepositoryLock.lock();
		try{
			float lowCapacityBorder = (float)(LOW_BORDER_PERCENTAGE / 100 * maxCapacity);
			if(currentCapacity > 0){
				if(lowCapacityBorder >= currentCapacity){
					
					if(lowFuelwarningFlag){
						logger.warning("Fuel amount in fuel repository is lower than: " + lowCapacityBorder);
						lowFuelwarningFlag = false;
						if(notifyHandler!= null){
							notifyHandler.notificationHandle(NotifyType.WARNING_LOW_FUEL, null);
						}
					}
					
					
					currentCapacity--;
					
					//Waiting here to simulate pumping one litter of fuel
					Thread.sleep(this.pumpingPacePerLiter);
					return true;
					
				}else{
					lowFuelwarningFlag=true;
				}
				currentCapacity--;
			}else{
				if(emptyFuelwarningFlag){
					logger.warning("Fuel repository is empty!");
					if(notifyHandler!= null){
						notifyHandler.notificationHandle(NotifyType.WARNING_FUEL_REP_EMPTY, null);
					}
					
					emptyFuelwarningFlag = false;
					
					throw new FuelRepositoryEmptyException();	
					
					
				}
				
			}
		}
		finally{
			fillingRepositoryLock.unlock();
		}
		return false;	
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
			emptyFuelwarningFlag = true;
		}
	}
	public void setNotificationHandler(NotificationsHandler notifyHandler){
		this.notifyHandler = notifyHandler; 
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
					notifyHandler.notificationHandle(NotifyType.INFO_FUEL_REP_STATUS, currentCapacity);
				}
			}
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(notifyHandler != null){
			notifyHandler.notificationHandle(NotifyType.INFO_FUEL_REP_DONE_FUELING, null);
		}
		
		fillingRepositoryLock.unlock();;
	}
	
}
