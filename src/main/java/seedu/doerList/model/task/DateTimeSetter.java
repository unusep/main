package seedu.doerList.model.task;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeSetter {

    public static LocalDateTime getStartOfDay(LocalDateTime date) {
        LocalDateTime startOfDay = date.with(LocalTime.MIN);
        return startOfDay;
    }
    
    public static LocalDateTime getEndOfDay(LocalDateTime date) {
        LocalDateTime endOfDay = date.with(LocalTime.MAX);
        return endOfDay;
    }
    
    public static LocalDateTime addXNumberOfDays(LocalDateTime date, long days) {
        return date.plusDays(days);
    }
}
