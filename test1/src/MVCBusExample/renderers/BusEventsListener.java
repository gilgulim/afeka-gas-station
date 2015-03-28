package MVCBusExample.renderers;

public interface BusEventsListener {
	void addedPassengerToModelEvent(Passenger thePassenger);
	void removedPassengerFromModelEvent(Passenger thePassenger);
}
