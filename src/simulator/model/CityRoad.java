package simulator.model;

public class CityRoad extends Road {

    CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    public void reduceTotalContamination() {
        int x;
        switch (this.weather) {
            case WINDY:
                x = 10;
                break;
            case STORM:
                x = 10;
                break;
            default:
                x = 2;
                break;
        }
        totalCont -= x;
        if (totalCont < 0)
            totalCont = 0;
    }

    @Override
    public void updateSpeedLimit() {
        // Empty by default

    }

    @Override
    public int calculateVehicleSpeed(Vehicle v) {
        return (int) (((11.0 - v.getContClass()) / 11.0) * maxSpeed);
    }

}
