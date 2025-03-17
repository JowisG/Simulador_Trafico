package simulator.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;
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
		file_btn.addActionListener(e -> {
			load_file();
		});
		bar.add(file_btn);
		
		separator(bar);
		
		JButton co2_btn = new JButton();		co2_btn.setIcon(new ImageIcon("resources/icons/co2class.png"));
		bar.add(co2_btn);
		
		JButton weather_btn = new JButton();		weather_btn.setIcon(new ImageIcon("resources/icons/weather.png"));
		bar.add(weather_btn);
		
		separator(bar);
		
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
		
		// Creating a "Glue" sp that quit goes at the right
		bar.add(Box.createHorizontalGlue());
		separator(bar);
		
		JButton quit_btn = new JButton();
		quit_btn.setIcon(new ImageIcon("resources/icons/exit.png"));
		bar.add(quit_btn);
		
		// Añadimos el JToolBar al ControlPanel
		this.add(bar);
		this.setVisible(true);
	}
	
	private void load_file() {
		JFileChooser chooser = new JFileChooser("./resources");
		chooser.setFileFilter(new FileNameExtensionFilter("JSON file", "json"));
		if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				// TODO mejorar esto
				InputStream input = new FileInputStream(chooser.getSelectedFile());
				_ctrl.reset();
				_ctrl.loadEvents(input);
				_ctrl.run(0);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void separator(JToolBar bar) {
		bar.addSeparator();
		JSeparator sep_file = new JSeparator();
		sep_file.setOrientation(JSeparator.VERTICAL);
		bar.add(sep_file);
		bar.addSeparator();
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
