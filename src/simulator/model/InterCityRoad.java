package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id) {
		super(id);
	}

	public InterCityRoad(String id, Junction src, Junction dest, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(id,  src,  dest,  maxSpeed,  co2Limit,  length, weather);
	
	}

	@Override
	
	public void reduceTotalContamination() {
		int x;
		switch (this.weather) {
		case SUNNY:
			x=2;
		break;
		case CLOUDY:
			x=3;
		break;
		case RAINY:
			x=10;
		break;
		case WINDY:
			x=15;
		break;
		case STORM:
			x=20;
		break;
		default:
			x=0;
			break;
		}
	this.totalCont = (int)(((100.0-x)/100.0)*this.totalCont);
		

	}

	@Override
	public void updateSpeedLimit() {
		if(totalCont > contaminationAlarmLimit) 
			currentSpeedLimit = (int)(maxSpeed*0.5);
		else 	
			currentSpeedLimit = maxSpeed;
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		if(weather.equals(Weather.STORM)) return (int)(this.currentSpeedLimit * 0.8);
		else return this.currentSpeedLimit;
	}

}
