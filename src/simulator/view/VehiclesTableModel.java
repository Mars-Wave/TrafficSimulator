package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.*;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{



	private List<Vehicle> _vehicles;
	private String[] _colNames = {"Id", "Location", "Itinerary", "CO2 Class", "Max. Speed", "Speed", "Total CO2", "Distance"};



	public VehiclesTableModel(Controller ctrl) {
		ctrl.addObserver(this);
		_vehicles = new ArrayList<>();
	}

	public String getColumnName(int column) {
		return _colNames[column];
	}


	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_vehicles = map.getVehicles();
		update();

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_vehicles = new ArrayList<>();
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

	public void setVehiclesList(List<Vehicle> vehicles) {
		_vehicles = vehicles;
		update();
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _vehicles == null ? 0 : _vehicles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
			case 0:
				s = _vehicles.get(rowIndex).getId();
				break;
			case 1:
				s = _vehicles.get(rowIndex).getLocation();
				break;
			case 2:
				s = _vehicles.get(rowIndex).getItinerary();
				break;
			case 3:
				s = _vehicles.get(rowIndex).getContClass();
				break;
			case 4:
				s = _vehicles.get(rowIndex).getMaxSpeed();
				break;
			case 5:
				s = _vehicles.get(rowIndex).getCurrentSpeed();
				break;
			case 6:
				s = _vehicles.get(rowIndex).getTotalCont();
				break;
			case 7:
				s = _vehicles.get(rowIndex).getTotalDist();
				break;

		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

}
