package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel implements TrafficSimObserver {

    private Controller controller;
    private RoadMap _map;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JFileChooser fc;
    private JSpinner tickSpinner;
    private boolean _stopped;
    private ArrayList<JButton> bs; //buttons that will be nullified when stop occurs. This can be seen in EnableToolbar(bool)
    private ChangeWeatherDialog SWC;
    private ChangeCO2ClassDialog SCC;
    private int _simTime;

    public ControlPanel(Controller cont, int ticks) {
        controller = cont;
        this.setLayout(new BorderLayout());
        bs = new ArrayList<>();
        initContP(ticks);
        controller.addObserver(this);
    }

    private void initContP(int ticks) {
        init();
        initFC();
        initSCC();
        initSWC();
        initRunButton();
        initStopButton();
        ticksSpinner(ticks);
        initExitButton();
    }

    private void init() {
        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void initExitButton() {
        JButton exit = new JButton(new ImageIcon("resources/icons/exit.png"));
        exit.addActionListener((e) -> {
            int n = JOptionPane.showOptionDialog(this, "Are sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (n == 0) {
                System.exit(0);
            }
        });
        rightPanel.add(exit);
    }

    private void initFC() {
        fc = new JFileChooser();
        JButton loadButton = new JButton();
        loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
        loadButton.addActionListener((e) -> {
            if (e.getSource() == loadButton) {
                int returnVal = fc.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(file);
                        controller.reset();
                        controller.loadEvents(in);
                    } catch (FileNotFoundException e1) {    // Exception treatment
                        JOptionPane.showMessageDialog(this, "File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "You cancelled the load", "Load cancelled", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        bs.add(loadButton);
        leftPanel.add(loadButton);

    }

    private void initSCC() {
        this.SCC = new ChangeCO2ClassDialog(controller);
        JButton sccButton = new JButton();
        sccButton.setIcon(new ImageIcon("resources/icons/co2class.png"));
        sccButton.addActionListener((e) -> {
        	SCC.setVehList(_map.getVehicles());
            SCC.updateTime(_simTime);
            this.SCC.setVisible(true);
        });
        bs.add(sccButton);
        leftPanel.add(sccButton);
    }

    private void initSWC() {
        this.SWC = new ChangeWeatherDialog(controller);
        JButton swcButton = new JButton();
        swcButton.setIcon(new ImageIcon("resources/icons/weather.png"));
        swcButton.addActionListener((e) -> {
        	SWC.setrList(_map.getRoads());
            SWC.updateTime(_simTime);
            this.SWC.setVisible(true);
        });
        bs.add(swcButton);
        leftPanel.add(swcButton);
    }

    private void initRunButton() {
        JButton runButton = new JButton();
        runButton.setIcon(new ImageIcon("resources/icons/run.png"));
        runButton.addActionListener((e) -> {
            _stopped = false;
            enableToolBar(false);
            run_sim((Integer) tickSpinner.getValue());
        });
        bs.add(runButton);
        leftPanel.add(runButton);
    }

    private void initStopButton() {
        JButton stopButton = new JButton();
        stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
        stopButton.addActionListener((e) -> {
            enableToolBar(true);
            stop();
        });
        leftPanel.add(stopButton);
    }

    private void ticksSpinner(int ticks) {
        JLabel ticksLabel = new JLabel("Ticks:");
        this.add(ticksLabel);
        tickSpinner = new JSpinner();
        tickSpinner.setValue(ticks);
        tickSpinner.setPreferredSize(new Dimension(50, 25));
        leftPanel.add(tickSpinner);
    }

    private void run_sim(int n) {
        if (n > 0 && !_stopped) {
            try {
                controller.run(1);
            } catch (Exception e) {
                _stopped = true;
                JOptionPane.showMessageDialog(this, "Something went wrong when trying to run the Simulator", "Exception", JOptionPane.ERROR_MESSAGE);
                enableToolBar(true);
                return;
            }
            SwingUtilities.invokeLater(() -> run_sim(n - 1));
        } else {
            enableToolBar(true);
            _stopped = true;
        }
    }


    private void enableToolBar(boolean b) {
        for (JButton button : bs) {
            button.setEnabled(b);
        }
    }

    private void stop() {
        _stopped = true;
    }


    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
    	update(map, time);
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
    	update(map, time);
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
    	update(map, time);
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
    	update(map, time);
       
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
    	update(map, time);
    }

    @Override
    public void onError(String err) {
    }
    
    public void update(RoadMap map, int time) {
    	_map = map;
    	_simTime = time;
    }

}