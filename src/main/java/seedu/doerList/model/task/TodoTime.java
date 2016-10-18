package seedu.doerList.model.task;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.parser.TimeParser;

import org.joda.time.DateTime;

/**
 * Represents an Task's time in the to-do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TodoTime {

    public final DateTime value;

    public static final String MESSAGE_TODOTIME_CONSTRAINTS = "Time should be in this format 'yyyy-MM-dd HH:mm' or natural language such as 'tomorrow', 'next week monday'";
    public static final String TIME_STANDARD_FORMAT = "yyyy-MM-dd HH:mm";

    /**
    * Validates given rawTime.
    *
    * @throws IllegalValueException if given rawTime string is invalid.
    */
   public TodoTime(String rawTime) throws IllegalValueException {
       DateTimeFormatter formatter = DateTimeFormat.forPattern(TIME_STANDARD_FORMAT);
       String time = new TimeParser().parse(rawTime);
       value = DateTime.parse(time, formatter);
   }

   public TodoTime(DateTime source) {
       value = source;
   }

   /**
    * getter method for value
    */
   public DateTime getTime() {
       return value;
   }

   /**
    * Returns true if a given time is before the deadline
    */
   public boolean isBefore(TodoTime deadline) {
       return this.value.isBefore(deadline.value);
   }

   @Override
   public String toString() {
       return value.toString(DateTimeFormat.forPattern(TIME_STANDARD_FORMAT));
   }

   @Override
   public boolean equals(Object other) {
       return other == this // short circuit if same object
               || (other instanceof TodoTime // instanceof handles nulls
               && this.toString().equals(((TodoTime) other).toString())); // state check
   }


   @Override
   public int hashCode() {
       return value.hashCode();
   }

}
