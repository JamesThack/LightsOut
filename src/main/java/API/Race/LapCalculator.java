package API.Race;

import APIObjects.RegexAssist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

public class LapCalculator {


    private TreeMap<Integer, Integer> laps;
    private static LapCalculator instance;

    public static LapCalculator getInstance() {
        if (instance == null) instance = new LapCalculator();
        return instance;
    }

    public LapCalculator() {

        laps = new TreeMap<>();

        try {
            URL url = new URL("https://api.openf1.org/v1/laps?session_key=latest");
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
                    int lapNumber = -1;
                    int time = -1;
                    for (String pair : keyValuePairs) {
                        String[] keyValue = pair.split(":");
                        if (keyValue.length < 2)continue;
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim().replace("]", "");
                        switch (key) {
                            case "\"date_start\"":
                                if (value.equals("null")) continue;
                                String timeRaw = pair.split("T")[1].replace("\"", "");
                                time = RegexAssist.convertToUnix(timeRaw);
                                break;
                            case "\"lap_number\"":
                                lapNumber = Integer.parseInt(value) - 1;
                                break;
                        }
                    }
                    if (lapNumber == -1 || time == -1) continue;
                    if (laps.get(lapNumber) == null) {
                        laps.put(lapNumber, time);
                    } else {
                        if (laps.get(lapNumber) >= time) laps.put(lapNumber, time);
                    }
                }
            }

            for (int cur : laps.keySet()) {
                System.out.println("Lap " + cur + " is set at " + RegexAssist.convertToTimeString(laps.get(cur)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public int getLapFromTime(int time) {

        int prevLap = 1;
        for (int cur : laps.keySet()) {
            int curTime = laps.get(cur);
            if (curTime >= time) {
                return prevLap;
            } else {
                prevLap = cur;
            }
        }
        return prevLap;
    }

    public int getTimeFromLap(int lap) {
        try {
            return laps.get(lap);
        } catch (Exception e) {
            return laps.get(1);
        }
    }

}
