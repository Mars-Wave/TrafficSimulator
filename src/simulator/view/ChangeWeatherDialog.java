package simulator.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog{
	
	private Controller controller;
	private JComboBox<String> rList;
	private int _simTime;

	
	public ChangeWeatherDialog(Controller controller) {
		this.controller = controller;
		rList = new JComboBox<>();
		initGUI();
		this.pack();
	}
	
	public void initGUI() {
		//
		JPanel contClassPanel = new JPanel(new BorderLayout());
		JLabel contClassLabel = new JLabel("<html><p>Schedule an event to change the weather of a road after a given number of simulation ticks from now.</p></html>");
		// Page start
		contClassPanel.add(contClassLabel, BorderLayout.PAGE_START);
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel roadLabel = new JLabel("Road:");
		centerPanel.add(roadLabel);
		centerPanel.add(rList);
		rList.setEditable(true);
		//for to load names
		JLabel weatherLabel = new JLabel("Weather:");
		centerPanel.add(weatherLabel);
		Weather weathers[] = Weather.weathers();
		JComboBox<Weather> weatherlist = new JComboBox(weathers);
		centerPanel.add(weatherlist);
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
		okButton.addActionListener((e) -> {
		    List<Pair<String, Weather>> ws = new ArrayList<>();
			Pair<String, Weather> p = new Pair(rList.getSelectedItem(), weatherlist.getSelectedItem());
			ws.add(p);
			controller.addEvent(new SetWeatherEvent(((Integer)spin.getValue() + _simTime), ws));
			this.setVisible(false);
			}
		);	// null treatment?
		bottomPanel.add(okButton);
		bottomPanel.add(cancelButton);
		this.add(bottomPanel, BorderLayout.PAGE_END);
		this.setTitle("Change Road Weather");
		this.setSize(new Dimension(350,140));
		this.setVisible(false);
		this.add(contClassPanel);
		this.setResizable(false);
	}

	public void setrList(List<Road> rList) {
		this.rList.removeAllItems();
		for (Road r : rList){
			this.rList.addItem(r.getId());
		}
	}
	
	public void updateTime(int time) {
		_simTime = time;
		
	}
}
