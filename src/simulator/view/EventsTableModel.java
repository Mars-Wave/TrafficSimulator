package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import extra.jtable.EventEx;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private List<Event> _events;
	private String[] _colNames = {  "Time", "Description" };


	public EventsTableModel(Controller ctrl) {
		ctrl.addObserver(this);
		_events = new ArrayList<>();
	}

	public String getColumnName(int column) {
		return _colNames[column];
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
		setEventsList(events);
		update();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_events.clear();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}
	public void update() {
		fireTableDataChanged();
	}

	public void setEventsList(List<Event> events) {
		
		_events = events;
		update();
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _events == null ? 0 : _events.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
			case 0:
				s = _events.get(rowIndex).getTime();
				break;
			case 1:
				s = _events.get(rowIndex).toString();
				break;
		}
		return s;
	}
}
