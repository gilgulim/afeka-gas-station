package bl;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import loging.CustomFilter;
import loging.CustomLogFormatter;
import bl.Exceptions.FuelRepositoryEmptyException;
import bl.Exceptions.LowFuelAmountException;


public class FuelPump implements Runnable
{
	private static Logger logger = Logger.getLogger("logger");
	private static int idGenerator = 1;
	private LinkedBlockingQueue<Car> carsQueue;
	private Thread pumpQueueThread;
	private int id;
	private boolean isActive;
	private int currentLitersInQueue;
	
	
	public FuelPump(){
		this.id = idGenerator++;
		isActive = false;
		carsQueue = new LinkedBlockingQueue<Car>();
		pumpQueueThread = new Thread(this);
		currentLitersInQueue = 0;
		
		//Init logger
		FileHandler theFileHandler;
		try {
			
			theFileHandler = new FileHandler(String.format("Pump_%d.txt", this.id), true);
			theFileHandler.setFormatter(new CustomLogFormatter());
			theFileHandler.setFilter(new CustomFilter(this, "id", id));
			logger.addHandler(theFileHandler);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	
	public int getLitersInQueue() {
		return currentLitersInQueue;
	}
	
	public synchronized void addCar(Car car){
		try {
			
			currentLitersInQueue+=car.getFuelAmountRequired();

			carsQueue.put(car);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void startFuelPump(){
		if(!isActive){
			isActive = true;
			pumpQueueThread.start();
			logger.info("FuelPump started");
		}
	}
	
	public void stopFuelPump(){
		if(isActive){
			//Stopping the thread
			isActive = false;
			
			//Releasing the blocking queue
			carsQueue.notifyAll();
			
			logger.info("FuelPump closed");
		}
	}

	@Override
	public String toString() {
		return "FuelPump [id=" + id + ", isActive=" + isActive + "]";
	}

	@Override
	public void run() {
		
		int fuelPumped, carFuelRequest;
		GasStation gasStation;
		FuelRepository fuelRep;
		while(isActive){
			
			try {
				
				
				//Will wait here if the queue is empty
				Car pumpingCar = carsQueue.take();
				if(pumpingCar != null){
					
					logger.info(String.format("Start fueling car: %s", pumpingCar));
					
					fuelPumped = 0;
					carFuelRequest = pumpingCar.getFuelAmountRequired();
					gasStation = pumpingCar.getGasStaion();
					fuelRep = gasStation.getFuelRep();
					
					while(fuelPumped < carFuelRequest){
						
						try{
							
							//Requesting one litter of fuel from the main repository
							//NOTE: This is a blocking method will wait here if needed
							fuelRep.getOneLitterOfFuel();
							
							//Waiting here to simulate pumping one litter of fuel
							Thread.sleep(gasStation.getPumpingPacePerLiter());
							
							//Decrease the amount of left fuel by one litter
							++fuelPumped;
							
						}catch(LowFuelAmountException ex){
							
							//TODO: Do something with this error. Maybe log?
						}catch(FuelRepositoryEmptyException ex){
							//Out of fuel in the gas station
						}
					}
		
					currentLitersInQueue -= fuelPumped;
					pumpingCar.setFuelAmountRequired(carFuelRequest - fuelPumped);
					
					logger.info(String.format("Finished fueling car: %s", pumpingCar));
					
					//Sending the car back to the gas station dispatcher
					gasStation.AddCarDispatcherQueue(pumpingCar);	

				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
}
