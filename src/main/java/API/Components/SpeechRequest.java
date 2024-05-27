package API.Components;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * Speech Request, this is a request to make the narrator speak, is used in the Speech classs
 */
public class SpeechRequest {

    private int driver;
    private String message;
    private int delay;
    private int priority;

    public SpeechRequest(int driver, String message, int priority) {
        this.driver = driver;
        this.message = message;
        this.delay = 2;
        this.priority = priority;

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

    public int getPriority() {
        return priority;
    }
}
