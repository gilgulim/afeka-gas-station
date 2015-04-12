package Views;

import java.util.Scanner;

import bl.*;


public class ConsoleMenu implements NotificationsHandler {
	
	private GasStation gasStation;
	private Scanner s;
	private boolean isRunning;
	
	public ConsoleMenu(GasStation gasStation){
		this.gasStation = gasStation;
		this.gasStation.setNotificationHandler(this);
		s = new Scanner(System.in);
		isRunning = true;
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
		gasStation.stopGasStation();
		System.out.println("gas station is now closed");
		isRunning = false;
		System.out.println("gas station is closed");
	}

	private void showStatistics() {
		System.out.println("total cars treated:  " + gasStation.getTotalCars());
		System.out.println("total cars cleaned: " + gasStation.getTotalCarsWashed());
		System.out.println("total cars fueled:  " + gasStation.getTotalCarsFueled());
		
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

	@Override
	public void notificationHandle(NotifyType errType, Object data) {
		switch (errType){
		case WARNING_LOW_FUEL : System.out.println("Repository low fuel");
			break;
		case WARNING_FUEL_REP_EMPTY : System.out.println("Repository empty");
			break;
		case INFO_FUEL_REP_DONE_FUELING: System.out.println("Repository done fueling");
			break;
		case INFO_FUEL_REP_STATUS: System.out.printf("%d ",data);
			break;
		default:
			break;
		}
	}
}
