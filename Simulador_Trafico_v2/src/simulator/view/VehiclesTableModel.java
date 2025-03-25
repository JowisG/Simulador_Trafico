package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{
	
	// Atributos
	private String[] col_names = {"ID", "Location", "Itinerary", "CO2 Class", "Speed Limit", "Speed", "CO2", "Distance"};
	private List<Vehicle> data;
	
	public VehiclesTableModel(Controller _ctrl) {
		Controller c = _ctrl;
		data = new ArrayList<>();
		c.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return col_names.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return col_names[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return data.get(rowIndex).getId();
		case 1:
			return data.get(rowIndex).getRoad().getId() + ":" + data.get(rowIndex).getLocation(); 
		case 2:
			return stringJunctions(data.get(rowIndex).getItinerary());
		case 3:
			return data.get(rowIndex).getContClass();
		case 4:
			return data.get(rowIndex).getMaxSpeed();
		case 5:
			return data.get(rowIndex).getSpeed();
		case 6:
			return data.get(rowIndex).getTotalCO2();
		case 7:
			return data.get(rowIndex).getDistance();
		}
		return null;
	}

	private Object stringJunctions(List<Junction> junctions) {
		String s = "[";
		for(int i = 0; i < junctions.size(); i++) {
			s += junctions.get(i).getId();
			if(i < junctions.size()-1) s += ", ";
		}
		return s += "]";
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		// TODO forma ineficiente
		data.clear();
		for(Vehicle v: map.getVehicles())
			data.add(v);
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		data.clear();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// No me preocupo or duplicados y que solo se hace a la hora de registrar un JSON
		for(Vehicle v: map.getVehicles())
			data.add(v);
		fireTableDataChanged();
	}

}
