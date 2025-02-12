package simulator.model;

public abstract class NewRoadEvent extends Event {
	
	/* Atributos */
	protected String id;
	protected String origin_junc;
	protected String dest_junc;
	protected int length;
	protected int max_pollution;
	protected int max_speed;
	protected Weather weather;
	
	public NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
	  super(time);
	  this.id = id;
	  origin_junc = srcJun;
	  dest_junc = destJunc;
	  this.length = length;
	  max_pollution = co2Limit;
	  max_speed = maxSpeed;
	  this.weather = weather;
	}
}
