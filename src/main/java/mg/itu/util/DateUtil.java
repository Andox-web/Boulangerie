package mg.itu.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDateTime parse(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime parsedDate = LocalDateTime.parse(dateTime, formatter);
            return parsedDate;
        } catch (Exception e) {
            return null;
        }
    }
}
