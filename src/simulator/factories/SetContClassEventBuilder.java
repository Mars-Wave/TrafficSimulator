package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

import java.util.ArrayList;
import java.util.List;

public class SetContClassEventBuilder extends Builder<Event> {

    public SetContClassEventBuilder() {
        super("set_cont_class");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        JSONArray arr = data.getJSONArray("info");
        Pair<String, Integer> p;
        List<Pair<String, Integer>> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject item = arr.getJSONObject(i);
            p = new Pair<>(item.getString("vehicle"), item.getInt("class"));
            list.add(p);
        }
        return new SetContClassEvent(data.getInt("time"), list);
    }
}


