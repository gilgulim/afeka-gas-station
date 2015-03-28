package bl;

public class WashingMachine {
	static private int idGenerator = 1;
	private int id;
	private boolean isBusy;
	
	public WashingMachine(){
		setId(idGenerator++);
		setBusy(false);
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
	
}
