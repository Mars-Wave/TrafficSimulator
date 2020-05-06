package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {


    private List<Junction> _junctions;
    private String[] _colNames = {"Id", "Green", "Queues"};


    public JunctionsTableModel(Controller ctrl) {
        ctrl.addObserver(this);
        _junctions = new ArrayList<>();
    }

    public String getColumnName(int column) {
        return _colNames[column];
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
		_junctions = new ArrayList<>();
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

    public void setVehiclesList(List<Junction> junctions) {
        _junctions = junctions;
        update();
    }

    @Override
    public int getColumnCount() {
        return _colNames.length;
    }

    @Override
    public int getRowCount() {
        return _junctions == null ? 0 : _junctions.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object s = null;
        switch (columnIndex) {
            case 0:
                s = _junctions.get(rowIndex).getId();
                break;
            case 1:
                int green = _junctions.get(rowIndex).getGreenLightIndex();
				if (green == -1) {
					s = "NONE";
				}
				else{
					s = _junctions.get(rowIndex).getInRoads().get(green).getId();
				}
				break;
            case 2:
                String aux = "";
                for (Road r : _junctions.get(rowIndex).getInRoads()) {
                    aux += r.getId() + ":" + r.getVehicles() + " ";
                }
                s = aux;
                break;
        }
        return s;
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        _junctions = map.getJunctions();
        update();
    }
}
