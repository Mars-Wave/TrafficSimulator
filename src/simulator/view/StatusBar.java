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

	public StatusBar(Controller _ctrl) {
		// TODO Auto-generated constructor stub
		controller = _ctrl;
		initGUI();
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JLabel simTimeLabel = new JLabel();
		this.add(simTimeLabel);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(new JSeparator(SwingConstants.VERTICAL));
		JLabel eventLabel = new JLabel();
		this.add(eventLabel);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
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
