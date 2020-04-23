package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEventBuilder extends Builder<Event> {

    public NewVehicleEventBuilder() {
        super("new_vehicle");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        JSONArray arr = data.getJSONArray("itinerary");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < arr.length(); i++) {
            list.add(arr.getString(i));
        }
        return new NewVehicleEvent(data.getInt("time"), data.getString("id"), data.getInt("maxspeed"),
                data.getInt("class"), list);
    }

}
