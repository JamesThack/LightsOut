package GUI.Sections;

import API.AccountHandler;
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
    private JLabel safety;
    private JLabel flag;
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
        jSeconds.setText("Race Time: " + RegexAssist.convertToTimeString(seconds - mainScreen.getSession().getStartTime()) + "  ");
        jSeconds.validate();
        jSeconds.repaint();

        if (lapCalculator != null)jLaps.setText("Current Lap: " + lapCalculator.getLapFromTime(seconds) + "   ");
        jLaps.validate();
        jLaps.repaint();

        if (mainScreen.getSafetyCar() != null) {
            if (mainScreen.getSafetyCar().isSafetyCar(seconds)) {
                if (safety.getText()!= "Safety Car Status:" && safety.getText().equals("Safety Car Satus: NO SAFETY CAR  ") && AccountHandler.getInstance().getOption("conditionnarrate")) mainScreen.getSpeechHandler().addSpeech(new SpeechRequest(-2, AccountHandler.getInstance().getSpeech("conditions"), -2));
                safety.setText("Safety Car Satus: SAFETY CAR  ");
                safety.setForeground(Color.YELLOW);
            } else {
                if (safety.getText()!= "" && safety.getText() != "Safety Car Status:" && !safety.getText().equals("Safety Car Satus: NO SAFETY CAR  ") && AccountHandler.getInstance().getOption("conditionnarrate")) mainScreen.getSpeechHandler().addSpeech(new SpeechRequest(-2, AccountHandler.getInstance().getSpeech("pits"), -2));
                safety.setText("Safety Car Satus: NO SAFETY CAR  ");
                safety.setForeground(Color.WHITE);
            }

        }

        if (!AccountHandler.getInstance().getOption("safetycarstatus")) {
            safety.setText("");
        }

        if (mainScreen.getFlags() != null) {
            if (mainScreen.getFlags().isRedFlag(seconds)) {
                if (flag.getText()!= "Red Flag Status:" && flag.getText().equals("Red Flag Status: GREEN FLAG  ") && AccountHandler.getInstance().getOption("flagnarrate")) mainScreen.getSpeechHandler().addSpeech(new SpeechRequest(-3, AccountHandler.getInstance().getSpeech("flag") + " red flag ", -3));
                flag.setText("Red Flag Status: RED FLAG  ");
                flag.setForeground(Color.BLUE);
            } else {
                if (flag.getText()!= "" && flag.getText()!= "Red Flag Status:" && !flag.getText().equals("Red Flag Status: GREEN FLAG  ") && AccountHandler.getInstance().getOption("flagnarrate")) mainScreen.getSpeechHandler().addSpeech(new SpeechRequest(-3, AccountHandler.getInstance().getSpeech("flag") + " green flag ", -3));
                flag.setText("Red Flag Status: GREEN FLAG  ");
                flag.setForeground(Color.GREEN);
            }

        }

        if (!AccountHandler.getInstance().getOption("flagstatus")) {
            flag.setText("");
        }
    }
    
    public JPanel getPanel() {
        return panel;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {

        if (lapCalculator != null && lapCalculator.getLapFromTime(this.seconds) != lapCalculator.getLapFromTime(seconds) && AccountHandler.getInstance().getOption("newlapnarrate")) mainScreen.getSpeechHandler().addSpeech(new SpeechRequest(-1, AccountHandler.getInstance().getSpeech("newlap") + " " + lapCalculator.getLapFromTime(seconds), -1));
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

        safety = new JLabel("Safety Car Status:");
        safety.setForeground(new Color(255, 255, 255));
        safety.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(safety);

        flag = new JLabel("Red Flag Status:");
        flag.setForeground(new Color(255, 255, 255));
        flag.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(flag);

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

    public LapCalculator getLapCalculator() {
        return lapCalculator;
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
