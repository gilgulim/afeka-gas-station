package MVCBusExample.views;

import MVCBusExample.listeners.BusUIEventsListener;

public interface AbstractBusView {
	void registerListener(BusUIEventsListener listener);
	void addPassengerToUI(int id, String name);
	void removePassengerFromUI(int id);
}
