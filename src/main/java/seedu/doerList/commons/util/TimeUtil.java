//@@author A0139168W
package seedu.doerList.commons.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {

    /*
     * Returns the specific date at time 00:00:00000000
     */
    public static LocalDateTime getStartOfDay(LocalDateTime date) {
        return date.with(LocalTime.MIN);
    }
    
    /*
     * Returns the specific date at time 23:59:99999999
     */
    public static LocalDateTime getEndOfDay(LocalDateTime date) {
        return date.with(LocalTime.MAX);
    }
}
