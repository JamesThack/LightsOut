package API.Race;

import API.DriverAPI;
import APIObjects.Driver;
import APIObjects.RegexAssist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class DriverPositions {
	
    private HashMap<Integer, TreeMap<Integer, Integer>> positions;
    private static DriverPositions instance;
    
    public static DriverPositions getInstance() {
    	if (instance == null) instance = new DriverPositions();
    	return instance;
    }

    public DriverPositions() {

        positions = new HashMap<>();

        try {
            URL url = new URL("https://api.openf1.org/v1/position?meeting_key=latest&session_key=latest");
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
                    int position = 0;
                    int time = 0;
                    int number = 0;
                    for (String pair : keyValuePairs) {
                        String[] keyValue = pair.split(":");
                        if (keyValue.length < 2)continue;
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim().replace("]", "");
                        switch (key) {
                            case "\"driver_number\"":
                                number = Integer.parseInt(value);
                                break;
                            case "\"position\"":
                                position = Integer.parseInt(value);
                                break;
                            case "\"date\"":
                                String timeRaw = pair.split("T")[1].replace("\"", "");
                                time = RegexAssist.convertToUnix(timeRaw);


                                break;
                        }
                    }

                    if (positions.get(number) == null) {
                        TreeMap<Integer, Integer> newPos = new TreeMap<>();
                        newPos.put(time, position);
                        positions.put(number, newPos);
                    } else {
                        positions.get(number).put(time, position);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void getAllDriverPos(int driverNumber) {
        for (int cur : positions.get(driverNumber).keySet()) {
            System.out.println(RegexAssist.convertToTimeString( cur )+ " , " +  positions.get(driverNumber).get(cur));
        }
    }

    public int getDriverPosAt(int driver, int time) {
        int lastPos = 0;
        for (int cur : positions.get(driver).keySet()) {
            if (time >= cur || lastPos == 0) {
                lastPos = positions.get(driver).get(cur);
            } else {
                return lastPos;
            }
        }
        return lastPos;
    }

    public TreeMap<Integer, Integer> getAllDriverPositions(int time) {

        TreeMap<Integer, Integer> driverPos = new TreeMap<>();

        for (Driver driver : DriverAPI.getInstance().getDrivers()) {
            int driverNumber = driver.getNumber();
            driverPos.put(getDriverPosAt(driverNumber, time), driverNumber);
        }

        return driverPos;
    }

    public void printDriverPositionsAt(int time) {
        TreeMap<Integer, Integer> timings = getAllDriverPositions(time);

        for (int cur : timings.keySet()) {
            System.out.println(DriverAPI.getInstance().getDriver(timings.get(cur)).getName() + " is in position " + cur);
        }
    }
    
    public ArrayList<Driver> getDriversInOrder(int time) {
    	ArrayList<Driver> order = new ArrayList<Driver>();
    	TreeMap<Integer, Integer> timings = getAllDriverPositions(time);
    	for (int cur : timings.keySet()) {
            order.add(DriverAPI.getInstance().getDriver(timings.get(cur)));
        }
    	
    	return order;
    }



}
