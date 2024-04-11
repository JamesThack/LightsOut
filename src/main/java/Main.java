import java.util.Timer;

import API.AsyncTimer;
import APIObjects.Race.DriverPositions;
import APIObjects.Race.LapCalculator;
import GUI.MainScreen;
import APIObjects.RegexAssist;
import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        
        MainScreen window = new MainScreen(RegexAssist.convertToUnix("06:11:00"));
        AsyncTimer asyncHandler = new AsyncTimer(window);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(asyncHandler, 0, 1000);
      
    }
    
    
    	

}


