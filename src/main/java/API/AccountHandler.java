package API;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Account Handler Class, handles logging in and user information
 */
public class AccountHandler {

    private static AccountHandler instance;

    private boolean isLoggedIn;

    private int accountId;
    private String username;
    private String firstName;
    private String surname;

    private PreferencesHandler preferences;

    private Connection connection;

    public static AccountHandler getInstance() {
        if (instance == null) instance = new AccountHandler();
        return instance;
    }

    public AccountHandler() {
        isLoggedIn = false;
        connect();
        preferences = new PreferencesHandler(connection);

    }

    public void createAccount() {

    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void logOut() {
        isLoggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username='" + username.toLowerCase() + "';";
        try {
            Statement stmt  = connection.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                String passwordCheck = rs.getString("password");

                if (encryptText(password).contentEquals(passwordCheck)) {
                    this.accountId = rs.getInt("userId");
                    this.username = rs.getString("username");
                    this.firstName = rs.getString("firstName");
                    this.surname = rs.getString("surName");
                    isLoggedIn = true;
                    preferences.loadPreferences(accountId);
                    preferences.loadSpeech(accountId);
                    preferences.loadDriverNarrate(accountId);

                    System.out.println("Lewis Hamilton: " + preferences.getDriverNarrate(44));
                    System.out.println("Lando Norris: " + preferences.getDriverNarrate(4));

                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;

    }

    public boolean getOption(String option) {
        return preferences.getOption(option);
    }

    public void setOption(String option, boolean value) {
        preferences.setOption(option, value);
        preferences.savePreferences(accountId);
    }

    public String getSpeech(String option) {
        return preferences.getSpeech(option);
    }

    public void setSpeech(String speech, String value) {
        preferences.setSpeech(speech, value);
    }

    public boolean getDriverNarrate(int driverNumber) {
        return preferences.getDriverNarrate(driverNumber);
    }

    public void setDriverNarrate(int driverNumber, boolean value) {
        preferences.setDriverNarrateOptions(driverNumber, value);
        preferences.saveDriverNarrate(accountId);
    }

    public void saveSpeech() {
        preferences.saveSpeech(accountId);
    }

    public boolean register(String username, String password, String firstName, String surname) {
        String sql = "INSERT INTO users(username, password, firstName, surName) VALUES('" + username.toLowerCase() + "','" + encryptText(password) + "','" + firstName + "','" + surname + "');";
        try {
            Statement stmt  = connection.createStatement();
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void updateAccountDetail(String column, String value) {
        String sql = "Update Users SET " + column + " = '" + value + "' WHERE userId=" + accountId + ";";
        try {
            Statement stmt  = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String validatePassword(String password) {
        boolean isEightChars = password.length() >= 8;


        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        //Pattern eight = Pattern.compile (".{8}");


        Matcher hasLetter = letter.matcher(password);
        Matcher hasDigit = digit.matcher(password);
        Matcher hasSpecial = special.matcher(password);

        if (!isEightChars) return "needs to have at least 8 characters";
        if (!(hasLetter.find() && hasDigit.find())) return "needs to contain a mix of letters and numbers";
        if (!hasSpecial.find()) return "needs to contain a special character";
        return "";

    }

    public boolean checkIfAccountExists(String username) {
        String sql = "SELECT * FROM Users WHERE username='" + username.toLowerCase() + "';";
        try {
            Statement stmt  = connection.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    /**
     * Change this class in order to use database access
     */
    private void connect() {
        try {
            String url = "jdbc:sqlite:/home/james/Documents/Uni Work/LightsOut/LightsOut/db/db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static byte[] shaEncrypt(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public String encryptText(String text) {
        try {
            return(toHexString(shaEncrypt(text)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
