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
	
	Controller _ctrl;
	
	public StatusBar(Controller _ctrl) {
		this._ctrl = _ctrl;
		initGUI();
		this._ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(Box.createRigidArea(new Dimension(15, 0)));
		JLabel time = new JLabel("Time: 0");
		time.setFont(new Font(getName(), Font.BOLD, 18));
		this.add(time);
		this.add(Box.createRigidArea(new Dimension(40, 0)));
		JSeparator sep = new JSeparator(JSeparator.VERTICAL);
		this.add(sep);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		JLabel events_desc = new JLabel("Event added (None)");
		events_desc.setFont(new Font(getName(), Font.BOLD, 18));
		this.add(events_desc);
		this.setVisible(true);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		JLabel text = (JLabel) this.getComponent(1);
		text.setText("Time: " + time);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		JLabel text = (JLabel) this.getComponent(5);
		text.setText("Event added ("+ e.toString()+")");
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		JLabel text = (JLabel) this.getComponent(1);
		text.setText("Time: 0");
		text = (JLabel) this.getComponent(5);
		text.setText("Event added (None)");
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub

	}

}
