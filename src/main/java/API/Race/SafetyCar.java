package API.Race;

import API.Components.Request;
import APIObjects.RegexAssist;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.TreeMap;

public class SafetyCar {

    private final TreeMap<Integer, String> safety;
    private final Request request;
    private TreeMap<Integer, Integer> pastDriverPositions;

    public SafetyCar(String sessionKey) {

        safety = new TreeMap<>();
        request = new Request("https://api.openf1.org/v1/race_control?category=SafetyCar&session_key=" + sessionKey);

        for (String cur : request.getResponses()) {
            String[] keyValuePairs = cur.split(",");
            String car = "";
            int time = -1;
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length < 2) continue;
                String key = keyValue[0].trim();
                String value = keyValue[1].trim().replace("]", "");
                switch (key) {
                    case "\"date\"":
                        if (value.equals("null")) continue;
                        String timeRaw = pair.split("T")[1].replace("\"", "");
                        time = RegexAssist.convertToUnix(timeRaw);
                        break;
                    case "\"message\"":
                        car = value.replace("\'", "");
                        break;
                }
            }
            if (car== "" || time == -1) continue;
            safety.put(time, car);
        }
    }

    public boolean isSafetyCar(int time) {
        if (safety.isEmpty()) return false;

        boolean isSafetyCar = false;
        for (int cur : safety.keySet()) {
            if (cur >= time) {
                return isSafetyCar;
            }
           if (safety.get(cur).contains("DEPLOYED")) {
               isSafetyCar = true;
           } else {
               isSafetyCar = false;
           }
        }

        return isSafetyCar;
    }

}
