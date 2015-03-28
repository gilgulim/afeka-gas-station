package MVCBusExample.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import MVCBusExample.bl.Passenger;
import MVCBusExample.listeners.BusUIEventsListener;
import MVCBusExample.utils.ImageUtils;

public class BusViewLabels extends JFrame implements AbstractBusView {
	private List<BusUIEventsListener> allListeners;

	private JPanel pnlMain;
	private JLabel lblName, lblNumOfPassengers;
	private JTextField txtName;
	private JButton cmdAdd, cmdRemove;
	private JPanel pnlPassengers;
	private JScrollPane passengersScrollPane;

	public BusViewLabels() {
		allListeners = new LinkedList<BusUIEventsListener>();

		setTitle("MVC Simple Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 305);

		pnlMain = new JPanel();

		lblName = new JLabel("Name: ");
		pnlMain.add(lblName);

		txtName = new JTextField("", 20);
		pnlMain.add(txtName);

		cmdAdd = new JButton("Add");
		cmdAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireAddPassengerPressed();
			}
		});
		pnlMain.add(cmdAdd);

		pnlPassengers = new JPanel(new FlowLayout());
		passengersScrollPane = new JScrollPane(pnlPassengers);
		passengersScrollPane.setSize(300, 200);
		passengersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		passengersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pnlMain.add(passengersScrollPane);
		
		lblNumOfPassengers = new JLabel("There are no passengers yet");
		pnlMain.add(lblNumOfPassengers);
		
		cmdRemove = new JButton("Remove");
		cmdRemove.setEnabled(false);
		cmdRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireRemovePassengerPressed();
			}
		});
		pnlMain.add(cmdRemove);

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

	@Override
	public void registerListener(BusUIEventsListener listener) {
		allListeners.add(listener);
	}

	@Override
	public void addPassengerToUI(int id, String name) {
		JLabel lblPassenger = new JLabel();
		
		lblPassenger.setText(name + " (" + id + ")");
		lblPassenger.setVerticalTextPosition(SwingConstants.TOP);
		lblPassenger.setHorizontalTextPosition(JLabel.CENTER);
		
		lblPassenger.setIcon(ImageUtils.getImageIcon(utils.ImageUtils.PASSENGER_ICON));
		
		pnlPassengers.add(lblPassenger);
		
		repaint();
		validate();
		
		cmdRemove.setEnabled(true);
		txtName.setText("");
		updateNumOfPassengersLabel();
	}
	
	@Override
	public void removePassengerFromUI(int id) {
		/*int numOfPassengers = pnlPassengers.getComponentCount();
		for (int i=0 ; i <numOfPassengers ; i++) {
			JLabel temp = (JLabel)pnlPassengers.getComponent(i);
			int currentId = Integer.parseInt(temp.getText().split("()")[1]);
			if (currentId == id) {
				pnlPassengers.remove(temp);
				if (pnlPassengers.getComponentCount() == 0) 
					cmdRemove.setEnabled(false);
				
				return;
			}
		}*/
		updateNumOfPassengersLabel();
	//	JOptionPane.showMessageDialog(null, "This option is not implemented in this version");
	}

	private void fireAddPassengerPressed() {
		String name = txtName.getText();
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must fill a name first!");
			return;
		}
		
		for (BusUIEventsListener l : allListeners) {
			l.addPassengerToUI(name);
		}
	}
	
	private void fireRemovePassengerPressed() {
		/*int selected = pnlPassengers.get
		int line = tblPassengers.getSelectedRow();
		if (line == -1) {
			JOptionPane.showMessageDialog(null, "You have to select a passenger first!");
		}
		
		int passengerId = (Integer)(tableModel.getValueAt(line, 0));
		for (BusUIEventsListener l : allListeners) {
			l.removePassengerFromUI(passengerId);
		}*/
		JOptionPane.showMessageDialog(null, "This option is not implemented in this version");
	}
	
	private void updateNumOfPassengersLabel() {
		lblNumOfPassengers.setText("The number of passengers is " + pnlPassengers.getComponentCount());
	}
}
