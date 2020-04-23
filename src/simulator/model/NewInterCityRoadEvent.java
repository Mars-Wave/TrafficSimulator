package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

    public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit,
                                 int maxSpeed, Weather weather) {
        super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
    }

    @Override
    void execute(RoadMap map) {
    	 Junction src = map.getJunction(srcJun), dest = map.getJunction(destJunc);
        InterCityRoad r = new InterCityRoad(id, src, dest, length, co2Limit, maxSpeed, weather);
        map.addRoad(r);
    }
    
    @Override
    public String toString() {
		return "New Inter City Road '"+ id +"'";
    	
    }
    
}