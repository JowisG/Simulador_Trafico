package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {
	
	/* Atributos */
	List<Pair<String, Integer>> cont;
	
	public SetContClassEvent(int time, List<Pair<String,Integer>> cs)  {
	  super(time);
	  if (cs == null)
		  throw new IllegalArgumentException("Contamination class list is null");
	  else
		  cont = new ArrayList<>(cs);
	}
	
	@Override
	void execute(RoadMap map) {
		for(Pair<String, Integer> v: cont) {
			if (map.getVehicle(v.getFirst()) == null)
				throw new IllegalArgumentException("Road: " + v.getFirst() + " does no exist in the map");
			else
				map.getVehicle(v.getFirst()).setContClass(v.getSecond());
		}
	}

}
