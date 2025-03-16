package simulator.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	Controller _ctrl;

	public ControlPanel (Controller ctrl){
		this._ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		/* Pongo las preferencias con una dimension y un BoxLayout */
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		// Creo el JToolBar
		JToolBar bar = new JToolBar();
		
		// Añadimos los botones
		JButton file_btn = new JButton();
		file_btn.setIcon(new ImageIcon("resources/icons/open.png"));
		bar.add(file_btn);
		
		bar.addSeparator();
		JSeparator sep_file = new JSeparator();
		sep_file.setOrientation(JSeparator.VERTICAL);
		bar.add(sep_file);
		bar.addSeparator();
		
		JButton co2_btn = new JButton();		co2_btn.setIcon(new ImageIcon("resources/icons/co2class.png"));
		bar.add(co2_btn);
		
		JButton weather_btn = new JButton();		weather_btn.setIcon(new ImageIcon("resources/icons/weather.png"));
		bar.add(weather_btn);
		
		JButton run_btn = new JButton();		run_btn.setIcon(new ImageIcon("resources/icons/run.png"));
		bar.add(run_btn);
		
		JButton stop_btn = new JButton();		stop_btn.setIcon(new ImageIcon("resources/icons/stop.png"));
		bar.add(stop_btn);
		
		JLabel ticks_label = new JLabel("Ticks: ");
		JSpinner ticks = new JSpinner();
		ticks.setMaximumSize(new Dimension(2000, 50));
		ticks.setValue(10);
		ticks_label.add(ticks);
		bar.add(ticks_label);
		bar.add(ticks);
		
		bar.addSeparator();
		// Creating a "Glue" sp that quit goes at the right
		bar.add(Box.createHorizontalGlue());
		JSeparator sep_end = new JSeparator();
		sep_end.setOrientation(JSeparator.VERTICAL);
		bar.add(sep_end);
		bar.addSeparator();
		
		JButton quit_btn = new JButton();
		quit_btn.setIcon(new ImageIcon("resources/icons/exit.png"));
		bar.add(quit_btn);
		
		// Añadimos el JToolBar al ControlPanel
		this.add(bar);
		this.setVisible(true);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

}
