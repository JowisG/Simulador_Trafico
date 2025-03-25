package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{
	
	// Atributos
	private String[] col_names = {"Time", "Description"};
	private List<Event> data;
	
	public EventsTableModel(Controller _ctrl) {
		Controller c = _ctrl;
		data = new ArrayList<>();
		c.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return (data.isEmpty())? 0 : data.size();
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
			return data.get(rowIndex).getTime();
		case 1:
			return data.get(rowIndex).toString();
		}
		return null;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		deleteEvent(time);
	}

	private void deleteEvent(int time) {
		for(int i = data.size()-1; i >= 0; i--) {
			if(data.get(i).getTime() <= time)
				data.remove(i);
		}
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		data.add(e);
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		data.clear();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		for(Event e: events)
			data.add(e);
		fireTableDataChanged();
	}

}
