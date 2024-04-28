package GUI.Pages;

import API.AccountHandler;
import GUI.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static APIObjects.RegexAssist.generateConstraints;

public class SignIn {

    private JPanel panel;

    private JTextArea loginUsername;
    private JPasswordField loginPassword;

    private JTextArea registerUsername;
    private JTextArea registerFirstName;
    private JTextArea registerSurname;
    private JPasswordField registerPassword;


    public SignIn(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void refreshPanel() {
        panel.removeAll();
        initialise();
    }

    private void initialise() {
        panel.setLayout(new BorderLayout());

        JPanel left = new JPanel(new GridBagLayout());
        JPanel right = new JPanel(new GridBagLayout());

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);

        loginUsername = new JTextArea();
        loginPassword = new JPasswordField();

        registerUsername = new JTextArea();
        registerFirstName = new JTextArea();
        registerSurname = new JTextArea();
        registerPassword = new JPasswordField();

        loginUsername.setPreferredSize(new Dimension(300, 20));
        loginPassword.setPreferredSize(new Dimension(300, 20));

        registerUsername.setPreferredSize(new Dimension(300, 20));
        registerPassword.setPreferredSize(new Dimension(300, 20));
        registerFirstName.setPreferredSize(new Dimension(300, 20));
        registerSurname.setPreferredSize(new Dimension(300, 20));


        JLabel loginUsernameLabel = new JLabel("Username");
        JLabel loginPasswordLabel = new JLabel("Password");
        JLabel registerUsernameLabel = new JLabel("Username");
        JLabel registerPasswordLabel = new JLabel("Password");
        JLabel firstName = new JLabel("First Name");
        JLabel surname = new JLabel("Surname");

        JButton loginButton = new JButton("Sign In");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(login());
        registerButton.addActionListener(register());

        Insets layoutInsets =  new Insets(10, 100, 10, 100);

        left.add(loginUsernameLabel, generateConstraints(0, 0, layoutInsets));
        left.add(loginUsername, generateConstraints(0, 1, layoutInsets));
        left.add(loginPasswordLabel, generateConstraints(0, 2, layoutInsets));
        left.add(loginPassword, generateConstraints(0, 3, layoutInsets));
        left.add(loginButton, generateConstraints(0, 4, layoutInsets));

        right.add(registerUsernameLabel, generateConstraints(0, 0, layoutInsets));
        right.add(registerUsername, generateConstraints(0, 1, layoutInsets));
        right.add(registerPasswordLabel, generateConstraints(0, 2, layoutInsets));
        right.add(registerPassword, generateConstraints(0, 3, layoutInsets));
        right.add(firstName, generateConstraints(0, 4, layoutInsets));
        right.add(registerFirstName, generateConstraints(0, 5, layoutInsets));
        right.add(surname, generateConstraints(0, 6, layoutInsets));
        right.add(registerSurname, generateConstraints(0, 7, layoutInsets));
        right.add(registerButton, generateConstraints(0, 8, layoutInsets));




    }

    public ActionListener login() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AccountHandler.getInstance().login(loginUsername.getText(), loginPassword.getText())) {
                    System.out.println("Logged in!");
                    MainScreen.getInstance().checkLoggedIn();
                } else {
                    System.out.println("Incorrect password");
                }
            }
        };
    }

    public ActionListener register() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountHandler.getInstance().register(registerUsername.getText(), registerPassword.getText(), registerFirstName.getText(), registerSurname.getText());
            }
        };
    }
}
