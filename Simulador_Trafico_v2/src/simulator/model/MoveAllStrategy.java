package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> v = new ArrayList<>();
		
		for (int i = 0; i < q.size(); i++) {
			v.add(q.get(i)); // No copio objetos
		}
		
		return v;
	}

}
