package API;

import APIObjects.Driver;
import APIObjects.Team;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class DriverAPI {

    private ArrayList<Driver> drivers;
    private static DriverAPI instance;

    public DriverAPI() {
        regenerateDriverList();
    }

    public Driver getDriver(String driverName) {
        for (Driver current : drivers) {
            if (current.getName().equalsIgnoreCase(driverName)) return current;
        }

        //Warning, program should never reach this point
        return null;
    }

    public Driver getDriver(int number) {
        for (Driver current : drivers) {
            if (current.getNumber() == number) return current;
        }

        //Warning, program should never reach this point
        return null;
    }

    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    public void regenerateDriverList() {

        drivers = new ArrayList<Driver>();
        try {
            URL url = new URL("https://api.openf1.org/v1/drivers?session_key=9472");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                String[] responses = output.replace("}", "").split("\\{");
                for (String cur : responses) {
                    String[] keyValuePairs = cur.split(",");
                    String name = "";
                    int number = 0;
                    Team newTeam = null;
                    for (String pair : keyValuePairs) {
                        String[] keyValue = pair.split(":");
                        if (keyValue.length != 2) continue;
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        switch (key) {
                            case "\"driver_number\"":
                                number = Integer.parseInt(value);
                                break;
                            case "\"full_name\"":
                                name = value.replace("\"", "");
                                break;

                            case "\"team_name\"":
                                newTeam = new Team( value.replace("\"", ""));
                                break;
                        }
                    }

                    Driver newDriver = new Driver(name, number, newTeam);
                    if (number >0 && number < 100) drivers.add(newDriver);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DriverAPI getInstance() {
        if (instance == null) instance = new DriverAPI();
        return instance;
    }

}
