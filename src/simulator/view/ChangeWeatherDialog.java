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
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog{
	
	private Controller controller;
	private String names[] = {};
	
	public ChangeWeatherDialog(Controller controller) {
		this.controller = controller;
		initGUI();
	}
	
	public void initGUI() {
		//
		JPanel contClassPanel = new JPanel(new BorderLayout());
		JLabel contClassLabel = new JLabel("<html><p>Schedule an event to change the weather of a road after a given number of simulation ticks from now.</p></html>");
		contClassPanel.setPreferredSize(new Dimension(200,100));
		// Page start
		contClassPanel.add(contClassLabel, BorderLayout.PAGE_START);
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel roadLabel = new JLabel("Road:");
		centerPanel.add(roadLabel);
		JComboBox<String> rList = new JComboBox(names);
		centerPanel.add(rList);
		//for to load names
		JLabel weatherLabel = new JLabel("Weather:");
		centerPanel.add(weatherLabel);
		String weathers[] = Weather.names();
		JComboBox<String> weatherlist = new JComboBox(weathers);
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
		List<Pair<String, Weather>> ws = new ArrayList();
		Pair<String, Weather> p = new Pair(rList.getSelectedItem(), weatherlist.getSelectedItem());
		ws.add(p);
		okButton.addActionListener((e) -> {controller.addEvent(new SetWeatherEvent((Integer)spin.getValue(), ws));});	// null treatment?
		bottomPanel.add(cancelButton);
		
		this.setTitle("Change Road Weather");
		this.setSize(new Dimension(350,110));
		this.setVisible(false);
		this.add(contClassPanel);
		this.setResizable(false);
	}
	
	public void loadNames(String names[]) {
			this.names = names;
	}
	
}
