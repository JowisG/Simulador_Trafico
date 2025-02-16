package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	/* Atributos */
	private Map<String, Builder<T>> builders;
	private List<JSONObject> builders_info;
	
	// Constructoras public
	public BuilderBasedFactory() {
		builders = new HashMap<>();
		builders_info = new LinkedList<>();
	}
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		// call add_builder(b) for each builder b in builder
		for(Builder<T> b: builders) {
			add_builder(b);
		}
	}

	// Otros metodos
	public void add_builder(Builder<T> b) {
		builders.put(b.get_type_tag(), b);
		builders_info.add(b.get_info());
	}
	
	// De la interfaz
	@Override
	public T create_instance(JSONObject info) {
		if(info == null)
			throw new IllegalArgumentException("'info' cannot be null");
		
		Builder<T> b = builders.get(info.getString("type"));
		T object = b.create_instance(info.has("data")? info.getJSONObject("data") : new JSONObject());
		if (object == null)
			throw new IllegalArgumentException("Unrecognized ‘info’:" + info.toString());
		return object;
		
	}

	@Override
	public List<JSONObject> get_info() {
		return Collections.unmodifiableList(builders_info);
	}

}
