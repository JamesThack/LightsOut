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

        panel.setBackground(new Color(21, 21, 31));

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

        for (Session cur : selector.getAllSessions()) {
            JButton newBut = new JButton();
            newBut.setPreferredSize(new Dimension(300, 100));
            newBut.setBackground(Color.RED);
            newBut.setForeground(Color.WHITE);
            if (cur.getName().contains("United Arab")) {
                newBut.setText("UAE" + " - " + cur.getDate().split("-")[0]);
            } else {
                newBut.setText(cur.getName() + " - " + cur.getDate().split("-")[0]);
            }

            newBut.setFont(new Font("Arial", Font.BOLD, 20));
            newBut.addActionListener(setRaceSessionListener(cur));
            panel.add(newBut);
        }

        JButton noLive = new JButton("NO LIVE RACE DETECTED");
        noLive.addActionListener(setLiveRace());
        noLive.setPreferredSize(new Dimension(300, 100));
        noLive.setBackground(Color.WHITE);
        noLive.setForeground(Color.BLACK);
        noLive.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(noLive);
    }

    public ActionListener setRaceSessionListener(Session session) {

        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DriverAPI.getInstance().regenerateDriverList(session.getSessionKey());
                MainScreen.getInstance().setSession(session);
                MainScreen.getInstance().setLive(false);
            }
        };
    }

    public ActionListener setLiveRace() {

        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DriverAPI.getInstance().regenerateDriverList("latest");
                MainScreen.getInstance().setSession(RaceSelector.getLiveSession());
                MainScreen.getInstance().setLive(true);
            }
        };
    }

}
