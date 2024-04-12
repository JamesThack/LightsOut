import java.util.Timer;

import API.AsyncTimer;
import GUI.MainScreen;
import APIObjects.RegexAssist;

public class Main {

    public static void main(String[] args) {
        
        MainScreen window = new MainScreen(RegexAssist.convertToUnix("06:11:00"));
        AsyncTimer asyncHandler = new AsyncTimer(window);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(asyncHandler, 0, 1000);
      
    }
    
    
    	

}


