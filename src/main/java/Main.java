import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import API.AsyncTimer;
import API.DriverAPI;
import APIObjects.Driver;
import APIObjects.Race.DriverPositions;
import GUI.MainScreen;
import APIObjects.RegexAssist;

public class Main {

    public static void main(String[] args) {

        DriverPositions positions = new DriverPositions();

        positions.getAllDriverPos(22);

        positions.printDriverPositionsAt(RegexAssist.convertToUnix("06:25:00"));
        
        MainScreen window = new MainScreen(RegexAssist.convertToUnix("06:11:00"));
       
        AsyncTimer asyncHandler = new AsyncTimer(window);
        
        Timer timer = new Timer();
        
        timer.scheduleAtFixedRate(asyncHandler, 0, 1000);
      
    }
    
    
    	

}


