package simulator.model;

import java.util.ArrayList;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;
	private static final String[] weathers = {"SUNNY", "CLOUDY", "RAINY", "WINDY", "STORM"};

	public static Weather[] weathers() {
		Weather[] weatherList = {Weather.SUNNY, Weather.CLOUDY, Weather.RAINY, Weather.WINDY, Weather.STORM};
		return weatherList;
	}


}
