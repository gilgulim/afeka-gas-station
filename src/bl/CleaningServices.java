package bl;

import java.util.Queue;
import java.util.Vector;

public class CleaningServices {
	private Vector<WashingTeam> washTeams;
	private Vector<WashingMachine> washMachines;
	private Vector<Car> cars;
	
	
	public CleaningServices(){
		washTeams = new Vector<WashingTeam>();
		washMachines = new Vector<WashingMachine>();
		cars = new Vector<Car>();
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
				+ washMachines + ", carsQueue=" + cars + "]";
	}
	
	
}
