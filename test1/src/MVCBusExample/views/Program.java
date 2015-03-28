package MVCBusExample.views;

import MVCBusExample.bl.Bus;
import MVCBusExample.controller.BusController;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bus model = new Bus();
		
		AbstractBusView viewTabel = new BusViewTable();
		AbstractBusView viewLabel = new BusViewLabels();
		
		BusController controller1 = new BusController(model, viewTabel);
		BusController controller2 = new BusController(model, viewLabel);
	}

}
