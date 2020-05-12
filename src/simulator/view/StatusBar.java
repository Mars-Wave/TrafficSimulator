package simulator.view;

import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{
	
	private Controller controller;
	private JLabel simTimeLabel;
	private JLabel eventLabel;

	public StatusBar(Controller _ctrl) {
		// TODO Auto-generated constructor stub
		controller = _ctrl;
		_ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		this.setVisible(true);
		this.setPreferredSize(new Dimension(25,25));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		simTimeLabel = new JLabel("Time: 0");
		this.add(simTimeLabel);
		this.add(Box.createRigidArea(new Dimension(150, 0)));
		JSeparator separator = new JSeparator(){
		    @Override
		    public Dimension getMaximumSize(){
		        return new Dimension(5, 25);
		    }
		};
		separator.setOrientation(JSeparator.VERTICAL);
		this.add(separator);
		eventLabel = new JLabel("Welcome!");
		this.add(eventLabel);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		simTimeLabel.setText("Time: " + time);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		eventLabel.setText("Event added (" + e.toString() + ")");
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
