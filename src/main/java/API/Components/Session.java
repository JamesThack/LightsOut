package API.Components;

public class Session {

    private String name;
    private int startTime;
    private String date;
    private String sessionKey;

    public Session(String name, int startTime, String date, String sessionKey) {
        this.name = name;
        this.startTime = startTime;
        this.date = date;
        this.sessionKey = sessionKey;
    }

    public String getName() {
        return name;
    }

    public int getStartTime() {
        return startTime;
    }

    public String getDate() {
        return date;
    }

    public String getSessionKey() {
        return sessionKey;
    }
}
