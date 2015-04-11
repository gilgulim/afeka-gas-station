package bl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ManualCarWash extends Thread{
	private static Logger logger = Logger.getLogger("logger");
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
			GasStation gasStation = car.getGasStaion();
			
			logger.log(Level.INFO, String.format("WashingTeam %d began wash car %d.", washingTeam.getId(), car.getId()),washingTeam);
			logger.log(Level.INFO, String.format("Car %d began washing by WashingTeam %d.", car.getId(), washingTeam.getId()),car);
			logger.log(Level.INFO, String.format("Car %d about to start manual wash.", car.getId()),gasStation.getCleaningSrv());
			
			ManualCarWash.sleep(CleaningTeamsManager.getManualWashTime());
			
			logger.log(Level.INFO, String.format("Car %d finished manual wash.", car.getId()),gasStation.getCleaningSrv());
			logger.log(Level.INFO, String.format("Car %d finished manual washing.", car.getId()),car);
			gasStation.addCarDispatcherQueue(car);
			cleanTeamMngr.addTeamToQueue(washingTeam);
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
