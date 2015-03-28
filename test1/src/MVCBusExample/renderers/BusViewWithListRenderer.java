package MVCBusExample.renderers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class BusViewWithListRenderer extends JFrame {
	private List<BusUIEventsListener> allListeners;

	private JPanel pnlMain;
	private JLabel lblName, lblNumOfPassengers;
	private JTextField txtName;
	private JButton cmdAdd, cmdRemove;
	private DefaultListModel<Passenger> model;
	private JList<Passenger> passengersList;
	private JScrollPane passengersScrollPane;

	public BusViewWithListRenderer() {
		allListeners = new LinkedList<BusUIEventsListener>();

		setTitle("Rendrer Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 380);

		pnlMain = new JPanel();

		lblName = new JLabel("Name: ");
		pnlMain.add(lblName);

		txtName = new JTextField("", 20);
		pnlMain.add(txtName);

		cmdAdd = new JButton("Add");
		cmdAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addPassenger();
			}
		});
		pnlMain.add(cmdAdd);

		model = new DefaultListModel<Passenger>();
		passengersList = new JList<Passenger>(model);
		passengersList.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						removePassenger();
					}
				});

		// if we want control over how's the list DATA is being painted - but
		// without actually painting it ourselves
		// we can set a Renderer that is basically a Swing component that is
		// being created for every data item
		passengersList.setCellRenderer(new PassengerListRenderer());
		passengersList.setVisibleRowCount(2);
		passengersList.setFixedCellHeight(80);

		passengersScrollPane = new JScrollPane(passengersList);
		passengersScrollPane.setAutoscrolls(true);
		pnlMain.add(passengersScrollPane);

		cmdRemove = new JButton("Remove");
		cmdRemove.setEnabled(false);
		cmdRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removePassenger();
			}
		});
		pnlMain.add(cmdRemove);

		lblNumOfPassengers = new JLabel("There are no passengers yet");
		pnlMain.add(lblNumOfPassengers);

		placeComponents();

		getContentPane().add(pnlMain);
		setVisible(true);
	}

	private void placeComponents() {
		SpringLayout theLayout = new SpringLayout();
		pnlMain.setLayout(theLayout);

		// the name gui components
		theLayout.putConstraint(SpringLayout.WEST, lblName, 10,
				SpringLayout.WEST, pnlMain);
		theLayout.putConstraint(SpringLayout.NORTH, lblName, 10,
				SpringLayout.NORTH, pnlMain);

		theLayout.putConstraint(SpringLayout.WEST, txtName, 70,
				SpringLayout.WEST, pnlMain);
		theLayout.putConstraint(SpringLayout.NORTH, txtName, 10,
				SpringLayout.NORTH, pnlMain);

		// the buttons gui components
		theLayout.putConstraint(SpringLayout.WEST, cmdAdd, 10,
				SpringLayout.WEST, pnlMain);
		theLayout.putConstraint(SpringLayout.NORTH, cmdAdd, 20,
				SpringLayout.SOUTH, lblName);

		// the table gui component
		theLayout.putConstraint(SpringLayout.WEST, passengersScrollPane, 10,
				SpringLayout.WEST, pnlMain);
		theLayout.putConstraint(SpringLayout.NORTH, passengersScrollPane, 25,
				SpringLayout.SOUTH, cmdAdd);

		// the num of passengers' label
		theLayout.putConstraint(SpringLayout.WEST, lblNumOfPassengers, 10,
				SpringLayout.WEST, pnlMain);
		theLayout.putConstraint(SpringLayout.NORTH, lblNumOfPassengers, 2,
				SpringLayout.SOUTH, passengersScrollPane);

		// the remove passenger button
		theLayout.putConstraint(SpringLayout.WEST, cmdRemove, 10,
				SpringLayout.WEST, pnlMain);
		theLayout.putConstraint(SpringLayout.NORTH, cmdRemove, 10,
				SpringLayout.SOUTH, lblNumOfPassengers);
	}

	private void removePassenger() {
		if (passengersList.getSelectedIndex() == -1)
			return;

		Passenger selectedPassenger = (Passenger)(passengersList.getSelectedValue());
		fireRemovePassengerEvent(selectedPassenger.getId());
	}

	private void addPassenger() {
		String name = txtName.getText();
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must fill a name first!");
			return;
		}

		fireAddPassengerEvent(name);
	}

	private void fireAddPassengerEvent(String name) {
		for (BusUIEventsListener l : allListeners)
			l.addPassengerToUI(name);
	}

	private void fireRemovePassengerEvent(int id) {
		for (BusUIEventsListener l : allListeners)
			l.removePassengerFromUI(id);
	}

	private void updateNumOfPassengersLabel() {
		lblNumOfPassengers.setText("The number of passengers is "
				+ Bus.getNumOfPassengers());
	}

	public void addPassengerToUI(Passenger thePassenger) {
		model.addElement(thePassenger);

		updateNumOfPassengersLabel();
		cmdRemove.setEnabled(true);
		txtName.setText("");

	}

	public void registerListener(BusUIEventsListener listener) {
		allListeners.add(listener);
	}

	public void removePassengerFromUI(Passenger thePassenger) {
		model.removeElement(thePassenger);

		updateNumOfPassengersLabel();

		if (model.getSize() == 0)
			cmdRemove.setEnabled(false);
	}
}
