package bl;

import java.util.Queue;
import java.util.Vector;

public class CleaningServices {
	private Vector<WashingTeam> washTeams;
	private Vector<WashingMachine> washMachines;
	private Queue<Car> carsQueue;
	
	
	public void addWashTeam(WashingTeam wt){
		washTeams.add(wt);
	}
	
	public void addWashMachine(WashingMachine wm){
		washMachines.add(wm);
	}
	
	public void addCarToQueue(Car c){
		carsQueue.add(c);
	}
	
	public Car pullCarFromQueue(){
		return carsQueue.poll();
	}
	
	public Vector<WashingTeam> getWashTeams() {
		return washTeams;
	}
	public Vector<WashingMachine> getWashMachines() {
		return washMachines;
	}
	
	
}
