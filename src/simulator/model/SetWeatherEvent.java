package simulator.model;

import exception.unexistingReferencedObject;
import simulator.misc.Pair;

import java.util.List;

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
        for (Pair<String, Weather> w : ws) {
            if (map.getRoad(w.getFirst()) == null) throw new NullPointerException();
            map.getRoad(w.getFirst()).setWeather(w.getSecond());
        }
    }

}
