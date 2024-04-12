package GUI.Sections;

import API.Race.LapCalculator;
import APIObjects.RegexAssist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceControlSection {

    private JLabel jSeconds;
    private JLabel jLaps;
    private JPanel panel;
    private JTextArea timeInput;

    private int seconds;
    private boolean isPaused;
    
    public RaceControlSection(int initialSeconds) {

        this.seconds = initialSeconds;
        isPaused = false;

        initialiseLayout();
    }
    
    public void updateView() {
        jSeconds.setText("Race Time: " + RegexAssist.convertToTimeString(seconds));
        jSeconds.validate();
        jSeconds.repaint();

        jLaps.setText("Current Lap: " + LapCalculator.getInstance().getLapFromTime(seconds));
        jLaps.validate();
        jLaps.repaint();
    }
    
    public JPanel getPanel() {
        return panel;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
        updateView();
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public int getCurrentLap() {
        return LapCalculator.getInstance().getLapFromTime(seconds);
    }

    private void initialiseLayout() {
        panel = new JPanel();
        panel.setBackground(new Color(224, 27, 36));
        
        jSeconds = new JLabel("Race Time: ");
        jSeconds.setForeground(new Color(255, 255, 255));
        panel.add(jSeconds);

        jLaps = new JLabel("Current Lap: ");
        jLaps.setForeground(new Color(255, 255, 255));
        panel.add(jLaps);

        loadNewButtons(new String[]{"▶", "⏸", "+5 Sec", "-5 Sec", "Set Time", "+1 Lap", "-1 Lap"},
                new ActionListener[]{
                        getPlayPauseActionListener(false),
                        getPlayPauseActionListener(true),
                        getSecondsActionListener(+ 5),
                        getSecondsActionListener( -5),
                        getSecondsSetActionListener(),
                        getLapsActionListener(1),
                        getLapsActionListener(-1)});

        timeInput = new JTextArea();
        timeInput.setText(RegexAssist.convertToTimeString(seconds));
        panel.add(timeInput);
    }

    private void loadNewButtons(String[] names, ActionListener[] listeners) {
        for (int i = 0; i< names.length; i++) {
            JButton newBut = new JButton(names[i]);
            newBut.addActionListener(listeners[i]);
            panel.add(newBut);
        }
    }

    public ActionListener getPlayPauseActionListener(boolean value) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = value;
            }
        };
    }

    public ActionListener getSecondsActionListener(int modifier) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSeconds(seconds + modifier);
            }
        };
    }

    public ActionListener getSecondsSetActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSeconds(RegexAssist.convertToUnix(timeInput.getText()));
            }
        };
    }

    public ActionListener getLapsActionListener(int modifier) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSeconds(LapCalculator.getInstance().getTimeFromLap(getCurrentLap() + modifier) + 1);
            }
        };
    }
}
