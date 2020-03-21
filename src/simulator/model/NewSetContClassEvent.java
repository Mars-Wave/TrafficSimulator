package simulator.model;

import simulator.misc.Pair;

import java.util.List;

public class NewSetContClassEvent extends Event {

    private List<Pair<String, Integer>> cs;

    public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) {
        super(time);
        if (cs == null)
            throw new NullPointerException();
        this.cs = cs;
    }

    @Override
    void execute(RoadMap map) {
        for (Pair<String, Integer> c : cs) {
            Vehicle v = map.getVehicle(c.getFirst());
            if (v == null) {
                throw new NullPointerException();
            }
            v.setContaminationClass(c.getSecond());
        }
    }

}
