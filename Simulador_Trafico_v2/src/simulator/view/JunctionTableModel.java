package simulator.view;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;

public class JunctionTableModel extends AbstractTableModel {

	private Controller _ctrl;
	
	public JunctionTableModel(Controller _ctrl) {
		this._ctrl = _ctrl;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
