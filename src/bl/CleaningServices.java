package bl;

import sun.misc.Queue;
import java.util.Vector;

public class CleaningServices {
	private Vector<WashingTeam> washTeams;
	private Vector<WashingMachine> washMachines;
	private Queue<Car> carsQueue;
	private int carWashPrice;
	private int autoWashTime;
	
	
	public CleaningServices(int carWashPrice, int autoWashTime){
		this.carWashPrice = carWashPrice;
		this.autoWashTime = autoWashTime;

		washTeams = new Vector<WashingTeam>();
		washMachines = new Vector<WashingMachine>();
		carsQueue = new Queue<Car>();
	}
	public void addWashTeam(WashingTeam wt){
		washTeams.add(wt);
	}
	
	public void addWashMachine(WashingMachine wm){
		washMachines.add(wm);
	}
		
	public Vector<WashingTeam> getWashTeams() {
		return washTeams;
	}
	public Vector<WashingMachine> getWashMachines() {
		return washMachines;
	}

	@Override
	public String toString() {
		return "CleaningServices [washTeams=" + washTeams + ", washMachines="
				+ washMachines + ", carsQueue=" + carsQueue + "]";
	}
	
	
}
