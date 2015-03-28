package bl;

import java.util.Vector;

public class GasStation {
	private String name;
	private Vector<FuelPump> pumps;
	private CleaningServices cleaningSrv;
	private float fuelPricePerLiter;
	private int carWashPrice;
	private int autoWashTimeToClean;
	
	public GasStation (String name){
		setName(name);
	}
	
	public void addPump(FuelPump fp){
		pumps.add(fp);
	}
	
	public void setCleanSrv(CleaningServices cs){
		setCleaningSrv(cs);
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
	public float getFuelPricePerLiter() {
		return fuelPricePerLiter;
	}
	public void setFuelPricePerLiter(float f) {
		this.fuelPricePerLiter = f;
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
