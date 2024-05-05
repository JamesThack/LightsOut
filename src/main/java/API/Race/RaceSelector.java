package API.Race;

import API.Components.Request;
import API.Components.Session;
import API.DriverAPI;
import APIObjects.Driver;
import APIObjects.RegexAssist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class RaceSelector {

    private final Request request;
    private final HashMap<Integer, HashMap<String, Session>> sessions;

    public RaceSelector() {

        sessions = new HashMap<>();
        request = new Request("https://api.openf1.org/v1/sessions?session_name=Race");

        for (String cur : request.getResponses()) {
            String[] keyValuePairs = cur.split(",");
            String name = "";
            int startTime = 0;
            String date = "";
            String sessionKey = "";
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length < 2) continue;
                String key = keyValue[0].trim();
                String value = keyValue[1].trim().replace("]", "");
                switch (key) {
                    case "\"country_name\"":
                        name = value.replace("\"", "");
                        break;
                    case "\"session_key\"":
                        sessionKey = value.replace("\"", "");
                        break;
                    case "\"date_start\"":
                        String timeRaw = pair.split("T")[1].replace("\"", "").split("\\+")[0];
                        startTime = RegexAssist.convertToUnix(timeRaw);
                        date = pair.split("T")[0].replace("date_start", "").replace("\"", "").replace(":", "");
                        break;
                }
            }
            if (name == "") continue;
            int year = Integer.valueOf(date.split("-")[0]);
            if (sessions.get(year) == null) {
                sessions.put(year, new HashMap<>());
            }
            sessions.get(year).put(sessionKey, new Session(name, startTime, date, sessionKey));
        }
    }

    public static Session getLiveSession() {
        Request req = new Request("https://api.openf1.org/v1/sessions?session_key=latest");

        for (String cur : req.getResponses()) {
            String[] keyValuePairs = cur.split(",");
            String name = "";
            int startTime = 0;
            String date = "";
            String sessionKey = "";
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length < 2) continue;
                String key = keyValue[0].trim();
                String value = keyValue[1].trim().replace("]", "");
                switch (key) {
                    case "\"country_name\"":
                        name = value.replace("\"", "");
                        break;
                    case "\"session_key\"":
                        sessionKey = value.replace("\"", "");
                        break;
                    case "\"date_start\"":
                        String timeRaw = pair.split("T")[1].replace("\"", "").split("\\+")[0];
                        startTime = RegexAssist.convertToUnix(timeRaw);
                        date = pair.split("T")[0].replace("date_start", "").replace("\"", "").replace(":", "");
                        break;
                }
            }
            if (name == "") continue;
            return new Session(name, startTime, date, sessionKey);
        }

        return null;
    }

    public HashMap<String, Session> getAllSessionsInYear(int year) {
        return sessions.get(year);
    }

    public ArrayList<Session> getAllSessions() {
        ArrayList<Session> allSessions = new ArrayList<Session>();
        for (int cur : sessions.keySet()) {
            allSessions.addAll(sessions.get(cur).values());
        }
        return allSessions;
    }


}
