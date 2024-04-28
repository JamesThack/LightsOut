package API.Race;

import API.Components.Request;
import API.Components.Session;
import APIObjects.RegexAssist;

import java.util.HashMap;
import java.util.TreeMap;

public class Flags {


    private final TreeMap<Integer, String> flags;
    private final Request request;
    private TreeMap<Integer, Integer> pastDriverPositions;

    public Flags(String sessionKey) {

        flags = new TreeMap<>();
        request = new Request("https://api.openf1.org/v1/race_control?category=Flag&scope=Track&session_key=" + sessionKey);

        for (String cur : request.getResponses()) {
            String[] keyValuePairs = cur.split(",");
            String flag = "";
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
                    case "\"flag\"":
                        flag = value.replace("\'", "");
                        break;
                }
            }
            if (flag== "" || time == -1) continue;
            flags.put(time, flag);
        }
        for (int time : flags.keySet()) {
            System.out.println(RegexAssist.convertToTimeString(time) + " " + flags.get(time));
        }


    }

    public boolean isRedFlag(int time) {
        if (flags.isEmpty()) return false;

        boolean isSafetyCar = false;
        for (int cur : flags.keySet()) {
            if (cur >= time) {
                return isSafetyCar;
            }
            if (flags.get(cur).contains("RED")) {
                isSafetyCar = true;
            } else {
                isSafetyCar = false;
            }
        }

        return isSafetyCar;
    }
}

