package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

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
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(events);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(events);
	}

	@Override
	public void onError(String err) {
	}

	private void update(List<Event> events) {
		setEventsList(events);
		fireTableDataChanged();
	}

	private void setEventsList(List<Event> events) {
		_events = events;
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
