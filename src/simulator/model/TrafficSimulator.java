package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import exception.MyException;
import simulator.misc.SortedArrayList;

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
        if (e.getTime() > simTime) {
            events.add(e);
            for (TrafficSimObserver observer : obs) {
                observer.onEventAdded(map, events, e, simTime);
            }
        }
        else {
        	String errMsg = "You can't add an event at a time (" + e.getTime() + ") lower or equal to the current sim time (" + simTime +").";
        	for (TrafficSimObserver observer : obs) {
                observer.onError(errMsg);
            }
        	throw new MyException(errMsg);
        }
    }

    public void advance() throws Exception {
        simTime++;
        for (TrafficSimObserver observer : obs) {
            observer.onAdvanceStart(map, events, simTime);
        }
        try {
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
        } catch (Exception e) {
            for (TrafficSimObserver observer : obs) {
                observer.onError(e.getMessage());
            }
            throw e;
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
