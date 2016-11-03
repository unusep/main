//@@author A0147978E
package seedu.doerList.logic.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    public static final DateTimeFormatter patternWithYear = DateTimeFormatter.ofPattern("Y-M-d HH:mm");
    public static final DateTimeFormatter patternWithoutYear = DateTimeFormatter.ofPattern("M-d HH:mm");
    public static final DateTimeFormatter patternHourMinute = DateTimeFormatter.ofPattern("HH:mm");

    public String toHumanReadableTime(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getYear() != time.getYear()) { // not the same year
            return time.format(patternWithYear);
        }
        // today
        if (now.getDayOfYear() - time.getDayOfYear() == 0) {
            return "Today " + time.format(patternHourMinute);
        } 
        // tomorrow
        if (now.getDayOfYear() - time.getDayOfYear() == -1) {
            return "Tomorrow " + time.format(patternHourMinute);
        }
        // yesterday
        if (now.getDayOfYear() - time.getDayOfYear() == 1) {
            return "Yesterday " + time.format(patternHourMinute);
        }
        return time.format(patternWithoutYear);
    }
}
