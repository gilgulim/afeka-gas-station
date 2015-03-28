package MVCBusExample.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import MVCBusExample.listeners.BusUIEventsListener;

public class BusViewTable extends JFrame implements AbstractBusView {
	private List<BusUIEventsListener> allListeners;

	private JPanel pnlMain;
	private JLabel lblName, lblNumOfPassengers;
	private JTextField txtName;
	private JButton cmdAdd, cmdRemove;
	private DefaultTableModel tableModel;
	private JTable tblPassengers;
	private JScrollPane passengersScrollPane;

	public BusViewTable() {
		allListeners = new LinkedList<BusUIEventsListener>();

		setTitle("MVC Simple Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 315);

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


		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(new String[] { "Id", "Name" });
		tblPassengers = new JTable(tableModel);

		tblPassengers.setPreferredScrollableViewportSize(new Dimension(270, 100));
		
		passengersScrollPane = new JScrollPane(tblPassengers);
		passengersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pnlMain.add(passengersScrollPane);
		
		cmdRemove = new JButton("Remove");
		cmdRemove.setEnabled(false);
		cmdRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireRemovePassengerPressed();
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

	@Override
	public void registerListener(BusUIEventsListener listener) {
		allListeners.add(listener);
	}

	@Override
	public void addPassengerToUI(int id, String name) {
		tableModel.addRow(new Object[] {id, name});
		cmdRemove.setEnabled(true);
		txtName.setText("");
		updateNumOfPassengersLabel();
	}
	
	@Override
	public void removePassengerFromUI(int id) {
		int numOfRows = tableModel.getRowCount();
		for (int i=0 ; i <numOfRows ; i++) {
			int idInRow = (Integer)tableModel.getValueAt(i, 0);
			if (idInRow == id) {
				tableModel.removeRow(i);
				if (tableModel.getRowCount() == 0) 
					cmdRemove.setEnabled(false);
				updateNumOfPassengersLabel();
				return;
			}
		}
	}
	
	private void updateNumOfPassengersLabel() {
		lblNumOfPassengers.setText("The number of passengers is " +tableModel.getRowCount());
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
		int line = tblPassengers.getSelectedRow();
		if (line == -1) {
			JOptionPane.showMessageDialog(null, "You have to select a passenger first!");
		}
		
		int passengerId = (Integer)(tableModel.getValueAt(line, 0));
		for (BusUIEventsListener l : allListeners) {
			l.removePassengerFromUI(passengerId);
		}
	}
}
