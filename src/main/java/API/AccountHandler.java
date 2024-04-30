package API;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AccountHandler {

    private static final Logger log = LoggerFactory.getLogger(AccountHandler.class);
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

    public void register(String username, String password, String firstName, String surname) {
        String sql = "INSERT INTO users(username, password, firstName, surName) VALUES('" + username.toLowerCase() + "','" + encryptText(password) + "','" + firstName + "','" + surname + "');";
        try {
            Statement stmt  = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
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

    private void connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:/home/james/Documents/Uni Work/LightsOut/LightsOut/db/db";
            // create a connection to the database
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        /* MessageDigest instance for hashing using SHA512*/
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        /* digest() method called to calculate message digest of an input and return array of byte */
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        /* Convert byte array of hash into digest */
        BigInteger number = new BigInteger(1, hash);

        /* Convert the digest into hex value */
        StringBuilder hexString = new StringBuilder(number.toString(16));

        /* Pad with leading zeros */
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public String encryptText(String text) {
        try {
            return(toHexString(getSHA(text)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
