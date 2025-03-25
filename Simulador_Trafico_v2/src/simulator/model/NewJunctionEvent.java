package simulator.model;

public class NewJunctionEvent extends Event {

	/* Atributos */
	private String id;
	private LightSwitchingStrategy light_strategy;
	private DequeuingStrategy queue_strategy;
	// Coordenadas
	private int x;
	private int y;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
	  super(time);
	  this.id = id;
	  light_strategy = lsStrategy;
	  queue_strategy = dqStrategy;
	  x = xCoor;
	  y = yCoor;
	}

	@Override
	void execute(RoadMap map) {
		Junction j = new Junction(id, light_strategy, queue_strategy, x, y);
		map.addJunction(j);
	}
	
	@Override
	public String toString() {
		return "New Junction '"+id+"'";
	}
}
