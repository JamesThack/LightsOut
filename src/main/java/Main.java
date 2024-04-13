import java.util.Timer;

import API.AsyncTimer;
import GUI.MainScreen;
import APIObjects.RegexAssist;

public class Main {

    public static void main(String[] args) {
        
        MainScreen window = MainScreen.getInstance();
        AsyncTimer asyncHandler = new AsyncTimer(window);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(asyncHandler, 0, 1000);
      
    }
    
    
    	

}


