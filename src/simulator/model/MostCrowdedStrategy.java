package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

    private int timeSlot;

    public MostCrowdedStrategy(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
        if (roads.isEmpty()) return -1;
        else if (currGreen == -1) return findMax(qs, 0);
        else if (currTime - lastSwitchingTime < timeSlot) return currGreen;
        else return findMax(qs, (currGreen + 1)%qs.size());

    }

    private int findMax(List<List<Vehicle>> qs, int a) {
        int pos = 0;
        int tempMax = 0;
        int max = 0;
        int j = a;
        do {
            tempMax = qs.get(j).size();
            if (tempMax > max) {
                max = tempMax;
                pos = j;
            }
            j = (j + 1) % qs.size();
        } while (j != a);
        return pos;
    }

}
