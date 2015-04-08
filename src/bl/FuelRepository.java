package bl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import bl.Exceptions.FillingRepositoryMaxLimitException;
import bl.Exceptions.FuelRepositoryEmptyException;
import bl.Exceptions.LowFuelAmountException;

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
	public void getOneLitterOfFuel() throws LowFuelAmountException, FuelRepositoryEmptyException, InterruptedException
	{
		fillingRepositoryLock.lock();
		try{
			float lowCapacityBorder = (float)(LOW_BORDER_PERCENTAGE / 100 * maxCapacity);
			if(currentCapacity > 0){
				if(lowCapacityBorder >= currentCapacity){
					
					logger.warning("Fuel amount in fuel repository is lower than: " + lowCapacityBorder);
					
					currentCapacity--;
					
					//Waiting here to simulate pumping one litter of fuel
					Thread.sleep(this.pumpingPacePerLiter);
					
					throw new LowFuelAmountException();
				}
				currentCapacity--;
			}else{
				logger.warning("Fuel repository is empty!");
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
		}
		
		fillRepositoryThread.start();
	}
	
	@Override
	public void run() {
		
		fillingRepositoryLock.lock();
		
		int fuelAmount = 0;
		try {
			while(fuelAmount < fillFuelAmount){
				Thread.sleep(FILLING_REPOSITORY_PACE);
				logger.log(Level.INFO, "Filling fuel repository -%d%-",fuelAmount / fillFuelAmount *100);
				fuelAmount++;
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fillingRepositoryLock.unlock();;
	}
	
}
