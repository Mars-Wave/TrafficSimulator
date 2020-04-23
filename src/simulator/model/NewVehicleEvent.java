package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {

    private String id;
    private int maxSpeed;
    private int contClass;
    private List<String> itinerary;

    public NewVehicleEvent(int time, String id, int maxSpeed, int
            contClass, List<String> itinerary) {
        super(time);
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.contClass = contClass;
        this.itinerary = itinerary;
    }

    @Override
    void execute(RoadMap map) {
        List<Junction> realitinerary = new ArrayList<>();
        for (int i = 0; i < itinerary.size(); i++) {
            realitinerary.add(map.getJunction(itinerary.get(i)));
        }
        Vehicle v = new Vehicle(id, maxSpeed, contClass, realitinerary);
        map.addVehicle(v);
        v.moveToNextRoad();
    }
    
    @Override
    public String toString() {
		return "New Vehicle '"+ id +"'";
    	
    }


}
