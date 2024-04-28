package GUI.Sections;

import API.Race.LapCalculator;
import APIObjects.Driver;
import API.Race.DriverPositions;
import APIObjects.RegexAssist;
import GUI.Components.RoundedButton;
import GUI.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RaceDriverViewSection {

    private final JPanel driverView;
    private final ArrayList<JButton> driverButtons;
    private DriverPositions driverPositions;

    private MainScreen screen;

    public RaceDriverViewSection(MainScreen screen) {

        driverView = new JPanel();
        driverButtons = new ArrayList<>();

        this.screen = screen;

        driverPositions = new DriverPositions(screen.getSession());

        for (Driver cur : driverPositions.getDriversInOrder(0)) {
            JButton newBut = new RoundedButton("", 175);
            newBut.setFont(new Font("Arial", Font.BOLD, 16));
            newBut.addActionListener(screen.getDriverInformation());
            driverButtons.add(newBut);
            driverView.add(newBut);
        }
    }

    private void makeDriverNodes(JPanel panel, int seconds) {
        ArrayList<Driver> driverOrder = driverPositions.getDriversInOrder(seconds);
        int count = 0;
        for (JButton cur : driverButtons) {
            if (count >= driverOrder.size()) continue;
            RoundedButton round = (RoundedButton) cur;
            round.setBorderRadius(round.getBorderRadius());
            Driver curDriver = driverOrder.get(count);
            cur.setBackground(curDriver.getTeam().getColour());
            cur.setForeground(new Color(0,0,0));
            //Temporary
            if (curDriver.getTeam().getName().contains("Red Bull") || curDriver.getTeam().getName().contains("Williams")) cur.setForeground(new Color(255, 255, 255));
            cur.setText("<html>P" + (count + 1) + ": " + curDriver.getStarter() + " " + curDriver.getNumber() + " <br>" +  screen.getTyreHandler().getTyreAt(curDriver.getNumber(), screen.getCurrentLap()) + "</html>");
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

    public void clearCache() {
        driverPositions.clearCachedDrivers();
    }
}
