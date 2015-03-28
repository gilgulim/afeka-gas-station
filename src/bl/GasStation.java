package bl;

import java.util.Vector;

public class GasStation {
	private int id;
	private String name;
	private Vector<FuelPump> pumps;
	private CleaningServices cleaningSrv;
	private int fuelPricePerLiter;
	private int carWashPrice;
	private int autoWashTimeToClean;
	
	public void addPump(FuelPump fp){
		pumps.add(fp);
	}
	
	public void setCleanSrv(CleaningServices cs){
		setCleaningSrv(cs);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Vector<FuelPump> getPumps() {
		return pumps;
	}

	public CleaningServices getCleaningSrv() {
		return cleaningSrv;
	}
	public void setCleaningSrv(CleaningServices cleaningSrv) {
		this.cleaningSrv = cleaningSrv;
	}
	public int getFuelPricePerLiter() {
		return fuelPricePerLiter;
	}
	public void setFuelPricePerLiter(int fuelPricePerLiter) {
		this.fuelPricePerLiter = fuelPricePerLiter;
	}
	public int getCarWashPrice() {
		return carWashPrice;
	}
	public void setCarWashPrice(int carWashPrice) {
		this.carWashPrice = carWashPrice;
	}
	public int getAutoWashTimeToClean() {
		return autoWashTimeToClean;
	}
	public void setAutoWashTimeToClean(int autoWashTimeToClean) {
		this.autoWashTimeToClean = autoWashTimeToClean;
	}	
	
}
