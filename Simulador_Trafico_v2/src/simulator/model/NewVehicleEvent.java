package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	/* Atributos */
	private String id;
	private int max_speed;
	private int grade_pollution;
	private List<String> itinerary;
	
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
	  super(time);
	  this.id = id;
	  max_speed = maxSpeed;
	  grade_pollution = contClass;
	  this.itinerary = new ArrayList<>(itinerary);
	}
	
	@Override
	void execute(RoadMap map) {
		List<Junction> junctions = new ArrayList<>();
		for (int i = 0; i < itinerary.size(); i++) {
			Junction j = map.getJunction(itinerary.get(i));
			junctions.add(j);
		}
		Vehicle v = new Vehicle(id, max_speed, grade_pollution, junctions);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
	
	@Override
	public String toString() {
		return "New Vehicle '"+id+"'";
	}
}
