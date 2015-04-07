package bl;

import java.util.concurrent.LinkedBlockingQueue;

public class CleaningServices implements Runnable{
	private LinkedBlockingQueue<WashingTeam> washTeamsQueue;
	private LinkedBlockingQueue<WashingMachine> washMachinesQueue;
	private LinkedBlockingQueue<Car> autoWashCarsQueue;
	private LinkedBlockingQueue<Car> manualWashCarsQueue;
	private int carWashPrice;
	private int autoWashTime;
	private Thread autoWashDispatcherThread;
	private boolean isActive;
	
	public CleaningServices(int carWashPrice, int autoWashTime){
		autoWashDispatcherThread = new Thread(this);
		
		this.carWashPrice = carWashPrice;
		this.autoWashTime = autoWashTime;

		washTeamsQueue = new LinkedBlockingQueue<WashingTeam>();
		washMachinesQueue = new LinkedBlockingQueue<WashingMachine>();
		autoWashCarsQueue = new LinkedBlockingQueue<Car>();
		manualWashCarsQueue = new LinkedBlockingQueue<Car>();
	}
	
	@Override
	public void run(){
		Car car;
		
		try {
			while(isActive){
			car = getCarFromAutoWashQueue();
			autoWashCar(car);
			addCarToManualWashQueue(car);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addWashTeam(WashingTeam wt){
		washTeamsQueue.add(wt);
	}
	
	public void addWashMachine(WashingMachine wm){
		washMachinesQueue.add(wm);
	}

	@Override
	public String toString() {
		return "CleaningServices [washTeams=" + washTeamsQueue + ", washMachines="
				+ washMachinesQueue + ", carsQueue=" + autoWashCarsQueue + "]";
	}

	
	
	private void autoWashCar(Car car) throws InterruptedException{
		Thread.sleep(autoWashTime);
	}
	
	private Car getCarFromAutoWashQueue() throws InterruptedException{
		return autoWashCarsQueue.take();
	}
	
	public void addCarToAutoWashQueue(Car car) throws InterruptedException{
		autoWashCarsQueue.put(car);
	}

	private Car getCarFromManualWashQueue() throws InterruptedException{
		return manualWashCarsQueue.take();
	}
	
	private void addCarToManualWashQueue(Car car) throws InterruptedException{
		manualWashCarsQueue.put(car);
	}
	
	private void manualWashCar(Car car) throws InterruptedException{
		//Car car;	
	}
}
