package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
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
import javax.swing.border.EmptyBorder;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.TrafficSimObserver;

public class ChangeCO2ClassDialog extends JDialog implements TrafficSimObserver {
	
	// Atributos
	private Controller _ctrl;
	private int time = 0;
	
	// elementos
	//private JDialog dco2;
	private JComboBox<String> vehicle_box;
	private JComboBox<Integer> class_box;
	private SpinnerNumberModel numSpin;
	
	// Info map
	List<String> vehicle_spin_list = new ArrayList<>();

	
	public ChangeCO2ClassDialog(Frame f, Controller _ctrl) {
		// f es null porque no se
		super(f,true);
		this._ctrl = _ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		setTitle("Change CO2 Class");
		setSize(new Dimension(500, 200));
		setLayout(new GridLayout(3, 1));
		
		JLabel text = new JLabel("<html><p>Schedule an event to change the CO2 class of a vehicle after a given number of ticks from now.</p></html>");
		add(text);
		
		// Para los spinners
		JPanel spinners_panel = new JPanel();
		spinners_panel.setLayout(new BoxLayout(spinners_panel, BoxLayout.X_AXIS));
		spinners_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel vehicle_label = new JLabel("Vehicle: ");
		spinners_panel.add(vehicle_label);
		vehicle_box = new JComboBox<String>();
		vehicle_box.setMaximumSize(new Dimension(200, 20));
		spinners_panel.add(vehicle_box);
		spinners_panel.add(Box.createRigidArea(new Dimension(10, 0)));
		
		JLabel class_label = new JLabel("CO2 Class: ");
		spinners_panel.add(class_label);
		class_box = new JComboBox<Integer>();
		for(int i = 0; i < 11; i++)
			class_box.addItem(i);
		class_box.setMaximumSize(new Dimension(200, 20));
		spinners_panel.add(class_box);
		spinners_panel.add(Box.createRigidArea(new Dimension(10, 0)));
		
		
		JLabel ticks_label = new JLabel("Ticks: ");
		spinners_panel.add(ticks_label);
		numSpin = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		JSpinner ticks = new JSpinner(numSpin);
		ticks.setMaximumSize(new Dimension(100, 20));
		ticks.setValue(10);
		ticks_label.add(ticks);
		spinners_panel.add(ticks);		
		
		add(spinners_panel);
		
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
			setVisible(false);
		});
		btn_panel.add(cancel_btn);
		add(btn_panel);
		
		setVisible(false);
	}
	
	public void open() {
		// No funciona -> setLocationRelativeTo(this.getParent());
		for(int i = 0; i < vehicle_spin_list.size(); i++)
			vehicle_box.addItem(vehicle_spin_list.get(i));
		setVisible(true);
	}

	private void action_ok() {
		if(!vehicle_spin_list.isEmpty()) {
			Pair<String, Integer> info  = new Pair<>((String) vehicle_box.getSelectedItem(), (Integer) class_box.getSelectedItem());
			List<Pair<String, Integer>> events = new ArrayList<>();
			events.add(info);
			SetContClassEvent e = new SetContClassEvent(time + numSpin.getNumber().intValue(), events);
			_ctrl.addEvent(e);
		}
		setVisible(false);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		vehicle_spin_list.clear();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		for(int i = 0; i < map.getVehicles().size(); i++) {
			vehicle_spin_list.add(map.getVehicles().get(i).getId());
		}
		this.time = time;
	}

}
