package bl;

public class Person {
	static private int idGenerator = 1000;
	private int id;
	private String name;
	
	public Person(String name) {
		setId(idGenerator++);
		setName(name);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
	
	
}
