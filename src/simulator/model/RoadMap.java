package simulator.model;

import exception.duplicateKeyException;
import exception.unexistingReferencedObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {

    private List<Junction> js;
    private List<Road> rs;
    private List<Vehicle> vs;
    private Map<String, Junction> sjMap;
    private Map<String, Road> srMap;
    private Map<String, Vehicle> svMap;

    public RoadMap() {
        js = new ArrayList<>();
        rs = new ArrayList<>();
        vs = new ArrayList<>();
        sjMap = new HashMap<>();
        srMap = new HashMap<>();
        svMap = new HashMap<>();
    }

    void addJunction(Junction j) {
        if (sjMap.get(j.getId()) == null) {
            js.add(j);
            sjMap.put(j.getId(), j);
        } else throw new duplicateKeyException("Duplicate Junction not allowed, with " + j);
    }

    void addRoad(Road r) {
        boolean found = false;
        for (int i = 0; i < js.size() && !found; i++) {
            found = js.get(i).equals(r.getDest());
        }
        if (!found) {
            throw new unexistingReferencedObject("No junction by the name implied exists to be a destination for the road.");
        }
        if (srMap.get(r.getId()) == null) {
            srMap.put(r.getId(), r);
            rs.add(r);
        } else throw new duplicateKeyException("Duplicate Road not allowed, with " + r);
    }

    void addVehicle(Vehicle v) {
        for (int i = 0; i < v.getItinerary().size() - 2; i++) {
            if (v.getItinerary().get(i).roadTo(v.getItinerary().get(i + 1)).equals(null)) {
                throw new unexistingReferencedObject("The itinerary is invalid for vehicle " + v);
            }
        }
        if (svMap.get(v.getId()) == null) {
            vs.add(v);
            svMap.put(v.getId(), v);
        } else throw new duplicateKeyException("Duplicate Vehicle not allowed, with " + v);
    }

    public Junction getJunction(String id) {
        return sjMap.get(id);
    }

    public Road getRoad(String id) {
        return srMap.get(id);
    }

    public Vehicle getVehicle(String id) {
        return svMap.get(id);
    }

    public List<Junction> getJunctions() {
        final List<Junction> aux = js;
        return aux;
    }

    public List<Road> getRoads() {
        final List<Road> aux = rs;
        return aux;
    }

    public List<Vehicle> getVehicles() {
        final List<Vehicle> aux = vs;
        return aux;
    }

    void reset() {
        svMap.clear();
        srMap.clear();
        sjMap.clear();
        vs.clear();
        rs.clear();
        js.clear();
    }

    public JSONObject report() {
        JSONObject rep = new JSONObject();
        JSONArray jQueue = new JSONArray();
        rep.put("junctions", jQueue);
        for (Junction j : js) {
            jQueue.put(j.report());
        }
        JSONArray rQueue = new JSONArray();
        rep.put("roads", rQueue);
        for (Road r : rs) {
            rQueue.put(r.report());
        }
        JSONArray vQueue = new JSONArray();
        rep.put("vehicles", vQueue);
        for (Vehicle v : vs) {
            vQueue.put(v.report());
        }
        return rep;
    }

}
