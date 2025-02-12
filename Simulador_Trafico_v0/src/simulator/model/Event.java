package simulator.model;

public abstract class Event implements Comparable<Event> {
	/* Atributos */
	// Private
	private static long counter = 0;
	
	//protected
	protected int time;
	protected long time_stamp;
	
	// Constructora package protected
	Event(int time) throws IllegalArgumentException{
		if(time < 1)
			throw new IllegalArgumentException("Invalid time: " + time);
		else {
			this.time = time;
			this.time_stamp = counter++; 
		}
	}
	
	//Getter
	int getTime() {
		return time;
	}
	
	// Abstractas
	abstract void execute(RoadMap map);
	
	@Override
	public int compareTo(Event o) {
		if (this.time > o.time)
			return 1;
		else if (this.time < o.time)
			return -1;
		else {
			if (this.time_stamp > o.time_stamp)
				return 1;
			else // En el caso de que tenga tanto el time como timestamp iguales se devuelve -1
				return -1;
		}
	}

}
