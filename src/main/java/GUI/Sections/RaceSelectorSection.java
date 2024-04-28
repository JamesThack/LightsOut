package GUI.Sections;

import API.Components.Session;
import API.DriverAPI;
import API.Race.Race;
import API.Race.RaceSelector;
import GUI.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceSelectorSection {

    private final JPanel panel;
    private RaceSelector selector;

    public RaceSelectorSection() {
        panel = new JPanel();

        selector = new RaceSelector();

        initialise();
    }

    public void redrawComponents() {
        panel.removeAll();
        addInRaces();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initialise() {
        addInRaces();
    }

    private void addInRaces() {
        JButton noLive = new JButton("NO LIVE RACE DETECTED");
        noLive.setPreferredSize(new Dimension(300, 100));
        noLive.setBackground(Color.RED);
        noLive.setForeground(Color.WHITE);
        panel.add(noLive);
        for (Session cur : selector.getAllSessions()) {
            JButton newBut = new JButton();
            newBut.setPreferredSize(new Dimension(300, 100));
            newBut.setBackground(Color.CYAN);
            newBut.setText(cur.getName() + " - " + cur.getDate().split("-")[0]);
            newBut.addActionListener(setRaceSessionListener(cur));
            panel.add(newBut);
        }
    }

    public ActionListener setRaceSessionListener(Session session) {

        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DriverAPI.getInstance().regenerateDriverList(session.getSessionKey());
                MainScreen.getInstance().setSession(session);
            }
        };
    }

}
