package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class", "Set ContClass Event");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		
		List<Pair<String, Integer>> vehicles = new ArrayList<>();
		JSONArray json_w_change = new JSONArray();
		json_w_change = data.getJSONArray("info");
		for (int i = 0; i < json_w_change.length(); i++) {
			JSONObject json = json_w_change.getJSONObject(i);
			vehicles.add(new Pair<String, Integer>(json.getString("vehicle"), json.getInt("class")));
		}
		
		return new SetContClassEvent(time, vehicles);
	}

}
