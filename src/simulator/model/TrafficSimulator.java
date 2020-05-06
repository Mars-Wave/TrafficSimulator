package simulator.model;

import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.ArrayList;
import java.util.List;

public class TrafficSimulator implements Observable<TrafficSimObserver> {

    RoadMap map;
    List<Event> events;
    int simTime;
    private List<TrafficSimObserver> obs;

    public TrafficSimulator() {
        map = new RoadMap();
        events = new SortedArrayList<>();
        obs = new ArrayList<>();
        simTime = 0;
    }

    public void addEvent(Event e) {
        if (e.getTime() >= simTime) {
            events.add(e);
            for (TrafficSimObserver observer : obs) {
                observer.onEventAdded(map, events, e, simTime);
            }
        }
    }

    public void advance() {
        simTime++;
        for (TrafficSimObserver observer : obs) {
            observer.onAdvanceStart(map, events, simTime);
        }
        while (!events.isEmpty() && events.get(0).getTime() == simTime) {
            events.get(0).execute(map);
            events.remove(0);
        }
        try {
            for (Junction j : map.getJunctions()) {
                j.advance(simTime);
            }
            for (Road r : map.getRoads()) {
                r.advance(simTime);
            }
        } catch (Exception e) {
            for (TrafficSimObserver observer : obs) {
                observer.onError(e.getMessage());
            }
        }

        for (TrafficSimObserver observer : obs) {
            observer.onAdvanceEnd(map, events, simTime);
        }


    }

    public void reset() {
        map.reset();
        simTime = 0;
        for (TrafficSimObserver observer : obs) {
            observer.onReset(map, events, simTime);
        }

    }

    public JSONObject report() {
        JSONObject rep = new JSONObject();
        rep.put("time", simTime);
        rep.put("state", map.report());
        return rep;
    }

    @Override
    public void addObserver(TrafficSimObserver o) {
        obs.add(o);
        o.onRegister(map, events, simTime);
    }

    @Override
    public void removeObserver(TrafficSimObserver o) {
        obs.remove(o);
    }
}
