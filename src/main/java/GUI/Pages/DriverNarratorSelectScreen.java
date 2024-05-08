package GUI.Pages;

import API.AccountHandler;
import API.DriverAPI;
import APIObjects.Driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static APIObjects.RegexAssist.generateConstraints;

public class DriverNarratorSelectScreen {

    private JFrame window;
    private JPanel panel;

    public DriverNarratorSelectScreen() {
        window = new JFrame();
        panel = new JPanel();
        window.setVisible(true);

        window.setTitle("Choose driver narration options");
        window.setSize(new Dimension(1200, 400));

        panel.setBackground(new Color(21, 21, 31));

        window.getContentPane().add(panel);

        initialiseWindow();
    }

    private void initialiseWindow() {
        panel.setLayout(new FlowLayout());

        for (Driver cur : DriverAPI.getInstance().getDrivers()) {
            quickAddCheckBox(panel, cur);
        }
    }

    private JCheckBox quickAddCheckBox(JPanel panel, Driver driver) {
        JCheckBox checkBox = new JCheckBox(driver.getName(), AccountHandler.getInstance().getDriverNarrate(driver.getNumber()));
        checkBox.setOpaque(false);
        checkBox.setForeground(Color.RED);
        checkBox.setFont(new Font("Arial", Font.BOLD, 25));
        checkBox.addActionListener(changePreference(driver.getNumber(), checkBox));
        panel.add(checkBox);

        return checkBox;
    }

    public ActionListener changePreference(int driverNumber, JCheckBox checkBox) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountHandler.getInstance().setDriverNarrate(driverNumber, checkBox.isSelected());
            }
        };
    }

}
