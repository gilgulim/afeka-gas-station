package bl;

public class ManualCarWash extends Thread{
	private Car car;
	private WashingTeam washingTeam;
	private CleaningTeamsManager cleanTeamMngr;
	
	public ManualCarWash(Car car, WashingTeam washingTeam, CleaningTeamsManager cleanTeamMngr){
		this.car = car;
		this.washingTeam = washingTeam;
		this.cleanTeamMngr = cleanTeamMngr;
	}

	@Override
	public void run() {
		try {
			ManualCarWash.sleep(CleaningTeamsManager.getManualWashTime());
			GasStation gasStation = car.getGasStaion();
			
			gasStation.AddCarDispatcherQueue(car);
			cleanTeamMngr.addTeamToQueue(washingTeam);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
