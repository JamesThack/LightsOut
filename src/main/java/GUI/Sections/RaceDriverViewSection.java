package GUI.Sections;

import APIObjects.Driver;
import API.Race.DriverPositions;
import GUI.Components.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RaceDriverViewSection {

    private JPanel driverView;
    private ArrayList<JButton> driverButtons;

    public RaceDriverViewSection() {

        driverView = new JPanel();
        driverButtons = new ArrayList<>();

        for (Driver cur : DriverPositions.getInstance().getDriversInOrder(0)) {
            JButton newBut = new RoundedButton("", 200);
            newBut.setFont(new Font("Arial", Font.BOLD, 15));
            driverButtons.add(newBut);
            driverView.add(newBut);
        }
    }

    private void makeDriverNodes(JPanel panel, int seconds) {
        ArrayList<Driver> driverOrder = DriverPositions.getInstance().getDriversInOrder(seconds);
        int count = 0;
        for (JButton cur : driverButtons) {
            Driver curDriver = driverOrder.get(count);
            cur.setBackground(curDriver.getTeam().getColour());
            cur.setForeground(new Color(0,0,0));
            if (curDriver.getTeam().getName().contains("Red Bull") || curDriver.getTeam().getName().contains("Williams")) cur.setForeground(new Color(255, 255, 255));
            cur.setText("P" + (count + 1) + ": " + curDriver.getStarter() + " " + String.valueOf(curDriver.getNumber()));
            count +=1;
        }
    }

    public JPanel getDriverView() {
        return driverView;
    }

    public void updateDriverView(int seconds) {
        driverView.repaint();
        makeDriverNodes(driverView, seconds);
        driverView.validate();
    }


}
