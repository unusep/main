package seedu.doerList.model.task;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.parser.TimeParser;

/**
 * Represents an Task's time in the to-do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TodoTime {
    public static final PrettyTime HumanReadableParser = new PrettyTime();
    

    public final LocalDateTime value;

    public static final String MESSAGE_TODOTIME_CONSTRAINTS = "Time should be in this format 'yyyy-MM-dd HH:mm' or natural language such as 'tomorrow', 'next week monday'";
    public static final String TIME_STANDARD_FORMAT = "yyyy-MM-dd HH:mm";

    /**
    * Validates given rawTime.
    *
    * @throws IllegalValueException if given rawTime string is invalid.
    */
   public TodoTime(String rawTime) throws IllegalValueException {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STANDARD_FORMAT);
       String time = new TimeParser().parse(rawTime);
       value = LocalDateTime.parse(time, formatter);
   }

   public TodoTime(LocalDateTime source) {
       value = source;
   }

   /**
    * getter method for value
    */
   public LocalDateTime getTime() {
       return value;
   }

   //@@author A0139168W
   @Override
   public String toString() {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STANDARD_FORMAT);
       return value.format(formatter).toString();
   }
   //@@author
   
   /**
    * Parse the time to human readable version
    * 
    * @return String
    */
   public String toHumanReadableTime() {
       return HumanReadableParser.format(Date.from(value.atZone(ZoneId.systemDefault()).toInstant()));
   }

   @Override
   public boolean equals(Object other) {
       return other == this // short circuit if same object
               || (other instanceof TodoTime // instanceof handles nulls
               && this.toString().equals(((TodoTime) other).toString())); // state check
   }

}
