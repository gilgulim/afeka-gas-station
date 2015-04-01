package bl;

public class FuelPump {
	static private int idGenerator = 1;
	private int id;
	private boolean isActive;
	private boolean isBusy;
	
	public FuelPump(){
		setId(idGenerator++);
		setActive(true);
		setBusy(false);
	}
	
	public void fuelCar(int carId){
		//TODO: complete method
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isBusy() {
		return isBusy;
	}
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	@Override
	public String toString() {
		return "FuelPump [id=" + id + ", isActive=" + isActive + ", isBusy="
				+ isBusy + "]";
	}
	
	
}
