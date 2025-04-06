package simulator.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
	// Atributos
	Controller _ctrl;
	
	// elementos
	private JLabel time;
	private JLabel events_desc;
	
	public StatusBar(Controller _ctrl) {
		this._ctrl = _ctrl;
		initGUI();
		this._ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(Box.createRigidArea(new Dimension(15, 0)));
		time = new JLabel("Time: 0");
		time.setFont(new Font(getName(), Font.BOLD, 18));
		this.add(time);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		JSeparator sep = new JSeparator(JSeparator.VERTICAL);
		sep.setMaximumSize(new Dimension(1, 50));
		this.add(sep);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		events_desc = new JLabel("");
		events_desc.setFont(new Font(getName(), Font.BOLD, 18));
		this.add(events_desc);
		this.setVisible(true);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		this.time.setText("Time: " + time);
		events_desc.setText("");
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		events_desc.setText("Event added ("+ e.toString()+")");
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		this.time.setText("Time: 0");
		events_desc.setText("");
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub

	}

}
