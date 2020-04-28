package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{

	private Controller _ctrl;
	private int colCount = 8;
	private int rowCount = 0;
	
	public VehiclesTableModel(Controller controller) {
		_ctrl = controller;
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		setValueAt("Id", rowCount, 0);
		setValueAt("Location", rowCount, 1);
		setValueAt("Itinerary", rowCount, 2);
		setValueAt("CO2 Class", rowCount, 3);
		setValueAt("Max.Speed", rowCount, 4);
		setValueAt("Speed", rowCount, 5);
		setValueAt("Total CO2", rowCount, 6);
		setValueAt("Distance", rowCount, 7);
		rowCount++;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colCount;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		for(Vehicle v : map.getVehicles()) {
			setValueAt(v.getId(), rowCount, 0);
			switch(v.getStatus()) {
			case PENDING:
				setValueAt("Pending", rowCount, 1);
					break;
			case TRAVELING:
				setValueAt(v.getRoad().getId() + ":" + v.getLocation(), rowCount, 1);
					break;
			case WAITING:
				setValueAt("WAITING:" + v.getItinerary().get(v.getCurrentJunc()).getId(), rowCount, 1);	// eZ
					break;
			case ARRIVED:
				setValueAt("Arrived", rowCount, 1);
					break;
			}
			setValueAt(v.getItinerary(), rowCount, 2);	// Probably not going to output [junction, junction, ...] might need to fix?
			setValueAt(v.getContClass(), rowCount, 3);
			setValueAt(v.getMaxSpeed(), rowCount, 4);
			setValueAt(v.getCurrentSpeed(), rowCount, 5);
			setValueAt(v.getTotalCont(), rowCount, 6);
			setValueAt(v.getTotalDist(), rowCount, 7);
			rowCount++;
		}
		
		
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub	// events.indexOf(e)
		
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
