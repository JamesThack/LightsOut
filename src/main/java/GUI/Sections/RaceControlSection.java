package GUI.Sections;

import API.Components.SpeechRequest;
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
    private float zoomAmount;

    private LapCalculator lapCalculator;

    private int seconds;
    private boolean isPaused;

    private MainScreen mainScreen;
    
    public RaceControlSection(MainScreen mainScreen) {


        this.mainScreen = mainScreen;

        isPaused = false;

        if(mainScreen.getSession() != null) {
            lapCalculator = new LapCalculator(mainScreen.getSession().getStartTime(), mainScreen.getSession().getSessionKey());
            this.seconds = lapCalculator.getTimeFromLap(1);
        } else {
            this.seconds = 0;
        }

        zoomAmount = 1;

        initialiseLayout();
    }
    
    public void updateView() {
        jSeconds.setText("Race Time: " + RegexAssist.convertToTimeString(seconds) + "  ");
        jSeconds.validate();
        jSeconds.repaint();

        if (lapCalculator != null)jLaps.setText("Current Lap: " + lapCalculator.getLapFromTime(seconds) + "   ");
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

        if (lapCalculator != null && lapCalculator.getLapFromTime(this.seconds) != lapCalculator.getLapFromTime(seconds)) mainScreen.getSpeechHandler().addSpeech(new SpeechRequest(-1, "Now on lap " + lapCalculator.getLapFromTime(seconds), -1));
        this.seconds = seconds;
    }


    public float getZoomAmount() {
        return zoomAmount;
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public int getCurrentLap() {
        if (lapCalculator != null) return lapCalculator.getLapFromTime(seconds);
        return 1;
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

        loadNewButtons(new String[]{"⏸", "+5 Sec", "-5 Sec", "-1 Lap", "+1 Lap", "Zoom +", "Zoom -", "Menu", "Clear"},
                new ActionListener[]{
                        getPlayPauseActionListener(),
                        getSecondsActionListener(+ 5),
                        getSecondsActionListener( -5),
                        getLapsActionListener(-1),
                        getLapsActionListener(1),
                        getZoomListener(0.2F),
                        getZoomListener(-0.2F),
                        mainScreen.changeRace(),
                        clearCacheListener()});
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

    public ActionListener getLapsActionListener(int modifier) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(lapCalculator != null) setSeconds(lapCalculator.getTimeFromLap(getCurrentLap() + modifier) + 1);
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

    public ActionListener clearCacheListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen.getInstance().getSpeechHandler().clearCache();
            }
        };
    }
}
