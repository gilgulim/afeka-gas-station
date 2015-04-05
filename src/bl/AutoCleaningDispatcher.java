package bl;

import java.util.concurrent.LinkedBlockingQueue;

public class AutoCleaningDispatcher extends Thread{
	private LinkedBlockingQueue<Car> autoWashQueue;
	private LinkedBlockingQueue<Car> manualWashQueue;
	private LinkedBlockingQueue<WashingTeam> washingTeamQueue;
	private int autoWashTimeToClean;
	private GasStation gasStation;

	
	public AutoCleaningDispatcher(GasStation gasStation){
		autoWashQueue = new LinkedBlockingQueue<Car>();
		manualWashQueue = new LinkedBlockingQueue<Car>();
		washingTeamQueue = new LinkedBlockingQueue<WashingTeam>();
		
		this.gasStation = gasStation;
		autoWashTimeToClean = this.gasStation.getAutoWashTimeToClean();
		for (int i=0; i<gasStation.getCleaningSrv().getWashTeams().size(); i++){
			try {
				washingTeamQueue.put(gasStation.getCleaningSrv().getWashTeams().elementAt(i));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void run(){
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
