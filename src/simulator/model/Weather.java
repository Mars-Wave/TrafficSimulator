package simulator.model;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;

	public static Weather[] weathers() {
		return new Weather[]{SUNNY, CLOUDY, RAINY, WINDY, STORM};
	}


}
