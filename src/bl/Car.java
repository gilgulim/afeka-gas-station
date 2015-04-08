package bl;



public class Car {
	private int id;
	private boolean isRequiresWash;
	private boolean isRequiresFuel;
	private int fuelAmountRequired;
	private int pumpIndex;
	private GasStation gasStaion;
	public Car(int id, boolean requiresWash, boolean requiresFuel, GasStation gasStation){
		setId(id);
		setRequiresWash(requiresWash);
		setRequiresFuel(requiresFuel);
		setGasStaion(gasStation);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isRequiresWash() {
		return isRequiresWash;
	}
	public void setRequiresWash(boolean isRequiresWash) {
		this.isRequiresWash = isRequiresWash;
	}
	public boolean isRequiresFuel() {
		return isRequiresFuel;
	}
	public void setRequiresFuel(boolean isRequiresFuel) {
		this.isRequiresFuel = isRequiresFuel;
	}
	public int getFuelAmountRequired() {
		return fuelAmountRequired;
	}
	public void setFuelAmountRequired(int fuelAmountRequired) {
		this.fuelAmountRequired = fuelAmountRequired;
	}

	public int getPumpIndex() {
		return pumpIndex;
	}

	public void setPumpIndex(int pumpIndex) {
		this.pumpIndex = pumpIndex;
	}

	public GasStation getGasStaion() {
		return gasStaion;
	}

	public void setGasStaion(GasStation gasStaion) {
		this.gasStaion = gasStaion;
	}
	
	@Override
	public String toString(){
		return "Car[FuelAmountRequired: " + fuelAmountRequired +" PumpIndex: "+ pumpIndex + " ReqFuel: " + isRequiresFuel + " ReqWash: " + isRequiresWash + "]";
	}
	
}
