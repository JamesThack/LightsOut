import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;

import API.AccountHandler;
import API.AsyncTimer;
import API.Components.Session;
import API.Race.RaceSelector;
import GUI.MainScreen;
import APIObjects.RegexAssist;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class Main {

    public static void main(String[] args) {


        AccountHandler.getInstance();

        MainScreen window = MainScreen.getInstance();
        AsyncTimer asyncHandler = new AsyncTimer(window);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(asyncHandler, 0, 1000);
      
    }
    
    
    	

}


