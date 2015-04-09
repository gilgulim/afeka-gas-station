package bl;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;
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
	
	public int getLitersInQueue() {
		return currentLitersInQueue;
	}
	
	public synchronized void addCar(Car car){
		try {
			
			currentLitersInQueue+=car.getFuelAmountRequired();

			carsQueue.put(car);
			logger.log(Level.INFO, String.format("car %d added to fuel pump %d waiting queue.", car.getId(), this.getId()),car);
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
			logger.log(Level.INFO, String.format("Pump %d started.", this.id), this);
		}
	}
	
	public void stopFuelPump(){
		if(isActive){
			//Stopping the thread
			isActive = false;
			
			//Releasing the blocking queue
			carsQueue.notifyAll();
			
			logger.log(Level.INFO, String.format("Pump %d closed",this.getId()), this);
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
					
					logger.log(Level.INFO, String.format("Car %d starts fueling.",pumpingCar.getId()), pumpingCar);
					logger.log(Level.INFO, String.format("pump %d starts fueling car: %s",this.getId(), pumpingCar.getId()), this);
					
					fuelPumped = 0;
					carFuelRequest = pumpingCar.getFuelAmountRequired();
					gasStation = pumpingCar.getGasStaion();
					fuelRep = gasStation.getFuelRep();
					
					while(fuelPumped < carFuelRequest){
						
						try{
							
							//Requesting one litter of fuel from the main repository
							//NOTE: This is a blocking method will wait here if needed
							fuelRep.getOneLitterOfFuel();
							
							
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
					
					logger.log(Level.INFO, String.format("Car %d finished fueling.",pumpingCar.getId()), pumpingCar);
					logger.log(Level.INFO, String.format("pump %d finished fueling car %d", this.getId(), pumpingCar.getId()), this);
					
					//Sending the car back to the gas station dispatcher
					gasStation.AddCarDispatcherQueue(pumpingCar);	
					logger.log(Level.INFO, String.format("Car %d added to dispatcher queue.",pumpingCar.getId()), pumpingCar);

				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
}
