package bl;

import java.util.concurrent.LinkedBlockingQueue;

public class CleaningTeamsManager implements Runnable{
	
	private CleaningServices cleaningServices;
	private LinkedBlockingQueue<WashingTeam> teamsQueue;
	private boolean isActive;
	
	public CleaningTeamsManager(CleaningServices cleaningServices) {
		this.cleaningServices = cleaningServices;
		
		teamsQueue = new LinkedBlockingQueue<WashingTeam>();
	}
	
	@Override
	public void run() {
		WashingTeam washingTeam;
		
		try {
			while(isActive){
			washingTeam = getTeamFromQueue();
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private WashingTeam getTeamFromQueue() throws InterruptedException{
		return teamsQueue.take();
	}
	
	protected void addTeamToQueue(WashingTeam washingTeam) throws InterruptedException{
		teamsQueue.put(washingTeam);
	}
}
