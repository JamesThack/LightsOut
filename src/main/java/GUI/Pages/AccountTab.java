package GUI.Pages;

import API.AccountHandler;
import APIObjects.RegexAssist;
import GUI.Components.RoundedButton;
import GUI.MainScreen;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static APIObjects.RegexAssist.generateConstraints;

public class AccountTab {

    private JPanel panel;
    private JPanel left;
    private JPanel right;
    private JPanel center;

    private JTextArea usernameInput;
    private JTextArea firstNameInput;
    private JTextArea surnameInput;
    private JPasswordField passwordInput;

    HashMap<String, JTextArea> speechInput;

    public AccountTab(MainScreen mainScreen, JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void refreshPage() {
        panel.removeAll();
        initialisePage();
    }

    public void initialisePage() {

        speechInput = new HashMap<>();

        panel.setLayout(new BorderLayout(0, 0));

        right = new JPanel(new GridBagLayout());
        left = new JPanel(new GridBagLayout());
        center = new JPanel(new GridBagLayout());

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        panel.add(center, BorderLayout.CENTER);

        quickAddLabel("Account Information", left, 0, 0, new Insets(0, 100, 300, 100));
        quickAddLabel("System Messages", center, 0, 0, new Insets(0, 100, 370, 100));
        quickAddLabel("Options", right, 0, 0, new Insets(0, 100, 250, 100));

        quickAddLabel("Username", left,0, 1);
        usernameInput = quickAddTextInput("", left, 0, 2);
        quickAddLabel("First Name", left, 0, 3);
        firstNameInput = quickAddTextInput("", left, 0, 4);
        quickAddLabel("Surname", left, 0, 5);
        surnameInput = quickAddTextInput("", left, 0, 6);
        quickAddLabel("Password", left, 0, 7);

        passwordInput = new JPasswordField("");
        passwordInput.setPreferredSize(new Dimension(400, 30));
        passwordInput.setFont(new Font("Arial", Font.PLAIN, 19));
        passwordInput.setBorder(new LineBorder(Color.black,2));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 8;
        left.add(passwordInput, constraints);

        JButton changeInfo = new JButton("Change details");
        changeInfo.setPreferredSize(new Dimension(200, 40));
        changeInfo.setFont(new Font("Arial", Font.BOLD, 20));
        changeInfo.setBackground(Color.CYAN);
        changeInfo.addActionListener(updateAccount());
        left.add(changeInfo, generateConstraints(0, 9, new Insets(50, 0, 10, 0)));

        JButton signOut = new JButton("Sign Out");
        signOut.setForeground(new Color(255, 255, 255));
        signOut.setBackground(new Color(255, 0, 0));
        signOut.addActionListener(logOut());
        signOut.setPreferredSize(new Dimension(200, 40));
        signOut.setFont(new Font("Arial", Font.BOLD, 20));
        left.add(signOut, generateConstraints(0, 10, new Insets(10, 0, 10, 0)));

        left.setBorder(new LineBorder(Color.black,2));
        center.setBorder(new LineBorder(Color.black,2));
        right.setBorder(new LineBorder(Color.black,2));

        quickAddLabel("New Position", center, 0, 1);
        quickAddTextInput(center, 0, 2, "newposition");

        quickAddLabel("New Lap", center, 0, 3);
        quickAddTextInput(center, 0, 4, "newlap");

        quickAddLabel("Safety Car In", center, 0, 5);
        quickAddTextInput( center, 0, 6, "pits");

        quickAddLabel("Safety Car Out", center, 0, 7);
        quickAddTextInput(center, 0, 8, "conditions");

        quickAddLabel("Flag", center, 0, 9);
        quickAddTextInput( center, 0, 10, "flag");

        JButton updateSpeech = new JButton("Update TTS Configuration");
        updateSpeech.setBackground(Color.CYAN);
        updateSpeech.addActionListener(changeSpeech());
        updateSpeech.setPreferredSize(new Dimension(300, 40));
        updateSpeech.setFont(new Font("Arial", Font.BOLD, 20));
        center.add(updateSpeech, generateConstraints(0, 11, new Insets(50, 0, 10, 0)));

        JLabel isNarratorLabel = quickAddLabel("Narrator enabled", right, 0, 1);
        JCheckBox isNarrator = quickAddCheckBox(right, 1, 1, "narrator");

        quickAddLabel("Narrator narrates new lap", right, 0, 2);
        quickAddCheckBox(right, 1, 2, "newlapnarrate");

        quickAddLabel("Narrator narrates overtakes", right, 0, 3);
        quickAddCheckBox(right, 1, 3, "overtakenarrate");

        quickAddLabel("Narrator narrates pits", right, 0, 4);
        quickAddCheckBox(right, 1, 4, "pitnarrate");

        quickAddLabel("Narrator narrates safety car", right, 0, 5);
        quickAddCheckBox(right, 1, 5, "conditionnarrate");

        quickAddLabel("Narrator narrates flags", right, 0, 6);
        quickAddCheckBox( right, 1, 6, "flagnarrate");

        quickAddLabel("Order Drivers Descending", right, 0, 7);
        quickAddCheckBox(right, 1, 7, "driverdescend");

        quickAddLabel("Show flag status", right, 0, 8);
        quickAddCheckBox(right, 1, 8, "flagstatus");

        quickAddLabel("Show race information", right, 0,9);
        quickAddCheckBox(right, 1, 9, "racestatus");

        quickAddLabel("Show safety car status", right, 0, 10);
        quickAddCheckBox(right, 1, 10, "safetycarstatus");

        JButton driverConfig = new JButton("Update Driver Configuration");
        driverConfig.addActionListener(openDriverNarratorMenu());
        right.add(driverConfig, RegexAssist.generateConstraints(0, 11));

    }

    private JLabel quickAddLabel(String text, JPanel panel, int x, int y) {

        JLabel newLab = new JLabel(text);
        newLab.setFont(new Font("Arial", Font.BOLD, 23));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = new Insets(10, 100, 10, 100);
        panel.add(newLab, constraints);
        return newLab;

    }

    private JLabel quickAddLabel(String text, JPanel panel, int x, int y, Insets insets) {

        JLabel newLab = new JLabel(text);
        newLab.setFont(new Font("Arial", Font.BOLD, 23));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = insets;
        panel.add(newLab, constraints);
        return newLab;

    }

    private JTextArea quickAddTextInput(String text, JPanel panel, int x, int y) {
        JTextArea newText = new JTextArea(text);
        newText.setPreferredSize(new Dimension(400, 30));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = new Insets(0, 100, 0, 100);
        newText.setFont(new Font("Arial", Font.PLAIN, 19));
        newText.setBorder(new LineBorder(Color.black,2));
        panel.add(newText, constraints);
        return newText;
    }

    private JTextArea quickAddTextInput(JPanel panel, int x, int y, String speech) {
        JTextArea newText = new JTextArea(AccountHandler.getInstance().getSpeech(speech));
        newText.setPreferredSize(new Dimension(400, 30));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = new Insets(0, 100, 0, 100);
        newText.setFont(new Font("Arial", Font.PLAIN, 19));
        newText.setBorder(new LineBorder(Color.black,2));
        panel.add(newText, constraints);

        speechInput.put(speech, newText);
        return newText;
    }

    private JCheckBox quickAddCheckBox(JPanel panel, int x, int y, String preference) {
        JCheckBox checkBox = new JCheckBox("", AccountHandler.getInstance().getOption(preference));
        checkBox.addActionListener(changePreference(preference, checkBox));
        panel.add(checkBox, generateConstraints(x, y, new Insets(0, 10, 0, 100)));

        return checkBox;
    }

    public ActionListener logOut() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountHandler.getInstance().logOut();
                MainScreen.getInstance().checkLoggedIn();
            }
        };
    }

    public ActionListener changePreference(String preferenceName, JCheckBox checkBox) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountHandler.getInstance().setOption(preferenceName, checkBox.isSelected());
            }
        };
    }

    public ActionListener changeSpeech() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (String cur : speechInput.keySet()) {
                    AccountHandler.getInstance().setSpeech(cur, speechInput.get(cur).getText());
                    AccountHandler.getInstance().saveSpeech();
                }
            }
        };
    }

    public ActionListener openDriverNarratorMenu() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DriverNarratorSelectScreen driverMenu = new DriverNarratorSelectScreen();
            }
        };
    }

    public ActionListener updateAccount() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usernameInput.getText().length() > 2) AccountHandler.getInstance().updateAccountDetail("username", usernameInput.getText());
                if (firstNameInput.getText().length() > 2) AccountHandler.getInstance().updateAccountDetail("firstName", firstNameInput.getText());
                if (surnameInput.getText().length() > 2) AccountHandler.getInstance().updateAccountDetail("surName", surnameInput.getText());
                if (passwordInput.getText().length() > 2) AccountHandler.getInstance().updateAccountDetail("password", AccountHandler.getInstance().encryptText(passwordInput.getText()));

                usernameInput.setText("");
                firstNameInput.setText("");
                surnameInput.setText("");
                passwordInput.setText("");

            }
        };
    }


}
