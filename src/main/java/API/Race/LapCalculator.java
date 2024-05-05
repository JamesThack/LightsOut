package API.Race;

import API.Components.Request;
import APIObjects.RegexAssist;

import java.util.TreeMap;

public class LapCalculator {


    private TreeMap<Integer, Integer> laps;
    private static LapCalculator instance;
    private Request request;
    private String sessionKey;
    private int startTime;

    public LapCalculator(int startTime, String sessionKey) {

        this.sessionKey = sessionKey;
        this.startTime = startTime;
        refreshData();

    }

    public void refreshData() {

        laps = new TreeMap<>();
        request = new Request("https://api.openf1.org/v1/laps", sessionKey);

        laps.put(1, startTime);

        for (String cur : request.getResponses()) {
            String[] keyValuePairs = cur.split(",");
            int lapNumber = -1;
            int time = -1;
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length < 2) continue;
                String key = keyValue[0].trim();
                String value = keyValue[1].trim().replace("]", "");
                switch (key) {
                    case "\"date_start\"":
                        if (value.equals("null")) continue;
                        String timeRaw = pair.split("T")[1].replace("\"", "").split("\\+")[0];;
                        time = RegexAssist.convertToUnix(timeRaw);
                        break;
                    case "\"lap_number\"":
                        lapNumber = Integer.parseInt(value);
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
