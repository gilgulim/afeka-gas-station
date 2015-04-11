package Views;

import java.util.Scanner;

import bl.Car;
import bl.GasStation;

public class ConsoleMenu {
	private GasStation gasStation;
	private Scanner s;
	private boolean isRunning;
	
	public ConsoleMenu(GasStation gasStation){
		this.gasStation = gasStation;
		s = new Scanner(System.in);
		isRunning = true;
		
		this.startMenu();
	}

	public void startMenu(){
		while(isRunning){
			int menuSelection;
			System.out.println("Choose one of the following options:");
			System.out.println("1. add car to Gas station.");
			System.out.println("2. add fuel to main repository (" + gasStation.getFuelRep().getCurrentCapacity() + "/" + gasStation.getFuelRep().getMaxCapacity() + ").");
			System.out.println("3. show statistics.");
			System.out.println("4. close the Gas station.");
			
			try{
				menuSelection = s.nextInt();
				if(menuSelection > 4 || menuSelection < 1){
					System.out.println("invalid input, numbers should be between 1 and 4.\n");
				}else
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
				System.out.println("invalid input, try again.\n");
				s.nextLine();
			}
		}
	}

	private void closeGasStation() {

	}

	private void showStatistics() {
		// TODO Auto-generated method stub
		
	}

	private void addFuelToMainRepository() {
		int maxFuelAllowed =gasStation.getFuelRep().getMaxCapacity() - gasStation.getFuelRep().getCurrentCapacity();
		System.out.println("maximum allowed fueling qty is:  " + maxFuelAllowed + ", enter emount");
		try{
			int fuelQty = s.nextInt();
			s.nextLine();
			
			if (fuelQty > maxFuelAllowed){
				System.out.println("max allowed exception");
			}else{
				gasStation.getFuelRep().fillRepository(fuelQty);
			}
		}catch (Exception e){
			System.out.println("invalid input");
		}
		
		
	}

	private void addCarToGasStation() {
		int carId, fuelQty=0;
		String strWash, strFuel;
		
		try{
			System.out.println("enter car ID, for example: 123.");
			carId = s.nextInt();
			s.nextLine();
			
			System.out.println("required wash? (y/n)");
			strWash = s.nextLine();
			
			System.out.println("required fuel? (y/n)");
			strFuel = s.nextLine();
			
			if(strFuel.equals("y")){
				System.out.println("enter fuel quantity required");
				fuelQty = s.nextInt();
				s.nextLine();
			}	
			
			boolean requireWash = strWash.equals("y") ? true : false;
			boolean requireFuel = strFuel.equals("y") ? true : false;
			
			Car car = new Car(carId, requireWash, requireFuel, gasStation);
			car.setFuelAmountRequired(fuelQty);

			gasStation.addCarDispatcherQueue(car);
			System.out.printf("The car %d added successfully to the gas station", carId);
			System.out.println();
		}catch(Exception e){
			System.out.println("wrong input\n");
			System.out.println("\n");
			
		}
		
		
		
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
