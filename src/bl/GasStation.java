package bl;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import loging.CustomFilter;
import loging.CustomLogFormatter;

public class GasStation implements Runnable {
	
	private static Logger logger = Logger.getLogger("logger");
	private final String MAIN_LOG_FILE_NAME = "GasStation.txt";
	private String name;
	private float fuelPricePerLiter;
	private Vector<FuelPump> pumpsVec;
	private CleaningServices cleaningSrv;
	private FuelRepository fuelRep;
	private LinkedBlockingDeque<Car> dispachQueue;
	
	private Thread carsDispatchThread;
	private boolean isActive;
	
	
	public GasStation (String name, float fuelPricePerLiter){
		logger.setUseParentHandlers(false);
		this.name = name;
		this.fuelPricePerLiter = fuelPricePerLiter;
		this.isActive = false;
		
		pumpsVec = new Vector<FuelPump>();
		dispachQueue = new LinkedBlockingDeque<Car>();
		
		carsDispatchThread = new Thread(this);
		carsDispatchThread.setName("CarsDispatchThread");
		
		//Setting main log file handler
		FileHandler theFileHandler;
		try {
			
			theFileHandler = new FileHandler(MAIN_LOG_FILE_NAME, true);
			theFileHandler.setFormatter(new CustomLogFormatter());
			logger.addHandler(theFileHandler);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startGasStation(){
		if(!isActive){
			
			logger.info("Gas-Station started.");
			
			isActive = true;
			//Starting the main car dispatcher thread
			carsDispatchThread.start();
			
			
			//Starting each fuel pump
			for(FuelPump fuelPump : pumpsVec){
				fuelPump.startFuelPump();
			}
			
			
			//Starting the cleaning services
			if(cleaningSrv != null){
				cleaningSrv.startCleaningSrv();
			}
		}
	}
	
	public void stopGasStation(){
		if(isActive){
			isActive = false;
			
		}
	}
	
	public synchronized void addCarDispatcherQueue(Car car){
		logger.log(Level.INFO, String.format("Car %d arrived to gas station and added to dispatcher queue.", car.getId()),car);
		dispachQueue.add(car);
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
	

	@Override
	public String toString() {
		return "GasStation [name=" + name + ", pumps=" + pumpsVec + ", cleaningSrv=" + cleaningSrv + ", fuelRep="
				+ fuelRep + ", fuelPricePerLiter=" + fuelPricePerLiter + "]";
	}

	@Override
	public void run() {
		
		FuelPump fuelPump;
		
		while(isActive){

			try {
				
				Car car = dispachQueue.take();
		
				if(car != null){
					logger.log(Level.INFO, String.format("car %d removed from dispatcher queue.", car.getId()),car);
					//If the car request both service make a decision about the fastest route
					if(car.isRequiresFuel() && car.isRequiresWash()){
						
						fuelPump = getCarFuelPump(car);
						if(fuelPump != null){
							
							int waitInPump = fuelPump.getLitersInQueue() *  fuelRep.getPumpingPacePerLiter();
							int waitInWash = cleaningSrv.getCurrentWaitingTime();
							
							if(waitInPump <= waitInWash){
								car.setRequiresFuel(false);
								fuelPump.addCar(car);
								
							}else{
								car.setRequiresWash(false);
								cleaningSrv.addCarToAutoWashQueue(car);
							}
							
						}
						else{
							//TODO: Throw an exception or an error message to the log that there is no such pump.
						}
						
						
					}else if(car.isRequiresFuel()){ //Only fuel
						
						fuelPump = getCarFuelPump(car);
						if(fuelPump != null){
							 
							car.setRequiresFuel(false);
							fuelPump.addCar(car);
							 
						}else{
							//TODO: Throw an exception or an error message to the log that there is no such pump.
						}
						
						
					}else if(car.isRequiresWash()){ //Only wash
						car.setRequiresWash(false);
						cleaningSrv.addCarToAutoWashQueue(car);
					}else{
						//Nothing is required the car is leaving the station.
						logger.log(Level.INFO, String.format("Car %d left the gas station.",car.getId()), car);
					}
					
				
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	
	
	private FuelPump getCarFuelPump(Car car){
		
		int pumpIndex = car.getPumpIndex();
		if (pumpIndex < pumpsVec.size()){
			return pumpsVec.get(pumpIndex);
		}
		return null;
	}
}
