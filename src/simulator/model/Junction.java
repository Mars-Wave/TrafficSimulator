package simulator.model;

import exception.negativeValue;
import exception.roadMapCoherenceException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Junction extends SimulatedObject {
    private List<Road> roadL;
    private Map<Junction, Road> jrmap;
    private List<List<Vehicle>> qs;
    private Map<Road, List<Vehicle>> rqmap;
    private int greenLightIndex;
    private int xCoor;
    private int yCoor;
    private int LastSwitchingTime;
    private LightSwitchingStrategy LSS;
    private DequeuingStrategy DS;

    public Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(id);
        if (lsStrategy == null | dqStrategy == null)
            throw new NullPointerException();
        if (xCoor < 0 | yCoor < 0)
            throw new negativeValue("Coordinates should not be negative");
        this.DS = dqStrategy;
        this.LSS = lsStrategy;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        roadL = new ArrayList<>();
        jrmap = new HashMap<>();
        qs = new ArrayList<>();
        rqmap = new HashMap<>();
        LastSwitchingTime = 1; //Infrastructure gets added in time 1 ALWAYS
    }

    public int getX() {
        return xCoor;
    }

    public int getY() {
        return yCoor;
    }

    public void addIncomingRoad(Road r) {
        if (r.destJunction.equals(this)) {
            roadL.add(r);
            List<Vehicle> q = new LinkedList<>();
            qs.add(q);
            rqmap.put(r, q);
        } else {
            throw new roadMapCoherenceException("You are trying to add an incoming road which is not in reality an incoming road of this junction. R = " + r.getId() + " , J = " + this.getId());
        }
    }

    public void addOutgoingRoad(Road r) {
        if (jrmap.get(r.getDest()) == null && r.getSrc().equals(this)) {
            jrmap.put(r.getDest(), r);
        } else {
            throw new roadMapCoherenceException("You are trying to add an outgoing road which is not in reality an outgoing road of this junction. R = " + r.getId() + " , J = " + this.getId());
        }
    }

    public void enter(Vehicle v) {
            rqmap.get(v.getRoad()).add(v);
    }

    public Road roadTo(Junction j) {
        return jrmap.get(j);
    }

    @Override
    public void advance(int time) {
        if (!qs.isEmpty() && !qs.get(greenLightIndex).isEmpty()) {
            List<Vehicle> q;
            q = DS.dequeue(qs.get(greenLightIndex));
            for (int i = 0; i < q.size(); i++) {
                q.get(i).moveToNextRoad();
                qs.get(greenLightIndex).remove(i);
            }
        }
        if (greenLightIndex != LSS.chooseNextGreen(roadL, qs, greenLightIndex, LastSwitchingTime, time)) {
            greenLightIndex = LSS.chooseNextGreen(roadL, qs, greenLightIndex, LastSwitchingTime, time);
            LastSwitchingTime = time;
        }
    }

    @Override
    public JSONObject report() {
        JSONObject rep = new JSONObject();
        rep.put("id", getId());
        rep.put("green", greenLightIndex == -1 ? "none" : roadL.get(greenLightIndex).getId());
        JSONArray queues = new JSONArray();
        rep.put("queues", queues);
        for (Road r : roadL) {
            JSONObject q = new JSONObject();
            queues.put(q);
            q.put("road", r.getId());
            JSONArray vehicles = new JSONArray();
            q.put("vehicles", vehicles);
            for (Vehicle v : rqmap.get(r)) {
                vehicles.put(v.getId());
            }
        }
        return rep;
    }
}
