package MVCBusExample.renderers;

public class BusController implements BusEventsListener, BusUIEventsListener {
    private Bus busModel;
    private BusViewWithListRenderer  busView;
    
    public BusController(Bus model, BusViewWithListRenderer view) {
        busModel = model;
        busView  = view;
        
        busModel.registerListener(this);
        busView.registerListener(this);
    }

	@Override
	public void addedPassengerToModelEvent(Passenger thePassenger) {
		busView.addPassengerToUI(thePassenger);
	}

	@Override
	public void removedPassengerFromModelEvent(Passenger thePassenger) {
		busView.removePassengerFromUI(thePassenger);
	}

	@Override
	public void addPassengerToUI(String name) {
		busModel.addPassenger(name);
	}

	@Override
	public void removePassengerFromUI(int id) {
		busModel.removePassenger(id);
	}
}
