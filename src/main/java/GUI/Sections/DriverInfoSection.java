package GUI.Sections;

import API.DriverAPI;
import APIObjects.Driver;
import GUI.MainScreen;

import javax.swing.*;

public class DriverInfoSection {

    private JPanel panel;
    private Driver driver;

    public DriverInfoSection(int driverNumber) {
        panel = new JPanel();

        driver = DriverAPI.getInstance().getDriver(driverNumber);
        JLabel label = new JLabel(driver.getName());
        panel.add(label);

        JButton homeButton = new JButton("Back");
        homeButton.addActionListener(MainScreen.getInstance().reopenMainScreen());
        panel.add(homeButton);
    }

    public JPanel getPanel() {
        return panel;
    }

}
