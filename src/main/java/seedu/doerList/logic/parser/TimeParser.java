package seedu.doerList.logic.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.task.TodoTime;

public class TimeParser {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public TimeParser() {}
    
    public String parse(String s) throws IllegalValueException {
        try {
            Parser parser = new Parser();
            List<DateGroup> dateGroups = parser.parse(s);
            DateGroup group = dateGroups.get(dateGroups.size() - 1);
            List<Date> dateList = group.getDates();
            Date lastDate = dateList.get(dateList.size() - 1);

            return dateFormat.format(lastDate);
        } catch (IndexOutOfBoundsException ire) {
            throw new IllegalValueException(TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
        }
    }
}
