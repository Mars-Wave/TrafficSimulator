package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

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
        setJunctionsList(map.getJunctions());
        fireTableDataChanged();
    }

    private void setJunctionsList(List<Junction> junctions) {
        _junctions = junctions;
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
               for (Map.Entry<Road, List<Vehicle>> rq : _junctions.get(rowIndex).getMapRq().entrySet()) {
                	String vListString = rq.getValue().toString();
                    aux += rq.getKey().getId() + ":" + vListString;
                }
                
                s = aux;
                break;
        }
        return s;
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
    }
}
