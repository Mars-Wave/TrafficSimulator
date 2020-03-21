package simulator.control;

import exception.NullObjectUsage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {
    private TrafficSimulator sim;
    private Factory<Event> eventsFactory;

    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
        if (sim == null) throw new NullObjectUsage("Simulator is null, cannot construct controller.");
        if (eventsFactory == null) throw new NullObjectUsage("eventsFactory is null, cannot construct controller.");
        this.sim = sim;
        this.eventsFactory = eventsFactory;
    }

    public void loadEvents(InputStream in) {
        JSONTokener tok = new JSONTokener(in);
        JSONObject jo = new JSONObject(tok);
        JSONArray ja = jo.getJSONArray("events");
        for (int i = 0; i < ja.length(); i++) {
            sim.addEvent(eventsFactory.createInstance(ja.getJSONObject(i)));
        }
    }

    public void run(int n, OutputStream out) {
        PrintStream p = new PrintStream(out);
        JSONArray states = new JSONArray();

        for (int i = 0; i < n; i++) {
            sim.advance();
            states.put(sim.report());
        }
        JSONObject totalReport = new JSONObject();
        totalReport.put("states", states);
        p.print(totalReport.toString(4));
    }

    public void reset() {
        sim.reset();
    }

}
