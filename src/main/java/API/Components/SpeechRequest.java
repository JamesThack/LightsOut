package API.Components;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class SpeechRequest {

    private int driver;
    private String message;
    private int delay;

    public SpeechRequest(int driver, String message) {
        this.driver = driver;
        this.message = message;
        this.delay = 3;

    }

    public int getDriver() {
        return driver;
    }

    public String getMessage() {
        return message;
    }

    public int getDelay() {
        delay -=1;
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
