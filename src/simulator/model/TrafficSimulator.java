package simulator.model;


import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.List;

public class TrafficSimulator {

    RoadMap map;
    List<Event> events;
    int simTime;

    public TrafficSimulator() {
        map = new RoadMap();
        events = new SortedArrayList<>();
        simTime = 0;
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void advance() {
        simTime++;
        while (!events.isEmpty() && events.get(0).getTime() == simTime) {
            events.get(0).execute(map);
            events.remove(0);
        }
        for (Junction j : map.getJunctions()) {
            j.advance(simTime);
        }
        for (Road r : map.getRoads()) {
            r.advance(simTime);
        }
    }

    public void reset() {
        map.reset();
        simTime = 0;
    }

    public JSONObject report() {
        JSONObject rep = new JSONObject();
        rep.put("time", simTime);
        rep.put("state", map.report());
        return rep;
    }
}
