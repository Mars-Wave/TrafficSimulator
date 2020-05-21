package simulator.view;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Vehicle;
import simulator.model.Weather;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeWeatherDialog extends JDialog {

    private Controller controller;
    private JComboBox<String> rList;
    private JComboBox<String> weatherList;
    private int _simTime;
    private JSpinner spin;


    public ChangeWeatherDialog(Controller controller) {
        this.controller = controller;
        initGUI();
        this.pack();
    }

    public void initGUI() {
        JPanel contClassPanel = new JPanel(new BorderLayout());
        JLabel contClassLabel = new JLabel("<html><p>Schedule an event to change the weather of a road after a given number of simulation ticks from now.</p></html>");
        contClassPanel.add(contClassLabel, BorderLayout.PAGE_START);
        initCenterPanel(contClassPanel);
        initBottomPanel();
        //settings
        this.setTitle("Change Road Weather");
        this.setSize(new Dimension(350, 140));
        this.setVisible(false);
        this.add(contClassPanel);
        this.setResizable(false);
    }

    private void initBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((e) -> {
            this.setVisible(false);
        });
        bottomPanel.add(cancelButton);
        JButton okButton = new JButton("OK");
        okButton.addActionListener((e) -> {
                    List<Pair<String, Weather>> ws = new ArrayList<>();
                    Pair<String, Weather> p = new Pair(rList.getSelectedItem(), weatherList.getSelectedItem());
                    if (p.getFirst() != null) {
                        ws.add(p);
                        controller.addEvent(new SetWeatherEvent(((Integer) spin.getValue() + _simTime), ws));
                        this.setVisible(false);
                    } else {
                        this.setVisible(false);
                    }
                }
        );
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);
        this.add(bottomPanel, BorderLayout.PAGE_END);
    }

    private void initCenterPanel(JPanel contClassPanel) {
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        initRList(centerPanel);
        initWeatherList(centerPanel);
        initTicksSpinner(centerPanel);
        contClassPanel.add(centerPanel);
    }

    private void initTicksSpinner(JPanel centerPanel) {
        centerPanel.add(new JLabel("Ticks:"));
        SpinnerModel sm = new SpinnerNumberModel(1, 1, 10000, 1);
        spin = new JSpinner(sm);
        spin.setPreferredSize(new Dimension(50, 25));
        centerPanel.add(spin);
    }

    private void initWeatherList(JPanel centerPanel) {
        centerPanel.add(new JLabel("Weather:"));
        weatherList = new JComboBox(Weather.weathers());
        weatherList.setEditable(false);
        centerPanel.add(weatherList);
    }

    private void initRList(JPanel centerPanel) {
        JLabel roadLabel = new JLabel("Road:");
        centerPanel.add(roadLabel);
        rList = new JComboBox<>();
        rList.setEditable(false);
        centerPanel.add(rList);
    }

    public void setrList(List<Road> rList) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) this.rList.getModel();
        for (Road r : rList) {
            model.addElement(r.getId());
        }
    }

    public void updateTime(int time) {
        _simTime = time;
    }
}
