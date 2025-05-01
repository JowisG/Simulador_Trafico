package simulator.view;

import java.awt.Dialog;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;

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

	public ControlPanel (Controller ctrl){
		this._ctrl = ctrl;
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
		file_btn.setToolTipText("Open a file");
		bar.add(file_btn);
		
		separator(bar);
		
		
		//crear el diálogo
		ChangeCO2ClassDialog dco2 = new ChangeCO2ClassDialog(ViewUtils.getWindow(this),_ctrl);
				co2_btn = new JButton();
		co2_btn.setIcon(new ImageIcon("resources/icons/co2class.png"));
		co2_btn.addActionListener(e -> {
			dco2.setLocationRelativeTo(ViewUtils.getWindow(this));
			dco2.open();
		});
		co2_btn.setToolTipText("Change the contamination class of a vehicle");
		bar.add(co2_btn);
		
		// crear dialogo
		ChangeWeatherDailog dweather = new ChangeWeatherDailog(ViewUtils.getWindow(this), _ctrl);
		
		weather_btn = new JButton();		weather_btn.setIcon(new ImageIcon("resources/icons/weather.png"));
		weather_btn.addActionListener(e -> {
			dweather.setLocationRelativeTo(ViewUtils.getWindow(this));
			dweather.open();
		});
		weather_btn.setToolTipText("Change the weather of a road");
		bar.add(weather_btn);
		
		separator(bar);
		
		run_btn = new JButton();		run_btn.setIcon(new ImageIcon("resources/icons/run.png"));
		run_btn.setToolTipText("Run simulation x ticks");
		bar.add(run_btn);
		
		stop_btn = new JButton();
		stop_btn.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop_btn.addActionListener(e ->{stopped = true;});
		stop_btn.setToolTipText("Stop simlation");
		bar.add(stop_btn);		
		JLabel ticks_label = new JLabel("Ticks: ");
		SpinnerNumberModel numSpin = new SpinnerNumberModel(_ticks, 1, Integer.MAX_VALUE, 1);
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
		quit_btn.setToolTipText("Quit simulator");
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
		if(chooser.showOpenDialog(this.getParent()) == JFileChooser.APPROVE_OPTION) {
			try {
				InputStream input = new FileInputStream(chooser.getSelectedFile());
				_ctrl.reset();
				_ctrl.loadEvents(input);
			} catch (FileNotFoundException e1) {
				JOptionPane.showConfirmDialog(this.getParent(), "Choosen file does not exist or is not accessable", "File not found", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (IllegalArgumentException e2) {
				JOptionPane.showConfirmDialog(this.getParent(), "The format the .json file has is incorrect, please select another file", "Incorrect format", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void close_Dialog() {
		int close_dig = JOptionPane.showConfirmDialog(ViewUtils.getWindow(this), "Do you really want to close Traffic Simulator?", "Close Traffic Simulator", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (close_dig == 0)
			ViewUtils.getWindow(this).dispose();
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
		
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		
	}

}
