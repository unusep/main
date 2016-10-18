package seedu.doerList.logic;

import static org.junit.Assert.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.parser.TimeParser;
import seedu.doerList.model.task.TodoTime;

public class TimeParserTest {
    private TimeParser parser;
    private DateTimeFormatter formatter;
    
    @Before
    public void setup() {
        parser = new TimeParser();
        formatter = DateTimeFormat.forPattern(TodoTime.TIME_STANDARD_FORMAT);
    }
    
    public void parse_withExplicitDateTime_successful() {
        DateTime now = new DateTime();
        assertEquals(now.toString(formatter), now.toString(formatter));
    }
    
    @Test
    public void parse_withImplicitDate_successful() throws IllegalValueException {
        assertEquals(parser.parse("today"), new DateTime().toString(formatter));
        assertEquals(parser.parse("tomorrow"), new DateTime().plusDays(1).toString(formatter));
    }
    
    @Test
    public void parse_withTime_successful() throws IllegalValueException {
        assertEquals(parser.parse("today 5pm"), 
                new DateTime().withHourOfDay(17).withMinuteOfHour(0).toString(formatter));
        assertEquals(parser.parse("today 17:20"),
                new DateTime().withHourOfDay(17).withMinuteOfHour(20).toString(formatter));
        assertEquals(parser.parse("today 5:20pm"),
                new DateTime().withHourOfDay(17).withMinuteOfHour(20).toString(formatter));
    }
    
    @Test
    public void parse_withImplicitTime_successful() throws IllegalValueException {
        assertEquals(parser.parse("next 2 hours"), new DateTime().plusHours(2).toString(formatter));
        assertEquals(parser.parse("next 2 days"), new DateTime().plusDays(2).toString(formatter));
        assertEquals(parser.parse("next 2 months"), new DateTime().plusMonths(2).toString(formatter));
        assertEquals(parser.parse("next week"), new DateTime().plusDays(7).toString(formatter));
        assertEquals(parser.parse("next months"), new DateTime().plusMonths(1).toString(formatter));
    }
}
