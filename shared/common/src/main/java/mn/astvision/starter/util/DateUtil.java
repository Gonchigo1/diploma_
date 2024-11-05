package mn.astvision.starter.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author digz6666
 */
public class DateUtil {
    public static LocalDateTime toLocalDate(Date in) {
        return LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDate(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    public static long calculateWeekends(LocalDateTime start, LocalDateTime end) {
        long weekends = 0;
        LocalDateTime current = start;

        while (current.isBefore(end)) {
            if (current.getDayOfWeek() == DayOfWeek.SATURDAY || current.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekends++;
            }
            current = current.plusDays(1);
        }

        return weekends;
    }
}
