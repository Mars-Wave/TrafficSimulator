package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {

    public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
                            Weather weather) {
        super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
    }

    @Override
    void execute(RoadMap map) {
        Junction src = null, dest = null;
        boolean found = false;
        for (int i = 0; i < map.getJunctions().size() && !found; i++) {
            if (map.getJunctions().get(i).getId().equalsIgnoreCase(srcJun)) {
                src = map.getJunctions().get(i);
                found = true;
            }
        }
        found = false;
        for (int i = 0; i < map.getJunctions().size() && !found; i++) {
            if (map.getJunctions().get(i).getId().equalsIgnoreCase(destJunc)) {
                dest = map.getJunctions().get(i);
                found = true;
            }
        }
        CityRoad r = new CityRoad(id, src, dest, maxSpeed, co2Limit, length, weather);
        map.addRoad(r);
    }
}