package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		setResizable(false);
		setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		
		JLabel text = new JLabel("<html><p>Schedule an event to change the CO2 class of a vehicle after a given number of ticks from now.</p></html>");
		add(text, constraints);
		
		// Para los spinners
		JPanel spinners_panel = new JPanel(new GridBagLayout());
		spinners_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel vehicle_label = new JLabel("Vehicle: ");
		spinners_panel.add(vehicle_label, constraints);
		vehicle_box = new JComboBox<String>();
		constraints.gridx = 1;
		constraints.weightx = 2.0;
		spinners_panel.add(vehicle_box, constraints);
		
		JLabel class_label = new JLabel("CO2 Class: ");
		constraints.gridx = 3;
		constraints.weightx = 1.0;
		spinners_panel.add(class_label, constraints);
		class_box = new JComboBox<Integer>();
		for(int i = 0; i < 11; i++)
			class_box.addItem(i);
		constraints.gridx = 4;
		constraints.weightx = 2.0;
		spinners_panel.add(class_box, constraints);
		
		
		JLabel ticks_label = new JLabel("Ticks: ");
		constraints.gridx = 6;
		constraints.weightx = 1.0;
		spinners_panel.add(ticks_label, constraints);
		numSpin = new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1);
		JSpinner ticks = new JSpinner(numSpin);
		ticks.setPreferredSize(new Dimension(40,20));
		constraints.gridx = 7;
		constraints.weightx = 1.0;
		spinners_panel.add(ticks, constraints);		
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		add(spinners_panel, constraints);
		
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
		constraints.gridy = 2;
		add(btn_panel, constraints);
		
		setVisible(false);
	}
	
	public void open() {
		// No funciona -> setLocationRelativeTo(this.getParent());
		vehicle_box.removeAllItems();
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
		this.time = time;
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
