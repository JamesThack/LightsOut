package GUI.Pages;

import API.AccountHandler;
import GUI.Components.BackgroundFrame;
import GUI.MainScreen;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static APIObjects.RegexAssist.generateConstraints;

public class SignIn {

    private BackgroundFrame panel;

    private JTextArea loginUsername;
    private JPasswordField loginPassword;

    private JTextArea registerUsername;
    private JTextArea registerFirstName;
    private JTextArea registerSurname;
    private JPasswordField registerPassword;


    public SignIn(BackgroundFrame panel) {
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void refreshPanel() {
        panel.removeAll();
        panel.updateImage("/art/SignIn.png");
        initialise();
    }

    private void initialise() {
        panel.setLayout(new BorderLayout());

        JPanel left = new JPanel(new GridBagLayout());
        JPanel right = new JPanel(new GridBagLayout());

        left.setOpaque(false);
        right.setOpaque(false);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);

        loginUsername = new JTextArea();
        loginPassword = new JPasswordField();

        registerUsername = new JTextArea();
        registerFirstName = new JTextArea();
        registerSurname = new JTextArea();
        registerPassword = new JPasswordField();

        loginUsername.setPreferredSize(new Dimension(400, 30));
        loginPassword.setPreferredSize(new Dimension(400, 30));

        loginUsername.setFont(new Font("Arial", Font.PLAIN, 19));
        loginPassword.setFont(new Font("Arial", Font.PLAIN, 19));

        loginUsername.setBorder(new LineBorder(Color.black,2));
        loginPassword.setBorder(new LineBorder(Color.black,2));

        registerUsername.setPreferredSize(new Dimension(400, 30));
        registerPassword.setPreferredSize(new Dimension(400, 30));
        registerFirstName.setPreferredSize(new Dimension(400, 30));
        registerSurname.setPreferredSize(new Dimension(400, 30));

        registerUsername.setFont(new Font("Arial", Font.PLAIN, 19));
        registerPassword.setFont(new Font("Arial", Font.PLAIN, 19));
        registerFirstName.setFont(new Font("Arial", Font.PLAIN, 19));
        registerSurname.setFont(new Font("Arial", Font.PLAIN, 19));

        registerUsername.setBorder(new LineBorder(Color.black,2));
        registerPassword.setBorder(new LineBorder(Color.black,2));
        registerFirstName.setBorder(new LineBorder(Color.black,2));
        registerSurname.setBorder(new LineBorder(Color.black,2));

        JLabel loginUsernameLabel = new JLabel("Username");
        loginUsernameLabel.setFont(new Font("Arial", Font.BOLD, 23));
        loginUsernameLabel.setForeground(Color.RED);
        JLabel loginPasswordLabel = new JLabel("Password");
        loginPasswordLabel.setForeground(Color.RED);
        loginPasswordLabel.setFont(new Font("Arial", Font.BOLD, 23));
        JLabel registerUsernameLabel = new JLabel("Username");
        registerUsernameLabel.setForeground(Color.RED);
        registerUsernameLabel.setFont(new Font("Arial", Font.BOLD, 23));
        JLabel registerPasswordLabel = new JLabel("Password");
        registerPasswordLabel.setForeground(Color.RED);
        registerPasswordLabel.setFont(new Font("Arial", Font.BOLD, 23));
        JLabel firstName = new JLabel("First Name");
        firstName.setForeground(Color.RED);
        firstName.setFont(new Font("Arial", Font.BOLD, 23));
        JLabel surname = new JLabel("Surname");
        surname.setForeground(Color.RED);
        surname.setFont(new Font("Arial", Font.BOLD, 23));

        JButton loginButton = new JButton("Sign In");
        loginButton.setBackground(Color.RED);
        loginButton.setForeground(Color.white);
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.RED);
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(200, 40));
        registerButton.setFont(new Font("Arial", Font.BOLD, 20));

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
                if (AccountHandler.getInstance().login(loginUsername.getText().toLowerCase(), loginPassword.getText())) {
                    System.out.println("Logged in!");
                    MainScreen.getInstance().checkLoggedIn();
                } else {
                    JOptionPane.showMessageDialog(panel, "Incorrect account credentials",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    public ActionListener register() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (registerUsername.getText().length() < 2 || registerFirstName.getText().length() < 2 || registerSurname.getText().length() < 2) {
                    JOptionPane.showMessageDialog(panel, "Please ensure no fields are empty",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (AccountHandler.getInstance().checkIfAccountExists(registerUsername.getText())) {
                    JOptionPane.showMessageDialog(panel, "Username Exists!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (AccountHandler.getInstance().validatePassword(registerPassword.getText()) != "") {
                    JOptionPane.showMessageDialog(panel, "Password " + AccountHandler.getInstance().validatePassword(registerPassword.getText()),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }



                AccountHandler.getInstance().register(registerUsername.getText().toLowerCase(), registerPassword.getText(), registerFirstName.getText(), registerSurname.getText());
                AccountHandler.getInstance().login(registerUsername.getText().toLowerCase(), registerPassword.getText());
                MainScreen.getInstance().checkLoggedIn();
            }
        };
    }
}
