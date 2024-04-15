package GUI.Sections;

import API.Race.LapCalculator;
import APIObjects.RegexAssist;
import GUI.Components.RoundedButton;
import GUI.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceControlSection {

    private JLabel jSeconds;
    private JLabel jLaps;
    private JPanel panel;
    private JTextArea timeInput;
    private float zoomAmount;

    private int seconds;
    private boolean isPaused;
    
    public RaceControlSection(int initialSeconds) {

        this.seconds = initialSeconds;
        isPaused = false;

        zoomAmount = 1;

        initialiseLayout();
    }
    
    public void updateView() {
        jSeconds.setText("Race Time: " + RegexAssist.convertToTimeString(seconds) + "  ");
        jSeconds.validate();
        jSeconds.repaint();

        jLaps.setText("Current Lap: " + LapCalculator.getInstance().getLapFromTime(seconds) + "   ");
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


    public float getZoomAmount() {
        return zoomAmount;
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
        panel.setLayout(new FlowLayout());
        
        jSeconds = new JLabel("Race Time: ");
        jSeconds.setForeground(new Color(255, 255, 255));
        jSeconds.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(jSeconds);

        jLaps = new JLabel("Current Lap: ");
        jLaps.setForeground(new Color(255, 255, 255));
        jLaps.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(jLaps);

        loadNewButtons(new String[]{"⏸", "+5 Sec", "-5 Sec", "-1 Lap", "+1 Lap", "Set Time", "Zoom +", "Zoom -"},
                new ActionListener[]{
                        getPlayPauseActionListener(),
                        getSecondsActionListener(+ 5),
                        getSecondsActionListener( -5),
                        getLapsActionListener(-1),
                        getLapsActionListener(1),
                        getSecondsSetActionListener(),
                        getZoomListener(0.2F),
                        getZoomListener(-0.2F)});

        timeInput = new JTextArea();
        timeInput.setText(RegexAssist.convertToTimeString(seconds));
        timeInput.setFont(new Font("Arial",  Font.BOLD, 18));
        timeInput.setMargin(new Insets(3, 3, 3, 3));
        panel.add(timeInput);
    }

    private void loadNewButtons(String[] names, ActionListener[] listeners) {
        for (int i = 0; i< names.length; i++) {
            JButton newBut = new RoundedButton(names[i], 60);
            newBut.setBackground(new Color(255, 255, 255));
            newBut.addActionListener(listeners[i]);
            panel.add(newBut);
        }
    }

    public ActionListener getPlayPauseActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = !isPaused;

                JButton button = (JButton) e.getSource();
                if (isPaused) button.setText("▶");
                if (!isPaused) button.setText("⏸");
            }
        };
    }

    public ActionListener getSecondsActionListener(int modifier) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSeconds(seconds + modifier);
                MainScreen.getInstance().updateWholeScreen();
            }
        };
    }

    public ActionListener getSecondsSetActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSeconds(RegexAssist.convertToUnix(timeInput.getText()));
                MainScreen.getInstance().updateWholeScreen();
            }
        };
    }

    public ActionListener getLapsActionListener(int modifier) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSeconds(LapCalculator.getInstance().getTimeFromLap(getCurrentLap() + modifier) + 1);
                MainScreen.getInstance().updateWholeScreen();
            }
        };
    }

    public ActionListener getZoomListener(float modifier) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomAmount += modifier;
                MainScreen.getInstance().updateWholeScreen();
            }
        };
    }
}
