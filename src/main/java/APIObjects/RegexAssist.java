package APIObjects;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class RegexAssist {

    public static int convertToUnix(String time) {
        String[] components = time.split(":"); // Split by colon

        int hours = Integer.parseInt(components[0]);
        int minutes = Integer.parseInt(components[1]);
        double seconds = Double.parseDouble(components[2]); // Include milliseconds
        long totalMilliseconds = (long) (((long) hours * 60 * 60 + minutes * 60L + seconds) * 1000);

        return (int) totalMilliseconds / 1000;
    }

    public static String convertToTimeString(int epoch) {
        Instant instant = Instant.ofEpochSecond(epoch);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return(zonedDateTime.format(formatter));
    }

    public static GridBagConstraints generateConstraints(int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        return constraints;
    }

    public static  GridBagConstraints generateConstraints(int x, int y, Insets insets) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.insets = insets;
        return constraints;
    }

}
