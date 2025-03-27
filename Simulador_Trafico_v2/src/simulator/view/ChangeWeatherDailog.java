package simulator.view;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ChangeWeatherDailog extends JDialog implements TrafficSimObserver {

	// Atributos
		private Controller _ctrl;
		private int time = 0;
		
		// elementos
		private JDialog weather;
		private JComboBox<String> road_box;
		private JComboBox<String> weather_box;
		private SpinnerNumberModel numSpin;
		
		// Info map
		List<String> road_spin_list = new ArrayList<>();

		
		public ChangeWeatherDailog(Controller _ctrl) {
			this._ctrl = _ctrl;
			_ctrl.addObserver(this);
			initGUI();
		}

		private void initGUI() {
			weather = new JDialog(null, "Change Weather", Dialog.ModalityType.APPLICATION_MODAL);
			weather.setSize(new Dimension(500, 200));
			weather.setLayout(new GridLayout(3, 1));
			
			JLabel text = new JLabel("<html><p>Schedule an event to change the weather of a road after a given number of simulation ticks from now.</p></html>");
			weather.add(text);
			
			// Para los spinners
			JPanel spinners_panel = new JPanel();
			spinners_panel.setLayout(new BoxLayout(spinners_panel, BoxLayout.X_AXIS));
			
			JLabel vehicle_label = new JLabel("Road: ");
			spinners_panel.add(vehicle_label);
			road_box = new JComboBox<String>();
			for(int i = 0; i < road_spin_list.size(); i++)
				road_box.addItem(road_spin_list.get(i));
			road_box.setMaximumSize(new Dimension(200, 20));
			spinners_panel.add(road_box);
			spinners_panel.add(Box.createRigidArea(new Dimension(10, 0)));
			
			JLabel class_label = new JLabel("Weather: ");
			spinners_panel.add(class_label);
			weather_box = new JComboBox<String>();
			
			for(Weather w: Weather.values())
				weather_box.addItem(w.toString());
			
			weather_box.setMaximumSize(new Dimension(200, 20));
			spinners_panel.add(weather_box);
			spinners_panel.add(Box.createRigidArea(new Dimension(10, 0)));
			
			
			JLabel ticks_label = new JLabel("Ticks: ");
			spinners_panel.add(ticks_label);
			numSpin = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
			JSpinner ticks = new JSpinner(numSpin);
			ticks.setMaximumSize(new Dimension(100, 20));
			ticks.setValue(10);
			ticks_label.add(ticks);
			spinners_panel.add(ticks);		
			
			weather.add(spinners_panel);
			
			// Para los botones
			JPanel btn_panel = new JPanel();
			JButton ok_btn = new JButton();
			ok_btn.addActionListener(e -> {
				action_ok();
			});
			ok_btn.setText("Comfirm");
			btn_panel.add(ok_btn);
			JButton cancel_btn = new JButton();
			cancel_btn.setText("Cancel");
			cancel_btn.addActionListener(e -> {
				weather.setVisible(false);
			});
			btn_panel.add(cancel_btn);
			weather.add(btn_panel);
			
			weather.setVisible(true);
		}

		private void action_ok() {
			if(!road_spin_list.isEmpty()) {
				Pair<String, Weather> info  = new Pair<>((String) road_box.getSelectedItem(), Weather.valueOf((String)weather_box.getSelectedItem()));
				List<Pair<String, Weather>> events = new ArrayList<>();
				events.add(info);
				SetWeatherEvent e = new SetWeatherEvent(time + numSpin.getNumber().intValue(), events);
				_ctrl.addEvent(e);
			}
			weather.setVisible(false);
		}

		@Override
		public void onAdvance(RoadMap map, Collection<Event> events, int time) {
			road_spin_list.clear();
			for(int i = 0; i < map.getRoads().size(); i++) {
				road_spin_list.add(map.getRoads().get(i).getId());
			}	
		}

		@Override
		public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onReset(RoadMap map, Collection<Event> events, int time) {
			road_spin_list.clear();
		}

		@Override
		public void onRegister(RoadMap map, Collection<Event> events, int time) {
			for(int i = 0; i < map.getRoads().size(); i++) {
				road_spin_list.add(map.getRoads().get(i).getId());
			}
			this.time = time;
		}


}
