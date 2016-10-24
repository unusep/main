//@@author A0147978E
package seedu.doerList.logic;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.parser.TimeParser;
import seedu.doerList.model.task.TodoTime;

/** Test cases to validate the natural language time parser*/
public class TimeParserTest {
    private TimeParser parser;
    private DateTimeFormatter formatter;
    
    @Before
    public void setup() {
        parser = new TimeParser();
        formatter = DateTimeFormatter.ofPattern(TodoTime.TIME_STANDARD_FORMAT);
    }
    
    @Test
    public void parse_withExplicitDateTime_successful() throws IllegalValueException {
        LocalDateTime now = LocalDateTime.now();
        assertEquals(parser.parse(now.format(formatter)), now.format(formatter));
    }
    
    @Test
    public void parse_withImplicitTime_successful() throws IllegalValueException {
        assertEquals(parser.parse("next 20 minutes"), LocalDateTime.now().plusMinutes(20).format(formatter));
        assertEquals(parser.parse("next 2 hours"), LocalDateTime.now().plusHours(2).format(formatter));
    }
    
    @Test
    public void parse_withImplicitDate_successful() throws IllegalValueException {
        assertEquals(parser.parse("today"), LocalDateTime.now().format(formatter));
        assertEquals(parser.parse("tomorrow"), LocalDateTime.now().plusDays(1).format(formatter));
        assertEquals(parser.parse("next 2 days"), LocalDateTime.now().plusDays(2).format(formatter));
        assertEquals(parser.parse("next 2 months"), LocalDateTime.now().plusMonths(2).format(formatter));
        assertEquals(parser.parse("next week"), LocalDateTime.now().plusDays(7).format(formatter));
        assertEquals(parser.parse("next months"), LocalDateTime.now().plusMonths(1).format(formatter));
    }
    
    @Test
    public void parse_withTime_successful() throws IllegalValueException {
        assertEquals(parser.parse("today 5pm"), 
                LocalDateTime.now().withHour(17).withMinute(0).format(formatter));
        assertEquals(parser.parse("today 17:20"),
                LocalDateTime.now().withHour(17).withMinute(20).format(formatter));
        assertEquals(parser.parse("today 5:20pm"),
                LocalDateTime.now().withHour(17).withMinute(20).format(formatter));
    }
    
}
