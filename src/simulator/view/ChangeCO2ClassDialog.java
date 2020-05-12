package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.SetContClassEvent;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog{
	
	private Controller controller;
	private JComboBox<String> vehList;
	private int _simTime;
	
	public ChangeCO2ClassDialog(Controller controller) {
		this.controller = controller;
		this.vehList = new JComboBox<>();
		initGUI();
		this.pack();
	}
	
	public void initGUI() {
		JPanel contClassPanel = new JPanel(new BorderLayout());
		JLabel contClassLabel = new JLabel("<html><p>Schedule an event to change the weather of a road after a given number of simulation ticks from now.</p></html>");
		// Page start
		contClassPanel.add(contClassLabel, BorderLayout.PAGE_START);
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel vehicleLabel = new JLabel("Vehicle:");
		centerPanel.add(vehicleLabel);
		centerPanel.add(vehList);
		vehList.setEditable(true);
		JLabel co2Label = new JLabel("CO2 Class:");
		centerPanel.add(co2Label);
		Integer numbers[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		JComboBox<Integer> co2list = new JComboBox<>(numbers);
		centerPanel.add(co2list);
		JLabel ticksLabel = new JLabel("Ticks:");
		centerPanel.add(ticksLabel);
		JSpinner spin = new JSpinner();
		spin.setPreferredSize(new Dimension(50,25));
		centerPanel.add(spin);
		contClassPanel.add(centerPanel);
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> {this.setVisible(false);});
		bottomPanel.add(cancelButton);
		JButton okButton = new JButton("OK");
		List<Pair<String, Integer>> cs = new ArrayList<>();
		
		okButton.addActionListener((e) -> {
			Pair<String, Integer> p = new Pair(vehList.getSelectedItem(), co2list.getSelectedItem());
			cs.add(p);
			controller.addEvent(new SetContClassEvent(((Integer)spin.getValue() + _simTime), cs));
			this.setVisible(false);
			}
		);	// null treatment?
		bottomPanel.add(okButton);
		bottomPanel.add(cancelButton);
		this.add(bottomPanel, BorderLayout.PAGE_END);
		this.setTitle("Change CO2 Class");
		this.setSize(new Dimension(350,140));
		this.setVisible(false);
		this.add(contClassPanel);
		this.setResizable(false);
	}

	public void setVehList(List<Vehicle> vList) {
		vehList.isEditable();
		vehList.removeAllItems();
		for (Vehicle v : vList){
			vehList.addItem(v.getId());
		}
		
	}

	public void updateTime(int time) {
		_simTime = time;
		
	}
}
