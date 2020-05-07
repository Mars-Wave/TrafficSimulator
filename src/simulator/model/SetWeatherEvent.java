package simulator.model;

import simulator.misc.Pair;

import java.util.List;

import exception.unexistingReferencedObject;

public class SetWeatherEvent extends Event {

    private List<Pair<String, Weather>> ws;

    public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
        super(time);
        if (ws != null) {
			this.ws = ws;
		}
        else {
			throw new unexistingReferencedObject("The list doesn't exist.");
		}
    }

    @Override
    void execute(RoadMap map) {
        for (int i = 0; i < ws.size(); i++) {
            if (map.getRoad(ws.get(i).getFirst()) == null) throw new NullPointerException();
            map.getRoad(ws.get(i).getFirst()).setWeather(ws.get(i).getSecond());
        }
    }
    
    @Override
    public String toString() {
		return "New Weather "+ wsString();
    	
    }
    
    private String wsString() {
    	String wsStr = "[";
		for(Pair<String, Weather> p : ws) {
			wsStr += "(" + p.getFirst() + "," + p.getSecond() + ") ";
		}
		wsStr += "]";
    	return wsStr;
    	
    }

}
