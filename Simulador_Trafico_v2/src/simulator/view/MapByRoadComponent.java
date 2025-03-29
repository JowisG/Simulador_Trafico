package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JPanel implements TrafficSimObserver {
	
	/* Atributos */
	
	private static final int _JUNC_RADIUS = 15;
	
	// Atributos de colores
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _ROAD_COLOR = Color.BLACK;
	private static final Color _SRC_COLOR = Color.BLUE;
	private static final Color _DEST_COLOR = Color.RED;
	
	// Atributos de datos
	private RoadMap _map;
	private Image _car;
	
	public MapByRoadComponent(Controller _ctrl) {
		setPreferredSize(new Dimension(300,200));
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		_car = loadImage("car.png");
	}
	
	/* Metodos para pintar */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// Colores para el background
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		// Mapa vacio o sin Junctions
		if(_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.RED);
			g.drawString("No roads map yet!", (getWidth() / 2) - 50, getHeight() / 2);
		}else {
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		int i = 0;
		for(Road r: _map.getRoads()) {
			
			int x1 = 50;
			int y = (i+1)*50;
			int x2 = getWidth()-100;
			
			// Dibujamos el nombre de la carretera
			g.setColor(_ROAD_COLOR);
			g.drawString(r.getId(), x1-30, y+5);
			
			// Dibujamos linea de la carretera
			g.drawLine(x1, y, x2, y);

			// Dibujamos los Junctions
			g.setColor(_SRC_COLOR);
			g.fillOval(x1-(_JUNC_RADIUS/2), y-(_JUNC_RADIUS/2), _JUNC_RADIUS, _JUNC_RADIUS);
			Color dest_color = _DEST_COLOR;
			int green = r.getDest().getGreenLightIndex();
			if(green != -1 && r.equals(r.getDest().getInRoads().get(green)))
				dest_color = Color.GREEN;
			g.setColor(dest_color);
			g.fillOval(x2-(_JUNC_RADIUS/2), y-(_JUNC_RADIUS/2), _JUNC_RADIUS, _JUNC_RADIUS);
			
			// Dibujamos el coche
			for(Vehicle v: r.getVehicles()) {
				int vx = x1 + (int) ((x2-x1) * ((double) v.getLocation() / (double) r.getLength()));
				int vy = y;
				
				if(v.getStatus() == VehicleStatus.TRAVELING) {
					g.drawImage(_car, vx, vy-10, 16, 16, this);
					// TODO Label color like the car in the other map
					g.setColor(Color.GREEN);
					g.drawString(v.getId(), vx, vy - 16);
				}
			}
			
			drawWeather(g, r, y);
			drawCont(g, r, y);
			i++;
		}
	}

	private void drawWeather(Graphics g, Road r, int y) {
		String weather = "";
		switch (r.getWeather()) {
		case SUNNY:
			weather = "sun";
			break;
		case RAINY:
			weather = "rain";
			break;
		case STORM:
			weather = "storm";
			break;
		case WINDY:
			weather = "wind";
			break;
		case CLOUDY:
			weather = "cloud";
		}
		Image weather_img = loadImage(weather +".png");
		g.drawImage(weather_img, getWidth()-90, y-16, 32, 32, this);
	}
	
	private void drawCont(Graphics g, Road r, int y) {
		int c = (int) Math.floor(Math.min((double) (r.getTotalCO2()/(1.0 + (double) r.getContLimit())), 1.0)/ 0.19);
		String name = "cont_" + c + ".png";
		Image cont_img = loadImage(name);
		g.drawImage(cont_img, getWidth()-42, y-16, 32, 32, this);
	}

	/* Metodos privados */
	
	private void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}

	private Image loadImage(String name) {
		Image img = null;
		
		try {
			return ImageIO.read(new File("resources/icons/" + name));
		} catch (IOException e) {}
		
		return img;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);		
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

}
