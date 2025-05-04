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

public class JunctionTableModel extends AbstractTableModel implements TrafficSimObserver{

	// Atributos
		private String[] col_names = {"ID", "Green", "Queues"};
		private List<Junction> data;
		
		public JunctionTableModel(Controller _ctrl) {
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
				return data.get(rowIndex).getId();
			case 1:
				return (data.get(rowIndex).getGreenLightIndex() == -1)? "NONE" : data.get(rowIndex).getInRoads().get(data.get(rowIndex).getGreenLightIndex()).getId() ;
			case 2:
				return data.get(rowIndex).queuesToString();
			}
			return null;
		}

		@Override
		public void onAdvance(RoadMap map, Collection<Event> events, int time) {
			data.clear();
			for(Junction j: map.getJunctions())
				data.add(j);
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
			for(Junction j: map.getJunctions())
				data.add(j);
			fireTableDataChanged();
		}

}
