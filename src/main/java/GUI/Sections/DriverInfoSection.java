package GUI.Sections;

import API.DriverAPI;
import API.Race.DriverPositions;
import APIObjects.Driver;
import APIObjects.RegexAssist;
import GUI.MainScreen;

import javax.swing.*;
import java.awt.*;

public class DriverInfoSection {

    private JPanel panel;
    private Driver driver;

    public DriverInfoSection(int driverNumber) {
        panel = new JPanel();

        driver = DriverAPI.getInstance().getDriver(driverNumber);


        panel.setLayout(new BorderLayout());


        panel.setBackground(new Color(21, 21, 31));

        JPanel center = new JPanel(new GridBagLayout());

        panel.add(center, BorderLayout.CENTER);
        center.setOpaque(true);
        center.setBackground(new Color(21, 21, 31));

        quickAddLabel("Name: " + driver.getName(), center, 1, 0);
        quickAddLabel("Driver Number: " + driverNumber, center, 1, 1);;
        quickAddLabel("Team: " + driver.getTeam().getName(), center, 1, 2);
        quickAddLabel("Race Story", center, 1, 3);
        quickAddLabel("Started: P" + MainScreen.getInstance().driverViewTab.getDriverPositions().getDriverPosAt(driverNumber, MainScreen.getInstance().getSession().getStartTime()) , center, 0, 4);
        quickAddLabel("Currently: P" + MainScreen.getInstance().driverViewTab.getDriverPositions().getDriverPosAt(driverNumber, MainScreen.getInstance().getSeconds()) , center, 2, 4);
        JButton homeButton = new JButton("Back");
        homeButton.setBackground(Color.RED);
        homeButton.setForeground(Color.white);
        homeButton.setPreferredSize(new Dimension(200, 40));
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        homeButton.addActionListener(MainScreen.getInstance().reopenMainScreen());
        center.add(homeButton, RegexAssist.generateConstraints(1, 6));
    }

    private JLabel quickAddLabel(String text, JPanel panel, int x, int y) {

        JLabel newLab = new JLabel(text);
        newLab.setFont(new Font("Arial", Font.BOLD, 28));
        newLab.setForeground(Color.WHITE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = new Insets(30, 20, 30, 20);
        panel.add(newLab, constraints);
        return newLab;

    }

    public JPanel getPanel() {
        return panel;
    }

}
