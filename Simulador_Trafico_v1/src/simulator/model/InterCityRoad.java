package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws IllegalArgumentException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() throws IllegalArgumentException{
		// Contaminacion = ((100-x)*tc)/100 tc = contaminacion actual
		int x;
		if (getWeather() == Weather.SUNNY) x = 2;
		else if (getWeather() == Weather.CLOUDY) x = 3;
		else if (getWeather() == Weather.RAINY) x = 10;
		else if (getWeather() == Weather.WINDY) x = 15;
		else if (getWeather() == Weather.STORM) x = 20;
		else throw new IllegalArgumentException("Weather does not exist (reduceCO2)");
		
		setTotalCO2(((100-x)*getTotalCO2())/100); // TODO Puede cambiar
		//total_pollution = ((100-x)*getTotalCO2())/100;
	}

	@Override
	void updateSpeedLimit() {
		if (getTotalCO2() > getContLimit())
			setSpeedLimit(getMaxSpeed()/2);
		else
			setSpeedLimit(getSpeedLimit());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int reduceTo = getSpeedLimit();
		if (getWeather() == Weather.STORM)
			reduceTo = (reduceTo*8)/10; // Si no quieres que se apliquen ambas => reduceTo = (getMaxSpeed()*8)/10
		return reduceTo;
	}

}
