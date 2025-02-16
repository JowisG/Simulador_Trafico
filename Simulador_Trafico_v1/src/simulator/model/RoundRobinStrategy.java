package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {
	
	// Atributos private
	private int timeSlot;
	
	/**
	 *  Constructora
	 * */
	public RoundRobinStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		if(roads.isEmpty()) return -1; // Si roads esta vacia
		
		if (currGreen == -1) return 0; // Si todos estan cerrados
		
		if(currTime-lastSwitchingTime < timeSlot) return currGreen;
		
		// Abrimos un semaforo más por cada vez que llegue a esta condición hasta que llegue a roads.size()-1
		return (currGreen+1 < roads.size())? currGreen+1: 0;
	}

}
