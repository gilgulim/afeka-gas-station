package bl;

import java.util.concurrent.LinkedBlockingQueue;

public class CleaningTeamsManager implements Runnable{
	private final static int MANUAL_WASH_TIME = 1500; 
	private CleaningServices cleaningServices;
	private LinkedBlockingQueue<WashingTeam> teamsQueue;
	private Thread manualWashQueueThread;
	private boolean isActive;
	
	public CleaningTeamsManager(CleaningServices cleaningServices) {
		this.cleaningServices = cleaningServices;
		manualWashQueueThread = new Thread(this);
		teamsQueue = new LinkedBlockingQueue<WashingTeam>();
	}
	
	@Override
	public void run() {
		GasStation gasStation;
		WashingTeam washingTeam;
		Car car;
		try {
			while(isActive){
				washingTeam = teamsQueue.take();
				car = cleaningServices.getCarFromQueue();
				if(car != null && washingTeam != null){
					sendCarToManualWash(car,washingTeam);	
				}
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void startCleaningTeam(){
		if(!isActive){
			isActive = true;
			manualWashQueueThread.start();
		}
	}
	
	public void stopCleaningTeam(){
		if(isActive){
			isActive = false;
			teamsQueue.notifyAll();
		}
	}
	
	private void sendCarToManualWash(Car car, WashingTeam washingTeam) throws InterruptedException {
		ManualCarWash manualCarWash = new ManualCarWash(car, washingTeam, this);
		manualCarWash.start();
	}
	
	protected void addTeamToQueue(WashingTeam washingTeam) throws InterruptedException{
		teamsQueue.put(washingTeam);
	}

	public static int getManualWashTime() {
		return MANUAL_WASH_TIME;
	}

	public LinkedBlockingQueue<WashingTeam> getTeamsQueue() {
		return teamsQueue;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
}
