package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	/**
	 * 
	 *  Atributos
	 * 
	 * */
	private RoadMap map;
	private Queue<Event> event_queue;
	private int time;
	
	// Observadores
	private List<TrafficSimObserver> observers;
	
	// Constructora public
	public TrafficSimulator() {
		map = new RoadMap();
		event_queue = new PriorityQueue<>();
		time = 0;
		observers = new ArrayList<>();
	}

	// Metodos
	public void addEvent(Event e) {
		event_queue.add(e);
		// Actualizamos los observadores :(
		for(TrafficSimObserver o: observers) {
			o.onEventAdded(map, event_queue, e, time);
		}
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
		
		// Actualizamos los observadores :(
		for(TrafficSimObserver o: observers) {
			o.onAdvance(map, event_queue, time);
		}
	}

	public void reset() {
		map.reset();
		event_queue.clear();
		time = 0;
		// Actualizamos los observadores :(
		for(TrafficSimObserver o: observers) {
			o.onReset(map, event_queue, time);
		}
	}

	public JSONObject report() {
		JSONObject json = new JSONObject();
		json.put("time", time);
		json.put("state", map.report());
		return json;
	}

	/* Cosas de Observable */
	@Override
	public void addObserver(TrafficSimObserver o) {
		observers.add(o);
		// Actualizamos los observadores :(
		/*for(TrafficSimObserver o1: observers) {
			o1.onRegister(map, event_queue, time);
		}*/
		// Creo que solo se actualiza el que se registra
		o.onRegister(map, event_queue, time);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		observers.remove(o);
	}
}
