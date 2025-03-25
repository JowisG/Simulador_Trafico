package simulator.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	private Controller _ctrl;
	private int _ticks = 10;
	private boolean stopped;
	
	// Buttons
	private JButton file_btn;
	private JButton co2_btn;
	private JButton weather_btn;
	private JButton run_btn;
	private JButton stop_btn;
	private JButton quit_btn;
	
	private JSpinner ticks;
	
	// Info map
	List<String> vehicle_spin_list;

	public ControlPanel (Controller ctrl){
		this._ctrl = ctrl;
		vehicle_spin_list = new ArrayList<>();
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		/* Pongo las preferencias con una dimension y un BoxLayout */
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		// Creo el JToolBar
		JToolBar bar = new JToolBar();
		
		// Añadimos los botones
		file_btn = new JButton();
		file_btn.setIcon(new ImageIcon("resources/icons/open.png"));
		file_btn.addActionListener(e -> {
			load_file();
		});
		bar.add(file_btn);
		
		separator(bar);
		
		co2_btn = new JButton();		co2_btn.setIcon(new ImageIcon("resources/icons/co2class.png"));
		co2_btn.addActionListener(e -> {
			co2_btn();
		});
		bar.add(co2_btn);
		
		weather_btn = new JButton();		weather_btn.setIcon(new ImageIcon("resources/icons/weather.png"));
		weather_btn.addActionListener(e -> {
			weather_change();
		});
		bar.add(weather_btn);
		
		separator(bar);
		
		run_btn = new JButton();		run_btn.setIcon(new ImageIcon("resources/icons/run.png"));
		bar.add(run_btn);
		
		stop_btn = new JButton();
		stop_btn.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop_btn.addActionListener(e ->{stopped = true;});		bar.add(stop_btn);
		
		JLabel ticks_label = new JLabel("Ticks: ");
		SpinnerNumberModel numSpin = new SpinnerNumberModel();
		ticks = new JSpinner(numSpin);
		ticks.setMaximumSize(new Dimension(2000, 50));
		ticks.setValue(10);
		ticks_label.add(ticks);
		bar.add(ticks_label);
		bar.add(ticks);
		
		// Creating a "Glue" sp that quit goes at the right
		bar.add(Box.createHorizontalGlue());
		separator(bar);
		
		quit_btn = new JButton();
		quit_btn.setIcon(new ImageIcon("resources/icons/exit.png"));
		quit_btn.addActionListener(e -> {
			close_Dialog();
		});
		bar.add(quit_btn);

		
		// Actions listener
		run_btn.addActionListener(e -> {
			_ticks = numSpin.getNumber().intValue();
			stopped = false;
			run_btn.setEnabled(false);
			file_btn.setEnabled(false);
			co2_btn.setEnabled(false);
			weather_btn.setEnabled(false);
			ticks.setEnabled(false);
			quit_btn.setEnabled(false);
			runTicks(_ticks);
		});
		
		// Añadimos el JToolBar al ControlPanel
		this.add(bar);
		this.setVisible(true);
	}

	private void weather_change() {
		JDialog dweather = new JDialog(null, "Change CO2 Class", Dialog.ModalityType.APPLICATION_MODAL);
		dweather.setSize(new Dimension(500, 200));
		dweather.setVisible(true);
	}

	private void co2_btn() {
		JDialog dco2 = new JDialog(null, "Change CO2 Class", Dialog.ModalityType.APPLICATION_MODAL);
		dco2.setSize(new Dimension(500, 200));
		dco2.setLayout(new GridLayout(3, 1));
		
		JLabel text = new JLabel("Schedule an event to change the CO2 class of a vehicle after a given number of ticks from now.");
		dco2.add(text);
		
		// Para los spinners
		JPanel spinners_panel = new JPanel();
		spinners_panel.setLayout(new BoxLayout(spinners_panel, BoxLayout.X_AXIS));
		JLabel vehicle_label = new JLabel("Vehicle: ");
		spinners_panel.add(vehicle_label);
		if (vehicle_spin_list.isEmpty())
			vehicle_spin_list.add("none");
		SpinnerListModel vehicle_spin_model = new SpinnerListModel(vehicle_spin_list);
		JSpinner vehicle_spin = new JSpinner(vehicle_spin_model);
		spinners_panel.add(vehicle_spin);
		dco2.add(spinners_panel);
		
		// Para los botones
		JPanel btn_panel = new JPanel();
		JButton ok_btn = new JButton();
		ok_btn.setText("Comfirm");
		btn_panel.add(ok_btn);
		JButton cancel_btn = new JButton();
		cancel_btn.setText("Cancel");
		cancel_btn.addActionListener(e -> {
			dco2.dispose();
		});
		btn_panel.add(cancel_btn);
		dco2.add(btn_panel);
		
		dco2.setVisible(true);
	}

	private void runTicks(int n) {
		if (n > 0 && !stopped) {
			try {
				_ctrl.run(1);
				SwingUtilities.invokeLater(() -> runTicks(n-1));
			}catch (Exception e) {
				JOptionPane.showConfirmDialog(this, "The simulation has encounter an error", "Run did't work", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				stopped = true;
				run_btn.setEnabled(true);
				file_btn.setEnabled(true);
				co2_btn.setEnabled(true);
				weather_btn.setEnabled(true);
				ticks.setEnabled(true);
				quit_btn.setEnabled(true);
			}
		}else {
			stopped = true;
			run_btn.setEnabled(true);
			file_btn.setEnabled(true);
			co2_btn.setEnabled(true);
			weather_btn.setEnabled(true);
			ticks.setEnabled(true);
			quit_btn.setEnabled(true);
		}
	}

	private void load_file() {
		stopped = true;
		JFileChooser chooser = new JFileChooser("./resources/examples");
		chooser.setFileFilter(new FileNameExtensionFilter("JSON file", "json"));
		if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				InputStream input = new FileInputStream(chooser.getSelectedFile());
				_ctrl.reset();
				_ctrl.loadEvents(input);
			} catch (FileNotFoundException e) {
				JOptionPane.showConfirmDialog(chooser, "Choosen file does not exist or is not accessable", "File not found", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	
	private void close_Dialog() {
		// Closes but because it does not have a reference to the mainWidow it cant dispose of it
		int close_dig = JOptionPane.showConfirmDialog(this, "Do you really want to close Traffic Simulator?", "Close Traffic Simulator", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (close_dig == 0)
			System.exit(0);
		// TODO use dispose with a method that gives you the windows it lives in
	}

	private void separator(JToolBar bar) {
		bar.addSeparator();
		JSeparator sep = new JSeparator();
		sep.setOrientation(JSeparator.VERTICAL);
		sep.setMaximumSize(new Dimension(0, 50));
		bar.add(sep);
		bar.addSeparator();
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		vehicle_spin_list.clear();
		for(int i = 0; i < map.getVehicles().size(); i++) {
			vehicle_spin_list.add(map.getVehicles().get(i).getId());
		}	
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		vehicle_spin_list.clear();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		vehicle_spin_list.clear();
		for(int i = 0; i < map.getVehicles().size(); i++) {
			vehicle_spin_list.add(map.getVehicles().get(i).getId());
		}
	}

}
