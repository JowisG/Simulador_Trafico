package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	/* Atributos */
	List<Pair<String, Weather>> weather;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
	  super(time);
	  if (ws == null)
		  throw new IllegalArgumentException("Weather list is null");
	  else
		  weather = new ArrayList<>(ws);
	}
	
	@Override
	void execute(RoadMap map) {
		for(Pair<String, Weather> w: weather) {
			if (map.getRoad(w.getFirst()) == null)
				throw new IllegalArgumentException("Road: " + w.getFirst() + " does no exist in the map");
			else
				map.getRoad(w.getFirst()).setWeather(w.getSecond());
		}		
	}

}
