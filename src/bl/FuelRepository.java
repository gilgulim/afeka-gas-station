package bl;

import java.util.logging.Logger;

import bl.Exceptions.FuelRepositoryEmptyException;
import bl.Exceptions.LowFuelAmountException;

public class FuelRepository {
	
	private static Logger logger = Logger.getLogger("logger");
	
	private final float LOW_BORDER_PERCENTAGE = 20;
	private boolean isAvailable;
	private int currentCapacity;
	private int maxCapacity;
	
	public FuelRepository(int currentCapacity, int maxCapacity){
		this.maxCapacity = maxCapacity;
		this.currentCapacity = currentCapacity;
		
		if(maxCapacity > 0){
			this.isAvailable = true;
		}else{
			this.isAvailable = false;
		}
		
		logger.info("Fuel-Repository started.");
	}
	public synchronized void getOneLitterOfFuel() throws LowFuelAmountException, FuelRepositoryEmptyException
	{
		float lowCapacityBorder = (float)(LOW_BORDER_PERCENTAGE / 100 * maxCapacity);
		if(currentCapacity > 0){
			if(lowCapacityBorder >= currentCapacity){
				logger.warning("Fuel amount in fuel repository is lower than: " + lowCapacityBorder);
				currentCapacity--;
				throw new LowFuelAmountException();
			}
			currentCapacity--;
		}else{
			logger.warning("Fuel repository is empty!");
			throw new FuelRepositoryEmptyException();
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
	
}
