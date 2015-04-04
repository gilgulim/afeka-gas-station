package bl;

import bl.Exceptions.LowFuelAmountException;

public class FuelRepository {

	private final int LOW_BORDER_PERCENTAGE = 20;
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
	}
	public synchronized void getOneLitterOfFuel() throws LowFuelAmountException
	{
		int lowCapacityBorder = LOW_BORDER_PERCENTAGE / 100 * maxCapacity; 
		if(lowCapacityBorder >= currentCapacity){
			currentCapacity--;
			throw new LowFuelAmountException();
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
