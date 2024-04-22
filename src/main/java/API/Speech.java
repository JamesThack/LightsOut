package API;

import API.Components.SpeechRequest;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class Speech {

    private Voice narrator;
    private TreeMap<Integer, SpeechRequest> cachedSpeech;
    private boolean isSpeaking;

    public Speech() {
        this.cachedSpeech = new TreeMap<>();
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        narrator = VoiceManager.getInstance().getVoice("kevin16");
        narrator.allocate();
        isSpeaking = false;
    }

    public void checkSpeech() {
        for (SpeechRequest cur : cachedSpeech.values()) {
            if (cur.getDelay() <= 0 && !isSpeaking) speak(cur);
        }
    }

    public void addSpeech(SpeechRequest speech) {
        cachedSpeech.put(speech.getDriver(), speech);

    }

    private int getHighestPriorityItem() {
        int highest = 100;
        for (SpeechRequest cur : cachedSpeech.values()) {
            if (cur.getPriority() < highest) highest = cur.getPriority();
        }
        return highest;
    }

    private void speak(SpeechRequest speech) {
        Thread speechThread = new Thread(new Runnable() {
            public void run() {
                if (getHighestPriorityItem() >= speech.getPriority()) {
                    isSpeaking = true;
                    narrator.speak(speech.getMessage());
                    cachedSpeech.remove(speech.getDriver());
                    isSpeaking = false;
                }
            }
        });
        speechThread.start();
    }

    public void speakText(String speech) {
        Thread speechThread = new Thread(new Runnable() {
            public void run() {
                System.out.println(speech);
                narrator.speak(speech);
            }
        });
        speechThread.start();
    }

    public void clearCache() {
        cachedSpeech.clear();
    }
}
