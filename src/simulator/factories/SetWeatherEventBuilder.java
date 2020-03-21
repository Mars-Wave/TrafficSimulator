package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class SetWeatherEventBuilder extends Builder<Event> {

    public SetWeatherEventBuilder() {
        super("set_weather");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        JSONArray arr = data.getJSONArray("info");
        Pair<String, Weather> p;
        List<Pair<String, Weather>> list = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject item = arr.getJSONObject(i);
            p = new Pair<>(item.getString("road"), Weather.valueOf(item.getString("weather").toUpperCase()));
            list.add(p);
        }
        return new SetWeatherEvent(data.getInt("time"), list);
    }

}