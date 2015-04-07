package bl;

import java.util.concurrent.LinkedBlockingQueue;

public class ManualWashManager{

	private CleaningServices cleaningServices;
	private LinkedBlockingQueue<Car> carsQueue;
	
	public ManualWashManager(CleaningServices cleaningServices) {
		this.cleaningServices = cleaningServices; 
		
		carsQueue = new LinkedBlockingQueue<Car>();
	}
	
	protected Car getCarFromQueue() throws InterruptedException{
		return carsQueue.take();
	}
	
	protected void addCarToQueue(Car car) throws InterruptedException{
		carsQueue.put(car);
	}

	public LinkedBlockingQueue<Car> getCarsQueue() {
		return carsQueue;
	}

}
