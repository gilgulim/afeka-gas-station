package MVCBusExample.renderers;

public interface BusUIEventsListener {
	void addPassengerToUI(String name);
	void removePassengerFromUI(int id);
}
