package bl;
import java.util.Vector;

public class GasStation {
	
	private String name;
	private Vector<FuelPump> pumps;
	private CleaningServices cleaningSrv;
	private FuelRepository fuelRep;
	
	private float fuelPricePerLiter;
	private int pumpingPacePerLiter;
	
	public GasStation (String name, float fuelPricePerLiter, int pumpingPacePerLiter){
		this.name = name;
		this.fuelPricePerLiter = fuelPricePerLiter;
		this.pumpingPacePerLiter = pumpingPacePerLiter;
		pumps = new Vector<FuelPump>();
	}
	
	public void AddCarDispatcherQueue(Car car){
		
		//TODO: Implement this method
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
	public int getPumpingPacePerLiter(){
		return pumpingPacePerLiter;
	}

	@Override
	public String toString() {
		return "GasStation [name=" + name + ", pumps=" + pumps + ", cleaningSrv=" + cleaningSrv + ", fuelRep="
				+ fuelRep + ", fuelPricePerLiter=" + fuelPricePerLiter + "]";
	}
}
