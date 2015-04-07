package bl;

import java.util.concurrent.LinkedBlockingQueue;

public class CleaningTeamsManager implements Runnable{
	private final static int MANUAL_WASH_TIME = 1500; 
	private CleaningServices cleaningServices;
	private ManualWashManager manualWashMngr;
	private LinkedBlockingQueue<WashingTeam> teamsQueue;
//	private Thread manualWashDispatcherThread;
	private boolean isActive;
	
	public CleaningTeamsManager(CleaningServices cleaningServices, ManualWashManager manualWashMngr) {
		this.cleaningServices = cleaningServices;
		this.manualWashMngr = manualWashMngr;
//		manualWashDispatcherThread = new Thread(this);
		teamsQueue = new LinkedBlockingQueue<WashingTeam>();
	}
	
	@Override
	public void run() {
		GasStation gasStation;
		WashingTeam washingTeam;
		Car car;
		try {
			while(isActive){
			washingTeam = getTeamFromQueue();
			car = manualWashMngr.getCarFromQueue();
			gasStation = car.getGasStaion();
			manualWashCar(car,washingTeam);
			addTeamToQueue(washingTeam);
			gasStation.AddCarDispatcherQueue(car);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void manualWashCar(Car car, WashingTeam washingTeam) throws InterruptedException {
		Thread.sleep(MANUAL_WASH_TIME);
	}

	private WashingTeam getTeamFromQueue() throws InterruptedException{
		return teamsQueue.take();
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
	
}
