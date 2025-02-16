package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather", "Set Weather Event");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		
		List<Pair<String, Weather>> weather_change = new ArrayList<>();
		JSONArray json_w_change = new JSONArray();
		json_w_change = data.getJSONArray("info");
		for (int i = 0; i < json_w_change.length(); i++) {
			JSONObject json = json_w_change.getJSONObject(i);
			Weather w = Weather.valueOf(json.getString("weather"));
			weather_change.add(new Pair<String, Weather>(json.getString("road"), w));
		}
		
		return new SetWeatherEvent(time, weather_change);
	}

}
