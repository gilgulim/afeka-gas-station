package bl;

import java.util.concurrent.LinkedBlockingQueue;

public class CleaningServices implements Runnable{
	private ManualWashManager manualCleanMngr;
	private CleaningTeamsManager cleanTeamMngr;
	private LinkedBlockingQueue<WashingMachine> washMachinesQueue;
	private LinkedBlockingQueue<Car> autoWashCarsQueue;
	private int carWashPrice;
	private int autoWashTime;
//	private Thread autoWashDispatcherThread;
	private boolean isActive;
	
	
	public CleaningServices(int carWashPrice, int autoWashTime){
		manualCleanMngr = new ManualWashManager(this);
		cleanTeamMngr = new CleaningTeamsManager(this, manualCleanMngr);
//		autoWashDispatcherThread = new Thread(this);
		
		this.carWashPrice = carWashPrice;
		this.autoWashTime = autoWashTime;

		washMachinesQueue = new LinkedBlockingQueue<WashingMachine>();
		autoWashCarsQueue = new LinkedBlockingQueue<Car>();
	}
	
	@Override
	public void run(){
		Car car;
		
		try {
			while(isActive){
			car = getCarFromAutoWashQueue();
			autoWashCar(car);
			manualCleanMngr.addCarToQueue(car);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addWashTeam(WashingTeam washingTeam) throws InterruptedException{
		cleanTeamMngr.addTeamToQueue(washingTeam);
	}
	
	public void addWashMachine(WashingMachine wm){
		washMachinesQueue.add(wm);
	}

	@Override
	public String toString() {
		return "CleaningServices [washTeams=" + washMachinesQueue + ", carsQueue=" + autoWashCarsQueue + "]";
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
	
	public int getCurrentWaitingTime(){
		int result, autoWashQueueSize, manualWashQueueSize, autoWashTime, manualWashTime, teamsQueueSize;
		
		autoWashQueueSize = autoWashCarsQueue.size();
		manualWashQueueSize = manualCleanMngr.getCarsQueue().size();
		teamsQueueSize = cleanTeamMngr.getTeamsQueue().size();
		autoWashTime = this.autoWashTime;
		manualWashTime = CleaningTeamsManager.getManualWashTime();
		
		result = (autoWashQueueSize * autoWashTime) + (manualWashQueueSize / teamsQueueSize)*manualWashTime;
		return result;
	}
}
