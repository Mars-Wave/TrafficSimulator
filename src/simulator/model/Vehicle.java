package simulator.model;

import org.json.JSONObject;

import exception.carStatusException;
import exception.contClassException;
import exception.itineraryLengthException;
import exception.maxSpeedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vehicle extends SimulatedObject {

    private List<Junction> itinerary;
    private int maxSpeed;
    private int currentSpeed;
    private int currentJunction;
    private VehicleStatus status;
    private Road road = null;
    private int location;
    private int contClass;
    private int totalCont;
    private int totalDist;

    Vehicle(String id) {
        super(id);
    }

    Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary)
            throws maxSpeedException, contClassException, itineraryLengthException {
        super(id);
        if (maxSpeed < 0)
            throw new maxSpeedException("Max speed can not be negative");
        if (contClass > 10 || contClass < 0)
            throw new contClassException("Contamination class value must be between 0 and 10");
        if (itinerary.size() < 2)
            throw new itineraryLengthException("Itinerary must have at least two stops");
        this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
        this.maxSpeed = maxSpeed;
        this.contClass = contClass;
        totalCont = 0;
        currentJunction = 0;
        totalDist = 0;
        location = 0;
        status = VehicleStatus.PENDING;
    }

    public int getLocation() {
        return location;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public List<Junction> getItinerary() {
        return itinerary;
    }

    public Road getRoad() {
        return road;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }
    
    public int getCurrentSpeed() {
        return currentSpeed;
    }
    
    public int getCurrentJunc() {
    	return currentJunction;
    }
    
    public int getTotalCont() {
    	return totalCont;
    }
    
    public int getTotalDist() {
    	return totalDist;
    }

    public void setSpeed(int s) {
        if (s < 0)
            throw new maxSpeedException("Max speed can not be negative");
        if (status.equals(VehicleStatus.TRAVELING)) currentSpeed = Math.min(s, maxSpeed);
        else currentSpeed = 0;
    }

    public int getContClass() {
        return contClass;
    }

    public void setContaminationClass(int c) {
        if (c > 10 || c < 0)
            throw new contClassException("Contamination class value must be between 0 and 10");
        contClass = c;
    }

    @Override
    public void advance(int time) {
        if (status.equals(VehicleStatus.TRAVELING)) {
            int prevloc = location;
            location = Math.min((location + currentSpeed), road.getLength());
            totalCont += contClass * (location - prevloc);
            totalDist += location - prevloc;
            this.road.addContamination(contClass * (location - prevloc));
            if (location == road.getLength()) {
                currentJunction++;
                itinerary.get(currentJunction).enter(this);
                status = VehicleStatus.WAITING;
                currentSpeed = 0;

            }
        }
    }

    public void moveToNextRoad() {
        if (this.status != VehicleStatus.PENDING && this.status != VehicleStatus.WAITING) {
            throw new carStatusException("Car is not waiting nor pending. " + this);
        }
        if (this.status != VehicleStatus.PENDING && currentJunction != 0) {
            this.road.exit(this);
        }
        if (currentJunction != itinerary.size() - 1) {
            location = 0;
            this.road = (this.itinerary.get(currentJunction).roadTo(this.itinerary.get(currentJunction + 1)));
            this.road.enter(this);
            status = VehicleStatus.TRAVELING;
        } else if (currentJunction == itinerary.size() - 1) {
            status = VehicleStatus.ARRIVED;
        }
    }

    @Override
    public JSONObject report() {
        JSONObject rep = new JSONObject();
        rep.put("id", this.getId());
        rep.put("speed", currentSpeed);
        rep.put("distance", totalDist);
        rep.put("co2", totalCont);
        rep.put("class", contClass);
        rep.put("status", status);
        if (this.status != VehicleStatus.PENDING && this.status != VehicleStatus.ARRIVED) {
            rep.put("road", road.getId());
            rep.put("location", location);
        }
        return rep;
    }

}
