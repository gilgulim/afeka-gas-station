package MVCBusExample.listeners;

public interface BusEventsListener {
	void addedPassengerToModelEvent(int id, String name);
	void removedPassengerFromModelEvent(int id);
}
