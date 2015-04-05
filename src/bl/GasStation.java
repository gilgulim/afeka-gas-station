package bl;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingDeque;

public class GasStation implements Runnable {
	
	private String name;
	private float fuelPricePerLiter;
	private int pumpingPacePerLiter;
	private Vector<FuelPump> pumpsVec;
	
	private CleaningServices cleaningSrv;
	private FuelRepository fuelRep;
	private LinkedBlockingDeque<Car> dispachQueue;
	
	private Thread carsDispatchThread;
	private boolean isActive;
	
	
	public GasStation (String name, float fuelPricePerLiter, int pumpingPacePerLiter){
		this.name = name;
		this.fuelPricePerLiter = fuelPricePerLiter;
		this.pumpingPacePerLiter = pumpingPacePerLiter;
		this.isActive = false;
		
		pumpsVec = new Vector<FuelPump>();
		
		carsDispatchThread = new Thread(this);
		carsDispatchThread.setName("CarsDispatchThread");
		
		dispachQueue = new LinkedBlockingDeque<Car>();
	}
	
	public void startGasStation(){
		if(!isActive){
			isActive = true;
			carsDispatchThread.start();
		}
	}
	
	public void stopGasStation(){
		if(isActive){
			isActive = false;
			
		}
	}
	
	public void AddCarDispatcherQueue(Car car){
		
		//TODO: Implement this method
	}
	
	public void addPump(FuelPump fp){
		pumpsVec.add(fp);
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
	public int getPumpingPacePerLiter(){
		return pumpingPacePerLiter;
	}

	@Override
	public String toString() {
		return "GasStation [name=" + name + ", pumps=" + pumpsVec + ", cleaningSrv=" + cleaningSrv + ", fuelRep="
				+ fuelRep + ", fuelPricePerLiter=" + fuelPricePerLiter + "]";
	}

	@Override
	public void run() {
		
		while(isActive){
			
			try {
				
				Car car = dispachQueue.take();
				if(car != null){
					
					//If the car request both service make a decision about the fastest route
					if(car.isRequiresFuel() && car.isRequiresWash()){
						
					}else if(car.isRequiresFuel()){ //Only fuel
						
						if (car.getPumpIndex() < pumpsVec.size()){
							
						}else{
							//TODO: Throw an exception or an error message to the log that there is no such pump.
						}
						
					}else{ //Only wash
						
					}
					
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
