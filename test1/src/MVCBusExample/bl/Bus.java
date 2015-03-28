package MVCBusExample.bl;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import MVCBusExample.listeners.BusEventsListener;

public class Bus {
	private static Logger theLogger = Logger.getLogger("myLogger");
	
	static private Vector<Passenger> allPassengers;
	private Vector<BusEventsListener> listeners;

	public Bus() {
		allPassengers = new Vector<Passenger>();
		listeners = new Vector<BusEventsListener>();
	}
	
	public int getNumOfPassengers() {
		return allPassengers.size();
	}
	
	public void registerListener(BusEventsListener listener) {
		listeners.add(listener);
	}
	
	public Passenger addPaseenger(String name) {
		Passenger newPassenger = new Passenger(name);
		allPassengers.add(newPassenger);
		fireAddPassengerEvent(newPassenger);
		theLogger.log(Level.INFO, "Adding the passenger " + name + " to the bus");
	
		return newPassenger;
	}
	
	public Passenger removePassenger(int id) {
		Passenger passengerToRemove = getPassengerById(id);
		allPassengers.remove(passengerToRemove);
		fireRemovePasengerEvent(passengerToRemove);
		
		theLogger.log(Level.INFO, "Remove the passenger " + passengerToRemove.getName() + " from the bus");
		return passengerToRemove;
	}
	
	public Passenger getPassengerById(int id) {
		for (Passenger p : allPassengers) {
			if (p.getId() == id)
				return p;
		}
		return null;
	}
	
	private void fireAddPassengerEvent(Passenger passenger) {
		for (BusEventsListener l : listeners) {
			l.addedPassengerToModelEvent(passenger.getId(), passenger.getName());
		}
	}
	
	private void fireRemovePasengerEvent(Passenger passenger) {
		for (BusEventsListener l : listeners) {
			l.removedPassengerFromModelEvent(passenger.getId());
		}
	}
}
