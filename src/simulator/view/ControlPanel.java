package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

    private Controller controller;
    private JPanel leftPanel;
    private JPanel rightPanel; 
    private JFileChooser fc;
    private RoadMap rMap;
    private JSpinner tickSpinner;
    private boolean _stopped;
    private ArrayList<JButton> bs;
    private JButton stopButton;
	ChangeCO2ClassDialog SCC;

    public ControlPanel(Controller cont) {
        controller = cont;
        controller.addObserver(this);
        this.setLayout(new BorderLayout());
        bs = new ArrayList<>();
        initContP();
    }

    private void initContP() {
    	init();
        initFC();
        initSCC();
        initSWC();
        initRunButton();
        initStopButton();
        ticksSpinner();
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
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(this, "File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    System.out.println("Load cancelled by user.");
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
            this.SCC.setVisible(true);
        });
        bs.add(sccButton);
        leftPanel.add(sccButton);
    }

    private void initSWC() {
        ChangeWeatherDialog SWC = new ChangeWeatherDialog(controller);
        JButton swcButton = new JButton();
        swcButton.setIcon(new ImageIcon("resources/icons/weather.png"));
        swcButton.addActionListener((e) -> {
            SWC.setVisible(true);
        });
        bs.add(swcButton);
        leftPanel.add(swcButton);
    }

    private void initRunButton() {
        JButton runButton = new JButton();
        runButton.setIcon(new ImageIcon("resources/icons/run.png"));
        runButton.addActionListener((e) -> {
            _stopped = false;
            run_sim((Integer) tickSpinner.getValue());
            enableToolBar(false);
        });
        bs.add(runButton);
        leftPanel.add(runButton);
    }

    private void initStopButton() {
        stopButton = new JButton();
        stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
        stopButton.addActionListener((e) -> {
            enableToolBar(true);
            stop();
        });

        leftPanel.add(stopButton);
    }

    private void ticksSpinner() {
        JLabel ticksLabel = new JLabel("Ticks:");
        this.add(ticksLabel);
        tickSpinner = new JSpinner();
        tickSpinner.setPreferredSize(new Dimension(50, 25));
        leftPanel.add(tickSpinner);
    }

    private void run_sim(int n) {
        if (n > 0 && !_stopped) {
            try {
                controller.run(1);
            } catch (Exception e) {
                // TODO show error message
                _stopped = true;
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
        // TODO Auto-generated method stub

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        // TODO Auto-generated method stub
		if (e.toString().startsWith("v")){
			SCC.addVehicle(e.toString());
		}
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onError(String err) {
        // TODO Auto-generated method stub
    }

}