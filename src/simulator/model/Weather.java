package simulator.model;

import java.util.ArrayList;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;
	private static final String[] weathers = {"SUNNY", "CLOUDY", "RAINY", "WINDY", "STORM"};

	public static String[] names() {
		return weathers;
	}


}
