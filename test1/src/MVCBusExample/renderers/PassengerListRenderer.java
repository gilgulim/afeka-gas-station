package MVCBusExample.renderers;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import utils.ImageUtils;

@SuppressWarnings("serial")
public class PassengerListRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		super.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);

		Passenger thePassenger = (Passenger)value;
		System.out.println("**** In getListCellRendererComponent: " + thePassenger.getName());
		setText(thePassenger.getName());
		setIcon(ImageUtils.getImageIcon(ImageUtils.PASSENGER_ICON));
		setBorder(BorderFactory.createEtchedBorder());
		setVerticalTextPosition(SwingConstants.TOP);
		setHorizontalTextPosition(JLabel.CENTER);

		return this;
	}

}
