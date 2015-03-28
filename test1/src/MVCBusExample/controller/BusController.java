package MVCBusExample.controller;

import MVCBusExample.bl.Bus;
import MVCBusExample.listeners.BusEventsListener;
import MVCBusExample.listeners.BusUIEventsListener;
import MVCBusExample.views.AbstractBusView;

public class BusController implements BusEventsListener, BusUIEventsListener {
    private Bus busModel;
    private AbstractBusView  busView;
    
    public BusController(Bus model, AbstractBusView view) {
        busModel = model;
        busView  = view;
        
        busModel.registerListener(this);
        busView.registerListener(this);
    }

	@Override
	public void addedPassengerToModelEvent(int id, String name) {
		busView.addPassengerToUI(id, name);
	}

	@Override
	public void removedPassengerFromModelEvent(int id) {
		busView.removePassengerFromUI(id);
	}

	@Override
	public void addPassengerToUI(String name) {
		busModel.addPaseenger(name);
	}

	@Override
	public void removePassengerFromUI(int id) {
		busModel.removePassenger(id);
	}
}
