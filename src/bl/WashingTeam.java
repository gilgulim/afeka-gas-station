package bl;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import loging.CustomFilter;
import loging.CustomLogFormatter;

public class WashingTeam {
	private static Logger logger = Logger.getLogger("logger");
	static private int idGenerator = 1;
	private int id;
	private Vector<Person> employees;
	
	public WashingTeam(){
		setId(idGenerator++);
		employees = new Vector<Person>();
		
		//Init logger
		FileHandler theFileHandler;
		try {
			
			theFileHandler = new FileHandler(String.format("WashingTeam_%d.txt", this.id), true);
			theFileHandler.setFormatter(new CustomLogFormatter());
			theFileHandler.setFilter(new CustomFilter(this, "id", this.id));
			logger.addHandler(theFileHandler);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addEmployee(String name){
		Person p = new Person(name);
		employees.add(p);
	}
	
	public void addEmployee(Person p){
		employees.add(p);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Vector<Person> getEmployees() {
		return employees;
	}
}
