package bl;

import java.util.Vector;

public class WashingTeam {
	static private int idGenerator = 1;
	private int id;
	private boolean isBusy;
	private Vector<Person> employees;
	
	public WashingTeam(){
		setId(idGenerator++);
		setBusy(false);
	}
	public void addEmployee(String name){
		Person p = new Person(name);
		employees.add(p);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isBusy() {
		return isBusy;
	}
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
	public Vector<Person> getEmployees() {
		return employees;
	}

}
