package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	private String _id;
	
	public NewVehicleEventBuilder() {
		super("new_vehicle", "New Vehicle Event");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		_id = data.getString("id");
		int maxSpeed = data.getInt("maxspeed");
		int contClass = data.getInt("class");
		
		JSONArray json_itinerary = data.getJSONArray("itinerary");
		List<String> itinerary = new ArrayList<>();
		for(int i = 0; i < json_itinerary.length(); i++) {
			itinerary.add(json_itinerary.getString(i));
		}
		
		return new NewVehicleEvent(time, _id, maxSpeed, contClass, itinerary);
	}
	
	@Override
	public String toString() {
		return "New Vehicle '"+_id+"'";
	}
}
