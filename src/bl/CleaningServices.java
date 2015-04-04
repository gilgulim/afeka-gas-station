package bl;

import sun.misc.Queue;

import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

public class CleaningServices implements Runnable {
	private LinkedBlockingQueue<WashingTeam> washTeams;
	private LinkedBlockingQueue<WashingMachine> washMachines;
	private LinkedBlockingQueue<Car> carsQueue;
	private boolean isActive;
	private static final int SECONDS = 1000; 
	
	public CleaningServices(){
		washTeams = new LinkedBlockingQueue<WashingTeam>();
		washMachines = new LinkedBlockingQueue<WashingMachine>();
		carsQueue = new LinkedBlockingQueue<Car>();
	}
	public void addWashTeam(WashingTeam wt){
		washTeams.add(wt);
	}
	
	public void addWashMachine(WashingMachine wm){
		washMachines.add(wm);
	}
		
	public LinkedBlockingQueue<WashingTeam> getWashTeams() {
		return washTeams;
	}
	public LinkedBlockingQueue<WashingMachine> getWashMachines() {
		return washMachines;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	@Override
	public String toString() {
		return "CleaningServices [washTeams=" + washTeams + ", washMachines="
				+ washMachines + ", carsQueue=" + carsQueue + "]";
	}
	@Override
	public void run() {
		Car car;
		
		try {
			while(true){
			car = getCarFromAutoWashQueue();
			autoWashCar(car);
			addCarToManualWashQueue(car);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void autoWashCar(Car car) throws InterruptedException{
		sleep(autoWashTimeToClean*SECONDS);
	}
	

	
	private Car getCarFromAutoWashQueue() throws InterruptedException{
		return autoWashQueue.take();
	}
	
	public void addCarToAutoWashQueue(Car car) throws InterruptedException{
		autoWashQueue.put(car);
	}

	private Car getCarFromManualWashQueue() throws InterruptedException{
		return manualWashQueue.take();
	}
	
	private void addCarToManualWashQueue(Car car) throws InterruptedException{
		manualWashQueue.put(car);
	}
	
	private void manualWashCar(Car car) throws InterruptedException{
		//Car car;	
	}
}
