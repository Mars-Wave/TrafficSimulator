package simulator.view;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.SetContClassEvent;
import simulator.model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeCO2ClassDialog extends JDialog {

    private Controller controller;
    private JSpinner spin;
    private JComboBox<Integer> co2list;
    private JComboBox<String> vehList;
    private int _simTime;

    public ChangeCO2ClassDialog(Controller controller) {
        this.controller = controller;
        this.vehList = new JComboBox<>();
        initGUI();
        this.pack();
    }

    public void initGUI() {
        JPanel contClassPanel = new JPanel(new BorderLayout());
        JLabel contClassLabel = new JLabel("<html><p>Schedule an event to change the weather of a road after a given number of simulation ticks from now.</p></html>");
        // Page start
        contClassPanel.add(contClassLabel, BorderLayout.PAGE_START);
        //vehicle combobox
        initCenterPanel(contClassPanel);
        initBottomPanel();
        this.setTitle("Change CO2 Class");
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
                    List<Pair<String, Integer>> cs = new ArrayList<>();
                    Pair<String, Integer> p = new Pair(vehList.getSelectedItem(), co2list.getSelectedItem());
                    if (p.getFirst() != null) {
                        cs.add(p);
                        controller.addEvent(new SetContClassEvent(((Integer) spin.getValue() + _simTime), cs));
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
        initVehicleBox(centerPanel);
        initCO2ClassBox(centerPanel);
        initSpinner(centerPanel);
        contClassPanel.add(centerPanel);
    }

    private void initSpinner(JPanel centerPanel) {
        centerPanel.add(new JLabel("Ticks:"));
        spin = new JSpinner();
        spin.setPreferredSize(new Dimension(50, 25));
        centerPanel.add(spin);
    }

    private void initCO2ClassBox(JPanel centerPanel) {
        centerPanel.add(new JLabel("CO2 Class:"));
        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        co2list = new JComboBox<>(numbers);
        centerPanel.add(co2list);
    }

    private void initVehicleBox(JPanel centerPanel) {
        centerPanel.add(new JLabel("Vehicle:"));
        centerPanel.add(vehList);
        vehList.setEditable(true);
    }

    public void setVehList(List<Vehicle> vList) {
        vehList.removeAllItems();
        for (Vehicle v : vList) {
            vehList.addItem(v.getId());
        }
    }

    public void updateTime(int time) {
        _simTime = time;
    }
}
