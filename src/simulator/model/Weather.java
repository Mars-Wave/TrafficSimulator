package simulator.model;

import java.util.ArrayList;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;
	
	static public String[] names() {
		String[] names = null;
		for(int i = 0; i < Weather.values().length; i++) {
			names[i] = (values()[i].toString());
		}
		return names;
	}

}
