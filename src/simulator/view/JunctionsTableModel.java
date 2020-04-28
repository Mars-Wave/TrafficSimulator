package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private Controller _ctrl;
	private int colCount = 3;
	private int rowCount = 0;

	public JunctionsTableModel(Controller controller) {
		_ctrl = controller;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		setValueAt("Id", rowCount, 0);
		setValueAt("Green", rowCount, 1);
		setValueAt("Queues", rowCount, 2);
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
		for (Junction j : map.getJunctions()) {
			setValueAt(j.getId(), rowCount, 0);

			if (j.getGreenLightIndex() == -1) {
				setValueAt("NONE", rowCount, 1);
				setValueAt("", rowCount, 2);
			} else {
				setValueAt(j.getInRoads().get(j.getGreenLightIndex()).getId(), rowCount, 1);
				setValueAt(j.getMapRq(), rowCount, 2);	// No way this does something similar to r1:[v2,v4] r2:[v1] ...
			}
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
