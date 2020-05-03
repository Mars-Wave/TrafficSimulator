package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import exception.contClassException;
import exception.maxSpeedException;
import exception.negativeValue;
import exception.vehicleCoherenceException;

import java.util.ArrayList;
import java.util.List;

public abstract class Road extends SimulatedObject {
	protected List<Vehicle> vehicles;
	protected Junction sourceJunction;
	protected Junction destJunction;
	protected int length;
	protected int maxSpeed;
	protected int currentSpeedLimit;
	protected int contaminationAlarmLimit;
	protected Weather weather;
	protected int totalCont;

	Road(String id) {
		super(id);
	}

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		if (maxSpeed < 0)
			throw new maxSpeedException("Max speed can not be negative");
		if (contaminationAlarmLimit < 0)
			throw new contClassException("Contamination alarm limit must be positive");
		if (length < 0)
			throw new negativeValue("Length value can not be negative");
		if (srcJunc == null | destJunc == null | weather == null)
			throw new NullPointerException();
		sourceJunction = srcJunc;
		destJunction = destJunc;
		this.maxSpeed = maxSpeed;
		contaminationAlarmLimit = contLimit;
		this.length = length;
		this.weather = weather;
		currentSpeedLimit = maxSpeed;
		destJunction.addIncomingRoad(this);
		sourceJunction.addOutgoingRoad(this);
		vehicles = new ArrayList<>();
	}

	public int getLength() {
		return length;
	}

	public Junction getDest() {
		return destJunction;
	}

	public Junction getSrc() {
		return sourceJunction;
	}
	
	public Weather getWeather() {
		return weather;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public int getSpeedLimit() {
		return currentSpeedLimit;
	}
	
	public int getTotalCont() {
		return totalCont;
	}
	
	public double getContLimit() {
		return contaminationAlarmLimit;
	}

	public void enter(Vehicle v) {
		if (v.getLocation() == 0 && v.getCurrentSpeed() == 0) {
			vehicles.add(v);
		} else {
			throw new vehicleCoherenceException(
					"The data for the vehicle is not coherent with the implicit rules " + this.getId());
		}

	}

	public void exit(Vehicle v) {
		vehicles.remove(v);
	}

	public void setWeather(Weather w) {
		if (w != null) {
			weather = w;
		} else
			throw new NullPointerException();
	}

	public void addContamination(int c) {
		if (c >= 0) {
			totalCont += c;

		} else
			throw new contClassException();
	}

	@Override
	public void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for (Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);

		}
		vehicles.sort((o1, o2) -> {
			if (o1.getLocation() > o2.getLocation())
				return 1;
			else if (o1.getLocation() < o2.getLocation())
				return 0;
			else
				return -1;
		});
	}

	@Override
	public JSONObject report() {
		JSONObject rep = new JSONObject();
		rep.put("id", getId());
		rep.put("speedlimit", currentSpeedLimit);
		rep.put("weather", weather.name());
		rep.put("co2", totalCont);
		JSONArray arr = new JSONArray();
		for (Vehicle v : vehicles) {
			arr.put(v.getId());
		}
		rep.put("vehicles", arr);
		return rep;
	}

	public abstract void reduceTotalContamination();

	public abstract void updateSpeedLimit();

	public abstract int calculateVehicleSpeed(Vehicle v);

    public List<Vehicle> getVehicles(){
    	return vehicles;
	}
}
