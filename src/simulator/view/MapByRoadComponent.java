package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
    private static final int _JRADIUS = 10;

    private static final Color _BG_COLOR = Color.WHITE;
    private static final Color _JUNCTION_COLOR = Color.BLUE;
    private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
    private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
    private static final Color _RED_LIGHT_COLOR = Color.RED;
    private Image _car;
    private Image _storm;
    private Image _wind;
    private Image _rain;
    private Image _cloud;
    private Image _sun;
    private ArrayList<Image> _conts;

    private RoadMap _map;

    MapByRoadComponent(Controller ctrl) {
        initGUI();
        setPreferredSize(new Dimension(300, 200));
        ctrl.addObserver(this);
    }


    private void initGUI() {
        _car = loadImage("car.png");
        _storm = loadImage("storm.png");
        _wind = loadImage("wind.png");
        _rain = loadImage("rain.png");
        _cloud = loadImage("cloud.png");
        _sun = loadImage("sun.png");
        _conts = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            String aux = "cont_" + i + ".png";
            _conts.add(loadImage(aux));
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // clear with a background color
        g.setColor(_BG_COLOR);
        g.clearRect(0, 0, getWidth(), getHeight());

        if (_map == null || _map.getJunctions().size() == 0) {
            g.setColor(Color.red);
            g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
        } else {
            drawMap(g);
        }
    }

    private void drawMap(Graphics g) {
        drawRoadsVehiclesAndJunctions(g);
    }

    private void drawRoadsVehiclesAndJunctions(Graphics g) {
        int i = 0;
        for (Road r : _map.getRoads()) {
            //road line
            g.setColor(Color.darkGray);
            g.drawLine(50, (i + 1) * 50, this.getWidth() - 100, (i + 1) * 50);
            //vehicles in the road line
            for (Vehicle v : r.getVehicles()) {
                if (v.getStatus() != VehicleStatus.ARRIVED) {
                    // Choose a color for the vehicle's label and background, depending on its
                    // contamination class
                    int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
                    g.setColor(new Color(0, vLabelColor, 0));

                    // draw an image of a car (with circle as background) and its identifier
                    //int carX = (this.getWidth() - 100) * (v.getLocation() / r.getLength()); //Check X calculation
                    int carX = 50 + (int) ((getWidth() - 100 - 50) * ((double) v.getLocation() / (double) v.getRoad().getLength())); //Check X calculation
                    g.fillOval(carX - 1, (i + 1) * 50 - 6, 14, 14);
                    g.drawImage(_car, carX, (i + 1) * 50 - 6, 12, 12, this);
                    g.drawString(v.getId(), carX, (i + 1) * 50 - 6);
                }
            }
            //junction of origin
            g.setColor(_JUNCTION_COLOR);
            g.fillOval(50 - _JRADIUS / 2, (i + 1) * 50 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
            g.drawString(r.getSrc().getId(), 50, (i + 1) * 50 - 6);
            //destination junction
            Color jLightColor = _RED_LIGHT_COLOR;
            int idx = r.getDest().getGreenLightIndex();
            if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
                jLightColor = _GREEN_LIGHT_COLOR;
            }
            g.setColor(jLightColor);
            g.fillOval(this.getWidth() - 100 - _JRADIUS / 2, (i + 1) * 50 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
            g.drawString(r.getDest().getId(), this.getWidth() - 100, (i + 1) * 50 - 6);
            Image imgaux = null;
            switch (r.getWeather()) {
                case SUNNY:
                    imgaux = _sun;
                    break;
                case CLOUDY:
                    imgaux = _cloud;
                    break;
                case RAINY:
                    imgaux = _rain;
                    break;
                case WINDY:
                    imgaux = _wind;
                    break;
                case STORM:
                    imgaux = _storm;
                    break;
            }
            g.drawImage(imgaux, this.getWidth() -100 + 16, (i + 1) * 50 - 16, 32, 32, this);
            int C = (int) Math.floor(Math.min((double) r.getTotalCont() / (1.0 + r.getContLimit()), 1.0) / 0.19);
            g.drawImage(_conts.get(C), this.getWidth() -100 + (16+32), (i + 1) * 50 - 16, 32, 32, this);
            i++;
        }
    }

    // loads an image from a file
    private Image loadImage(String img) {
        Image i = null;
        try {
            return ImageIO.read(new File("resources/icons/" + img));
        } catch (IOException e) {
        }
        return i;
    }

    public void update(RoadMap map) {
        _map = map;
        repaint();
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        update(map);
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onError(String err) {
    }
}