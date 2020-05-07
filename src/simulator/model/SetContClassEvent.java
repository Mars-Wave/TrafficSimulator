package simulator.model;

import simulator.misc.Pair;

import java.util.List;

public class SetContClassEvent extends Event {

    private List<Pair<String, Integer>> cs;

    public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
        super(time);
        if (cs == null)
            throw new NullPointerException();
        this.cs = cs;
    }

    @Override
    void execute(RoadMap map) {
        for (int i = 0; i < cs.size(); i++) {
            Vehicle v = map.getVehicle(cs.get(i).getFirst());
            if (v == null) {
                throw new NullPointerException();
            }
            v.setContaminationClass(cs.get(i).getSecond());
        }
    }
    
    @Override
    public String toString() {
		return "Change CO2 Class: "+ csString();
    	
    }
    
    private String csString() {
    	String csStr = "[";
		for(Pair<String, Integer> p : cs) {
			csStr += "(" + p.getFirst() + "," + p.getSecond() + ") ";
		}
		csStr += "]";
    	return csStr;
    	
    }

}
