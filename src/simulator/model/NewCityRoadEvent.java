package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {

    public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
                            Weather weather) {
        super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
    }

    @Override
    void execute(RoadMap map) {
        Junction src = map.getJunction(srcJun), dest = map.getJunction(destJunc);
        CityRoad r = new CityRoad(id, src, dest, maxSpeed, co2Limit, length, weather);
        map.addRoad(r);
    }
    
    @Override
    public String toString() {
		return "New City Road '"+ id +"'";
    	
    }
}