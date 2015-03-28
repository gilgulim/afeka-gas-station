package MVCBusExample.renderers;

public class Program {

	public static void main(String[] args) {
		Bus model = new Bus();
		BusViewWithListRenderer view = new BusViewWithListRenderer();
		BusController theController = new BusController(model, view);
	}
}
