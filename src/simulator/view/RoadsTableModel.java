package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private int colCount = 7;
	private int rowCount = 0;
	
	public RoadsTableModel(Controller ctrl) {
		ctrl.addObserver(this);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		setValueAt("Id", rowCount, 0);
		setValueAt("Length", rowCount, 1);
		setValueAt("Weather", rowCount, 2);
		setValueAt("Max.Speed", rowCount, 3);
		setValueAt("Speed Limit", rowCount, 4);
		setValueAt("Total CO2", rowCount, 5);
		setValueAt("CO2 Limit", rowCount, 6);
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
		for(Road r : map.getRoads()) {
			setValueAt(r.getId(), rowCount, 0);
			setValueAt(r.getLength(), rowCount, 1);
			setValueAt(r.getWeather().toString(), rowCount, 2);
			setValueAt(r.getMaxSpeed(), rowCount, 3);
			setValueAt(r.getSpeedLimit(), rowCount, 4);
			setValueAt(r.getTotalCont(), rowCount, 5);
			setValueAt(r.getContLimit(), rowCount, 6);
			rowCount++;
		}
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
