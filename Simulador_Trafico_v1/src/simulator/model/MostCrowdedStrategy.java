package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	// Atributos private
	private int timeSlot;
	
	/**
	 *  Constructora
	 * */
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		int maxSize = 0;
		int pos = 0;
		
		if (roads.isEmpty()) return -1; // Para si road esta vacia
		
		if (currGreen == -1) { // Si estan todos rojos abre la más larga
			for (int i = 0; i < qs.size(); i++) { // Buscamos la más larga
				if (maxSize < qs.get(i).size()) {
					maxSize = qs.get(i).size();
					pos = i;
				}
			}
			return pos;
		}
		
		if(currTime-lastSwitchingTime < timeSlot) return currGreen;
		
		for (int i = currGreen+1; i < qs.size(); i++) { // Buscamos la más larga desde currGreen+1 y la ponemos en verde
			if (maxSize < qs.get(i).size()) {
				maxSize = qs.get(i).size();
				pos = i;
			}
		}
		return pos;
	}

}
