package GUI.Sections;

import APIObjects.Race.LapCalculator;
import APIObjects.RegexAssist;
import GUI.Components.RoundedButton;
import com.sun.org.apache.xerces.internal.impl.dv.xs.UnionDV;

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
        jSeconds.setText("Seconds: " + RegexAssist.convertToTimeString(seconds));
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


        JButton btnNewButton = new JButton("▶");
        btnNewButton.addActionListener(getPlayPauseActionListener(false));
        panel.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("⏸");
        btnNewButton_1.addActionListener(getPlayPauseActionListener(true));
        panel.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("+5 Sec");
        btnNewButton_2.addActionListener(getSecondsActionListener(+ 5));
        panel.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("-5 Sec");
        btnNewButton_3.addActionListener(getSecondsActionListener( -5));
        panel.add(btnNewButton_3);

        timeInput = new JTextArea();
        timeInput.setText(RegexAssist.convertToTimeString(seconds));
        panel.add(timeInput);

        JButton btnSetTime = new JButton("Set Time");
        btnSetTime.addActionListener(getSecondsSetActionListener());
        panel.add(btnSetTime);

        JButton plusLap = new JButton("+1 Lap");
        plusLap.addActionListener(getLapsActionListener(1));
        panel.add(plusLap);

        JButton minusLap = new JButton("-1 Lap");
        minusLap.addActionListener(getLapsActionListener(- 1));
        panel.add(minusLap);
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
