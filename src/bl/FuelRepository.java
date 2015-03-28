package bl;

public class FuelRepository {

	private boolean isAvailable;
	private int currentCapacity;
	private int maxCapacity;
	
	public FuelRepository(int currentCapacity, int maxCapacity){
		setCurrentCapacity(currentCapacity);
		setMaxCapacity(maxCapacity);
		setAvailable(true);
	}
	
	public void decreaseFuelAmount(int amount){
		//TODO: complete method - if decrease <0 throws exception;
	}
	
	public void increaseFuelAmount(int amount){
		//TODO: complete method - if increase > maxCapacity throws exception;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public int getCurrentCapacity() {
		return currentCapacity;
	}
	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	
}
