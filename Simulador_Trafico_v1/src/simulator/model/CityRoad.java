package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws IllegalArgumentException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() throws IllegalArgumentException{
		int x;
		if (getWeather() == Weather.SUNNY || getWeather() == Weather.CLOUDY ||  getWeather() == Weather.RAINY) x = 2;
		else if (getWeather() == Weather.STORM || getWeather() == Weather.WINDY) x = 10;
		else throw new IllegalArgumentException("Weather does not exist (reduceCO2)");
		
		setTotalCO2((getTotalCO2() - x <= 0)? 0: getTotalCO2() - x); // TODO Puede cambiar
	}

	@Override
	void updateSpeedLimit() {
		// No cambia la velocidad limite siempre es la misma
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return ((11-v.getContClass())*getSpeedLimit())/11;
	}

}
