package Views;

import java.util.Scanner;

public class ConsoleMenu {
	private Scanner s;
	private boolean isRunning = true;
	
	public ConsoleMenu(){
		s = new Scanner(System.in);
	}

	public void startMenu(){
		while(isRunning){
			System.out.println("Choose one of the following options:");
			System.out.println("1. add car to Gas station.");
			System.out.println("2. add fuel to main repository.");
			System.out.println("3. show statistics.");
			System.out.println("4. close the Gas station.");
			
			try{
				int menuSelection = s.nextInt();
				if(menuSelection > 4 || menuSelection < 1){
					System.out.println("invalid input, numbers should be between 1 and 4.");
				}
				switch(menuSelection){
					case 1: addCarToGasStation();
						break;
					case 2: addFuelToMainRepository();
						break;
					case 3: showStatistics();
						break;
					case 4: closeGasStation();
						break;
				}
				
			} catch (Exception e){
				System.out.println("invalid input, try again.");
			}
		}
	}

	private void closeGasStation() {
		// TODO Auto-generated method stub
		
	}

	private void showStatistics() {
		// TODO Auto-generated method stub
		
	}

	private void addFuelToMainRepository() {
		// TODO Auto-generated method stub
		
	}

	private void addCarToGasStation() {
		// TODO Auto-generated method stub
		
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
