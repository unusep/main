package seedu.doerList.logic.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.doerList.commons.exceptions.IllegalValueException;

public class TimeParser {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final String MESSAGE_NO_TIME_FOUND = "No time was found in the given String";

    public TimeParser() {}
    public static String parse(String s) throws IllegalValueException {
        try {
            Parser parser = new Parser();
            List<DateGroup> dateGroups = parser.parse(s);
            DateGroup group = dateGroups.get(0);
            List<Date> dateList = group.getDates();
            Date firstDate = dateList.get(0);

            return dateFormat.format(firstDate);
        } catch (IndexOutOfBoundsException ire) {
            throw new IllegalValueException(MESSAGE_NO_TIME_FOUND);
        }
    }
}
