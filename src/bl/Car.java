package bl;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import loging.CustomFilter;
import loging.CustomLogFormatter;



public class Car {
	
	private static Logger logger = Logger.getLogger("logger");
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
		
		//Init logger
		FileHandler theFileHandler;
		try {
			
			theFileHandler = new FileHandler(String.format("Car_%d.txt", this.id), true);
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
