package bl;

import java.util.Queue;
import java.util.Vector;

public class GasStation {
	private String name;
	private Vector<FuelPump> pumps;
	private Vector<Car> cars;
	private CleaningServices cleaningSrv;
	private FuelRepository fuelRep;
	private float fuelPricePerLiter;
	private int carWashPrice;
	private int autoWashTimeToClean;
	
	public GasStation (String name){
		setName(name);
		pumps = new Vector<FuelPump>();
		cars = new  Vector<Car>();
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

	public FuelRepository getFuelRep() {
		return fuelRep;
	}
	public void setFuelRep(FuelRepository fuelRep) {
		this.fuelRep = fuelRep;
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

	@Override
	public String toString() {
		return "GasStation [name=" + name + ", pumps=" + pumps + ", carsQueue="
				+ cars + ", cleaningSrv=" + cleaningSrv + ", fuelRep="
				+ fuelRep + ", fuelPricePerLiter=" + fuelPricePerLiter
				+ ", carWashPrice=" + carWashPrice + ", autoWashTimeToClean="
				+ autoWashTimeToClean + "]";
	}

	public void addCar (Car car) {
		this.cars.addElement(car);
	}
	
	
}
