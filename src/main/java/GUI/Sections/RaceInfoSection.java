package GUI.Sections;

import API.AccountHandler;
import GUI.MainScreen;

import javax.swing.*;
import java.awt.*;

public class RaceInfoSection {

    private final JPanel panel;
    private MainScreen screen;

    public RaceInfoSection(MainScreen screen) {
        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, 1));

        this.screen = screen;

        initialise();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initialise() {
        panel.setBackground(new Color(0, 0, 0));

        addComponents();
    }

    public void clearScreen() {
        panel.removeAll();

        JLabel lblTime = new JLabel("  Race Information:  ");
        lblTime.setFont(new Font("Arial", Font.BOLD, 25));
        lblTime.setForeground(new Color(255, 255, 255));
        panel.add(lblTime);

    }

    public void addComponents() {
        panel.removeAll();

        JLabel lblTime = new JLabel("  Race Information:  ");
        lblTime.setFont(new Font("Arial", Font.BOLD, 25));
        panel.add(lblTime);
        lblTime.setForeground(new Color(255, 255, 255));
        if (screen.getSession() != null) {
            JLabel circuitName = new JLabel("    Location - " + screen.getSession().getName() + "    ");
            circuitName.setFont(new Font("Arial", Font.BOLD, 20));
            JLabel raceTime = new JLabel("    Date - " + screen.getSession().getDate());
            raceTime.setFont(new Font("Arial", Font.BOLD, 20));
            JLabel conditions = new JLabel("    Wind - Calm");
            conditions.setFont(new Font("Arial", Font.BOLD, 20));
            JLabel trackTemp = new JLabel("    Track Temp - " + (int) ((Math.random() * (12 - 6)) + 6) + ".C    ");
            trackTemp.setFont(new Font("Arial", Font.BOLD, 20));

            circuitName.setForeground(new Color(255, 255, 255));
            raceTime.setForeground(new Color(255, 255, 255));
            conditions.setForeground(new Color(255, 255, 255));
            trackTemp.setForeground(new Color(255, 255, 255));

            panel.add(circuitName);
            panel.add(raceTime);
            panel.add(conditions);
            panel.add(trackTemp);
        }

        if (!AccountHandler.getInstance().getOption("racestatus")) {
            panel.removeAll();
        }

    }

}
