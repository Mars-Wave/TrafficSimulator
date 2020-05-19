package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.*;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{


	private List<Road> _roads;
	private String[] _colNames = {"Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};


	public RoadsTableModel(Controller ctrl) {
		_roads = new ArrayList<>();
		ctrl.addObserver(this);
	}

	public String getColumnName(int column) {
		return _colNames[column];
	}


	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
	}

	private void update(RoadMap map) {
		setRoadsList(map.getRoads());
		fireTableDataChanged();
	}

	private void setRoadsList(List<Road> Roads) {
		_roads = Roads;
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _roads == null ? 0 : _roads.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
			case 0:
				s = _roads.get(rowIndex).getId();
				break;
			case 1:
				s = _roads.get(rowIndex).getLength();
				break;
			case 2:
				s = _roads.get(rowIndex).getWeather();
				break;
			case 3:
				s = _roads.get(rowIndex).getMaxSpeed();
				break;
			case 4:
				s = _roads.get(rowIndex).getSpeedLimit();
				break;
			case 5:
				s = _roads.get(rowIndex).getTotalCont();
				break;
			case 6:
				s = _roads.get(rowIndex).getContLimit();
				break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}
}
