package API.Race;

import API.AccountHandler;
import API.Components.Request;
import API.Components.Session;
import API.Components.SpeechRequest;
import API.DriverAPI;
import APIObjects.Driver;
import APIObjects.RegexAssist;
import GUI.MainScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class DriverPositions {

    private  HashMap<Integer, TreeMap<Integer, Integer>> positions;
    private static DriverPositions instance;
    private Request request;
    private TreeMap<Integer, Integer> pastDriverPositions;
    private Session session;


    public DriverPositions(Session session) {

        this.session = session;
        refreshData();
    }

    public void refreshData() {
        positions = new HashMap<>();

        request = new Request("https://api.openf1.org/v1/position", session.getSessionKey());

        for (String cur : request.getResponses()) {
            String[] keyValuePairs = cur.split(",");
            int position = 0;
            int time = 0;
            int number = 0;
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length < 2) continue;
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
                        String timeRaw = pair.split("T")[1].replace("\"", "").split("\\+")[0];
                        time = RegexAssist.convertToUnix(timeRaw);
                        break;
                }
            }

            if (number == 0) continue;
            if (  positions.get(number) == null) {
                TreeMap<Integer, Integer> newPos = new TreeMap<>();
                newPos.put(time, position);
                positions.put(number, newPos);
            } else {
                positions.get(number).put(time, position);
            }
        }
    }

    public void getAllDriverPos(int driverNumber) {
        for (int cur : positions.get(driverNumber).keySet()) {
            System.out.println(RegexAssist.convertToTimeString(cur) + " , " + positions.get(driverNumber).get(cur));
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

        for (int cur : positions.keySet()) {
            Driver driver = DriverAPI.getInstance().getDriver(cur);
            driverPos.put(getDriverPosAt(cur, time), cur);
        }
        if (pastDriverPositions == null) {
            pastDriverPositions = driverPos;
            return driverPos;
        }

        try {
            for (int cur : driverPos.keySet()) {
                int newDriver = driverPos.get(cur);
                if (newDriver != pastDriverPositions.get(cur)) {
                    Driver driver = DriverAPI.getInstance().getDriver(newDriver);
                    SpeechRequest request = new SpeechRequest(newDriver, driver.getName() + " " + AccountHandler.getInstance().getSpeech("newposition") + " " + +cur, cur);
                    if (AccountHandler.getInstance().getOption("overtakenarrate") && AccountHandler.getInstance().getDriverNarrate(driver.getNumber()))
                        MainScreen.getInstance().getSpeechHandler().addSpeech(request);
                }
            }
        } catch (Exception e) {

        }
        pastDriverPositions = driverPos;
        return driverPos;
    }

    public void clearCachedDrivers() {
        pastDriverPositions = null;
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
