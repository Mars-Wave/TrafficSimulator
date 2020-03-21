package simulator.factories;

import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

    private Factory<DequeuingStrategy> dqsFactory;
    private Factory<LightSwitchingStrategy> lssFactory;

    public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
        super("new_junction");
        this.lssFactory = lssFactory;
        this.dqsFactory = dqsFactory;
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        return new NewJunctionEvent(data.getInt("time"), data.getString("id"), this.lssFactory.createInstance(data.getJSONObject("ls_strategy")), this.dqsFactory.createInstance(data.getJSONObject("dq_strategy")),
                data.getJSONArray("coor").getInt(0), data.getJSONArray("coor").getInt(1));
    }

}
