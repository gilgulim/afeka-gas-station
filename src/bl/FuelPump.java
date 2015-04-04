package bl;

import java.util.concurrent.LinkedBlockingQueue;
import bl.Exceptions.LowFuelAmountException;


public class FuelPump implements Runnable
{
	static private int idGenerator = 1;
	private LinkedBlockingQueue<Car> carsQueue;
	private Thread pumpQueueThread;
	private int id;
	private boolean isActive;
	
	
	public FuelPump(){
		this.id = idGenerator++;
		carsQueue = new LinkedBlockingQueue<Car>();
		pumpQueueThread = new Thread(this);
	}	
	
	public int getId() {
		return id;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void startFuelPump(){
		if(!isActive){
			pumpQueueThread.start();
		}
	}
	
	public void stopFuelPump(){
		if(isActive){
			//Stopping the thread
			isActive = false;
			
			//Releasing the blocking queue
			carsQueue.notifyAll();
		}
	}

	@Override
	public String toString() {
		return "FuelPump [id=" + id + ", isActive=" + isActive + "]";
	}

	@Override
	public void run() {
		
		int fuelAmount;
		GasStation gasStation;
		FuelRepository fuelRep;
		while(isActive){
			
			try {
				
				//Will wait here if the queue is empty
				Car pumpingCar = carsQueue.take();
				
				fuelAmount = pumpingCar.getFuelAmountRequired();
				gasStation = pumpingCar.getGasStaion();
				fuelRep = gasStation.getFuelRep();
				
				while(fuelAmount > 0){
					
					try{
						
						//Requesting one litter of fuel from the main repository
						//NOTE: This is a blocking method will wait here if needed
						fuelRep.getOneLitterOfFuel();
						
						//Waiting here to simulate pumping one litter of fuel
						Thread.sleep(gasStation.getPumpingPacePerLiter());
						
						//Decrease the amount of left fuel by one litter
						--fuelAmount;
						
					}catch(LowFuelAmountException ex){
						//TODO: Do something with this error. Maybe log?
					}
				}
				
				
				//Sending the car back to the gas station dispatcher
				gasStation.AddCarDispatcherQueue(pumpingCar);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
}
