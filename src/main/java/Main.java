import API.DriverAPI;
import APIObjects.Driver;
import APIObjects.Race.DriverPositions;
import APIObjects.RegexAssist;

public class Main {

    public static void main(String[] args) {

        DriverPositions positions = new DriverPositions();

        positions.getAllDriverPos(81);

        positions.printDriverPositionsAt(RegexAssist.convertToUnix("05:00:00"));
    }

}
