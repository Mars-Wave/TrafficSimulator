package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    private Controller _ctrl;
	private JPanel mapsPanel;
    private JPanel tablesPanel;

    public MainWindow(Controller ctrl) {
        super("Traffic Simulator");
        _ctrl = ctrl;
        initGUI();
    }

    private void initGUI() {
//Main panel setup
        initMainPanel();
//tables
        initTables();
//maps
		initMaps();
//settings
        this.setPreferredSize(new Dimension(1000, 700));
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

	private void initMaps() {
    	initSpatialMap();
    	initLinearMap();
	}

	private void initLinearMap() {
		JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		mapsPanel.add(mapByRoadView);
	}

	private void initSpatialMap() {
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapsPanel.add(mapView);
	}

	private void initTables() {
        initEventsTable();
        initVehiclesTable();
        initRoadsTable();
        initJunctionsTable();
    }

    private void initJunctionsTable() {
        JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
        junctionsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(junctionsView);
    }

    private void initRoadsTable() {
        JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
        roadsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(roadsView);
    }

    private void initVehiclesTable() {
        JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
        vehiclesView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(vehiclesView);
    }

    private void initEventsTable() {
        JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
        eventsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(eventsView);
    }

    private void initMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setResizable(true);
        this.setContentPane(mainPanel);
        mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
        mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(viewsPanel, BorderLayout.CENTER);
        tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        viewsPanel.add(tablesPanel);
        mapsPanel = new JPanel();
        mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
        viewsPanel.add(mapsPanel);
    }

    private JPanel createViewPanel(JComponent c, String title) {
        JPanel p = new JPanel(new BorderLayout());
        Border b = BorderFactory.createLineBorder(Color.black, 2);
//TODO add a framed border to p with title
        p.setBorder(BorderFactory.createTitledBorder(b, title, TitledBorder.LEFT, TitledBorder.TOP));
        p.add(new JScrollPane(c));
        return p;
    }


}
