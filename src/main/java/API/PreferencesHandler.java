package API;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The preferences handler, sorts all of the user preferences
 */
public class PreferencesHandler {

    private HashMap<String, Boolean> options;
    private HashMap<String, String> voice;
    private HashMap<Integer, Boolean> driverNarrateOptions;

    private Connection connection;


    public PreferencesHandler(Connection connection) {
        options = new HashMap<>();
        voice = new HashMap<>();
        driverNarrateOptions = new HashMap<>();

        this.connection = connection;

        initialiseDefaults();
    }

    public boolean getOption(String option) {
        if (options.get(option)== null) return true;
        return options.get(option);
    }

    public String getSpeech(String option) {
        if (voice.get(option)== null) return "Missing voice option";
        return voice.get(option);
    }

    public void setOption(String option, boolean value) {
        options.put(option, value);
    }

    public boolean getDriverNarrate(int driver) {
        if (driverNarrateOptions.get(driver) == null) return true;
        return driverNarrateOptions.get(driver);
    }

    public void setDriverNarrateOptions(int driver, boolean value) {
        driverNarrateOptions.put(driver, value);
    }

    public void setSpeech(String speech, String text) {
        voice.put(speech, text);
    }

    public void initialiseDefaults() {
        options.put("narrator", true);
        options.put("newlapnarrate", true);
        options.put("overtakenarrate", true);
        options.put("pitnarrate", true);
        options.put("conditionnarrate", true);
        options.put("flagnarrate", true);
        options.put("driverdescend", true);
        options.put("flagstatus", true);
        options.put("racestatus", true);
        options.put("safetycarstatus", true);

        voice.put("newposition", "Is now in position");
        voice.put("newlap", "Now on lap");
        voice.put("pits", "Safety car is coming in");
        voice.put("conditions", "Safety car is going out");
        voice.put("flag", "Flag has changed to");
    }

    public void loadPreferences(int accountId) {

        String sql = "SELECT * FROM Preferences WHERE userId=" + accountId + ";";
        try {
            Statement stmt  = connection.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {

                String preferenceName = rs.getString("preferenceName");
                boolean preferenceValue = (rs.getInt("preferenceValue") == 1);
                options.put(preferenceName, preferenceValue);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void loadDriverNarrate(int accountId) {

        String sql = "SELECT * FROM DriverNarrate WHERE userId=" + accountId + ";";
        try {
            Statement stmt  = connection.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {

                int driverNumber = rs.getInt("driverNumber");
                boolean preferenceValue = (rs.getInt("value") == 1);
                driverNarrateOptions.put(driverNumber, preferenceValue);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void loadSpeech(int accountId) {

        String sql = "SELECT * FROM Speech WHERE userId=" + accountId + ";";
        try {
            Statement stmt  = connection.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {

                String speechName = rs.getString("speechName");
                String speechValue = rs.getString("speechValue");
                voice.put(speechName, speechValue);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void saveSpeech(int accountId) {
        for (String cur : voice.keySet()) {
            String sql = "Update Speech SET speechValue = '" + voice.get(cur) + "' WHERE userId=" + accountId + " AND speechName='" + cur + "';";
            if (!checkIfSpeechExists(accountId, cur)) sql = "INSERT INTO Speech(userId, speechName, speechValue) VALUES(" + accountId + ",'" +cur + "','" + voice.get(cur) + "');";
            try {
                Statement stmt  = connection.createStatement();
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }


        }
    }



    public void savePreferences(int accountId) {
        for (String cur : options.keySet()) {
            int saveValue = 1;
            if (!options.get(cur)) saveValue = 0;
            String sql = "Update Preferences SET preferenceValue = " + saveValue + " WHERE userId=" + accountId + " AND preferenceName='" + cur + "';";
            if (!checkIfPreferenceExists(accountId, cur)) sql = "INSERT INTO Preferences(userId, preferenceName, preferenceValue) VALUES(" + accountId + ",'" +cur + "'," + options.get(cur) + ");";
            try {
                Statement stmt  = connection.createStatement();
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }


        }
    }

    public void saveDriverNarrate(int accountId) {
        for (int cur : driverNarrateOptions.keySet()) {
            int saveValue = 1;
            if (!driverNarrateOptions.get(cur)) saveValue = 0;
            String sql = "Update DriverNarrate SET value = " + saveValue + " WHERE userId=" + accountId + " AND driverNumber=" + cur + ";";
            if (!checkIfDriverNarrateExists(accountId, cur)) sql = "INSERT INTO DriverNarrate(userId, driverNumber, value) VALUES(" + accountId + "," +cur + "," + driverNarrateOptions.get(cur) + ");";
            try {
                Statement stmt  = connection.createStatement();
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean checkIfPreferenceExists(int accountId, String preference) {
        String sql = "SELECT * FROM Preferences WHERE userId=" + accountId + " AND preferenceName='" + preference + "';";
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

    public boolean checkIfDriverNarrateExists(int accountId, int driverNumber) {
        String sql = "SELECT * FROM DriverNarrate WHERE userId=" + accountId + " AND driverNumber=" + driverNumber + ";";
        try {
            Statement stmt  = connection.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean checkIfSpeechExists(int accountId, String speech) {
        String sql = "SELECT * FROM Speech WHERE userId=" + accountId + " AND speechName='" + speech + "';";
        try {
            Statement stmt  = connection.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


}
