package GUI.Sections;

import javax.swing.*;
import java.awt.*;

public class RaceInfoSection {

    private final JPanel panel;

    public RaceInfoSection() {
        panel = new JPanel();

        initialise();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initialise() {
        panel.setBackground(new Color(0, 0, 0));

        JLabel lblTime = new JLabel("Conditions:");
        lblTime.setForeground(new Color(255, 255, 255));
        panel.add(lblTime);
    }

}
