package GUI.Pages;

import API.AccountHandler;
import GUI.MainScreen;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
        JLabel loginPasswordLabel = new JLabel("Password");
        loginPasswordLabel.setFont(new Font("Arial", Font.BOLD, 23));
        JLabel registerUsernameLabel = new JLabel("Username");
        registerUsernameLabel.setFont(new Font("Arial", Font.BOLD, 23));
        JLabel registerPasswordLabel = new JLabel("Password");
        registerPasswordLabel.setFont(new Font("Arial", Font.BOLD, 23));
        JLabel firstName = new JLabel("First Name");
        firstName.setFont(new Font("Arial", Font.BOLD, 23));
        JLabel surname = new JLabel("Surname");
        surname.setFont(new Font("Arial", Font.BOLD, 23));

        JButton loginButton = new JButton("Sign In");
        loginButton.setBackground(Color.cyan);
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.green);
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
                if (AccountHandler.getInstance().login(loginUsername.getText(), loginPassword.getText())) {
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
                if (registerUsername.getText().length() < 2 || registerPassword.getText().length() < 2 || registerFirstName.getText().length() < 2 || registerSurname.getText().length() < 2) {
                    JOptionPane.showMessageDialog(panel, "Please input valid information",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AccountHandler.getInstance().register(registerUsername.getText(), registerPassword.getText(), registerFirstName.getText(), registerSurname.getText());
                AccountHandler.getInstance().login(registerUsername.getText(), registerPassword.getText());
                MainScreen.getInstance().checkLoggedIn();
            }
        };
    }
}
