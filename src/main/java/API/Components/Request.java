package API.Components;

import APIObjects.RegexAssist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeMap;

public class Request {

    private String[] responses;

    public Request(String request, String sessionKey) {
        generateElements(request + "&session_key=" + sessionKey);
    }

    public Request(String request) {
        generateElements(request);
    }

    public Request(String request, String sessionKey, String meetingKey) {
        generateElements(request + "&session_key= " + sessionKey + "&meeting_key=" + meetingKey);
    }

    public String[] getResponses() {
        return responses;
    }

    private void generateElements(String request) {
        try {
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            responses = br.readLine().replace("}", "").split("\\{");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
