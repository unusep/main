//@@author A0139168W
package seedu.doerList.commons.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {

    public static LocalDateTime getStartOfDay(LocalDateTime date) {
        return date.with(LocalTime.MIN);
    }
    
    public static LocalDateTime getEndOfDay(LocalDateTime date) {
        return date.with(LocalTime.MAX);
    }
}
