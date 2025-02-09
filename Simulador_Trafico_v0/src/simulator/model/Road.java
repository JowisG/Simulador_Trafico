package simulator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;


public abstract class Road extends SimulatedObject{
	/*
	 * 
	 * Atributos
	 * 
	 */
	
	// private and package protected
	private List<Vehicle> vehicles;
	
	private Junction junc_origin;
	private Junction junc_dest;
	
	private int length;
	private int speed_limit;
	private int act_speed_limit;
	
	private int max_pollution;
	private int total_pollution;
	private Weather weather;
	
	/** 
	 * Constructora protegida para paquete
	 * */
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) 
		throws IllegalArgumentException{
		super(id);
		if (maxSpeed <= 0)
			throw new IllegalArgumentException("maxSpeed must be positive");
		else
			speed_limit = maxSpeed;
		if (contLimit <= 0) 
			throw new IllegalArgumentException("the limit to contamination must be positive");
		else
			max_pollution = contLimit;
		if (length <= 0)
			throw new IllegalArgumentException("the lenght of the road must be positive");
		else 
			this.length = length;
		if (destJunc == null)
			throw new IllegalArgumentException("the destination Junction can't be null");
		else {
			junc_dest = destJunc;
			junc_dest.addIncommingRoad(this);
		}
		if (srcJunc == null)
			throw new IllegalArgumentException("the origin Junction can't be null");
		else {
			junc_origin = srcJunc; 
			junc_origin.addOutGoingRoad(this);
		}
		if (weather == null)
			throw new IllegalArgumentException("the weather can't be null");
		else
			this.weather = weather;
		
		act_speed_limit = speed_limit;
		total_pollution = 0;
		vehicles = new ArrayList<>();
	}
	
	// Metodos para interacciones con vehicles
	void enter(Vehicle v) throws IllegalArgumentException{
		if (v.getLocation() == 0 && v.getSpeed() == 0) {
			vehicles.add(v);
		}else {
			throw new IllegalArgumentException("Can't insert vehicle is location and/or speed aren't 0");
		}
	}
	
	void exit(Vehicle v) {
		for (int i = vehicles.size()-1; i > 0; i--) { // Se puede cambiar a un while
			if (v.getId() == vehicles.get(i).getId()) {
				vehicles.remove(i);
				break;
			}
		}
	}
	
	// Metodos set o de modificación
	void setWeather(Weather w) throws IllegalArgumentException{
		if (w == null)
			throw new IllegalArgumentException("the weather can`t be null");
		else
			weather = w;
	}
	
	void setSpeedLimit(int v) throws IllegalArgumentException { // TODO Puede que sea innecesario
		if (v < 0)
			throw new IllegalArgumentException("the speed must be positive (set Road)");
		else
			act_speed_limit = v;
	}
	
	void setTotalCO2(int c) throws IllegalArgumentException { // TODO Puede que sea innecesario
		if (c < 0)
			throw new IllegalArgumentException("the contamination must be positive (set Road)");
		else
			total_pollution = c;
	}
	
	void addContamination(int c) throws IllegalArgumentException {
		if (c < 0) 
			throw new IllegalArgumentException("the contamination to add to the road must be positive");
		else
			total_pollution += c;
	}
	
	// Metodos get
	int getLength() {
		return length;
	}
	Junction getSrc() {
		return junc_origin;
	}
	Junction getDest() {
		return junc_dest;
	}
	Weather getWeather() {
		return weather;
	}
	int getContLimit() {
		return max_pollution;
	}
	int getTotalCO2() {
		return total_pollution;
	}
	int getMaxSpeed() {
		return speed_limit;
	}
	int getSpeedLimit() {
		return act_speed_limit;
	}
	List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicles);
	}
	
	// Metodos abstractos
	abstract void reduceTotalContamination() throws IllegalArgumentException;
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	
	// Metodos de actualizar	
	@Override
	void advance(int time) {
		// Actualizamos datos de la carretera
		reduceTotalContamination();
		updateSpeedLimit();
		
		// Actualizamos vehiculos
		for (Vehicle v: vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
		// Ordenamos lista de vehiculos por location
		Collections.sort(vehicles, new VehicleComparator());
	}

	// Metodos de JSON
	@Override
	public JSONObject report() { // TODO revisar
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("speedlimit", speed_limit);
		json.put("weather", weather.toString());
		json.put("co2", total_pollution);
		List<String> vehicle_ids = new ArrayList<>();
		for(int i = 0; i < vehicles.size(); i++)
			vehicle_ids.add(vehicles.get(i).getId());
		json.put("vehicles", vehicle_ids);
		return json;
	}

}
