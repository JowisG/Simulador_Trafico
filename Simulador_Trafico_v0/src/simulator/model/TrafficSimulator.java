package simulator.model;

import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator {
	/**
	 * 
	 *  Atributos
	 * 
	 * */
	private RoadMap map;
	private Queue<Event> event_queue;
	private int time;
	
	// Constructora public
	public TrafficSimulator() {
		map = new RoadMap();
		event_queue = new PriorityQueue<>();
		time = 0;
	}

	// Metodos
	public void addEvent(Event e) {
		event_queue.add(e);
	}
	
	public void advance() {
		this.time++;
		// Ejecutamos los eventos que se deb de ejecutar
		for(int i = event_queue.size()-1; i >= 0 ; i--) {
			Event e = event_queue.poll();
			if (time == e.time) {
				e.execute(map);
			}else
				event_queue.add(e);
		}
		// Advance de Junctions
		for (Junction j: map.getJunctions()) {
			j.advance(time);
		}
		// Advance de Roads
		for (Road r: map.getRoads()) {
			r.advance(time);
		}
	}
	
	public void reset() {
		map.reset();
		event_queue.clear();
		time = 0;
	}

	public JSONObject report() {
		JSONObject json = new JSONObject();
		json.put("time", time);
		json.put("state", map.report());
		return json;
	}
	
	// 
}
