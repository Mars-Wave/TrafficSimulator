package simulator.control;

import exception.NullObjectUsage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
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
        p.println("{ " + "\"" + "states" + "\"" + ":[");
        for (int i = 0; i < n; i++) {
            sim.advance();
            p.print(sim.report().toString(4));
            if (i != n - 1) {
                p.print(",");
            }
        }
        p.println("]}");
    }

    public void reset() {
        sim.reset();
    }

    public void addObserver(TrafficSimObserver o) {
        sim.addObserver(o);
    }

    public void removeObserver(TrafficSimObserver o) {
        sim.removeObserver(o);
    }

    public void addEvent(Event e) {
        sim.addEvent(e);
    }

    public void run(int n) {    //Laters
        // TODO Auto-generated method stub
        run(n, null);
    }

}
