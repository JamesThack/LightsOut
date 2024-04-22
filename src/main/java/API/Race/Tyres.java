package API.Race;

import API.Components.Request;
import API.Components.Session;
import APIObjects.RegexAssist;

import java.util.HashMap;
import java.util.TreeMap;

public class Tyres {


    private final HashMap<Integer, TreeMap<Integer, String>> tyres;
    private final Request request;
    private TreeMap<Integer, Integer> pastDriverPositions;


    public Tyres(Session session) {

        tyres = new HashMap<>();

        request = new Request("https://api.openf1.org/v1/stints", session.getSessionKey());

        for (String cur : request.getResponses()) {
            String[] keyValuePairs = cur.split(",");
            String compound = "";
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
                    case "\"compound\"":
                        compound = value.replace("\"", "");
                        break;
                    case "\"lap_start\"":
                        time =  Integer.valueOf(value);
                        break;
                    case "\"lap_end\"":
                        time =  Integer.valueOf(value);
                        break;
                }
            }

            if (number == 0) continue;
            if (  tyres.get(number) == null) {
                TreeMap<Integer, String> newPos = new TreeMap<>();
                newPos.put(time, compound);
                tyres.put(number, newPos);
            } else {
                tyres.get(number).put(time, compound);
            }
        }
    }

    public String getTyreAt(int driver, int lap) {
        String lastPos = "";
        if (tyres.get(driver) == null) return "MEDIUM";
        for (int cur : tyres.get(driver).keySet()) {
            if (lap >= cur || lastPos == "") {
                lastPos = tyres.get(driver).get(cur);
            } else {
                return lastPos;
            }
        }
        return lastPos;
    }

}
