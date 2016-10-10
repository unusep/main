package seedu.doerList.model.task;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import seedu.doerList.commons.exceptions.IllegalValueException;

import org.joda.time.DateTime;

/**
 * Represents an Task's time in the to-do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TodoTime {

    public final DateTime value;

    public static final String MESSAGE_TODOTIME_CONSTRAINTS = "Time should be in this format 'yyyy-MM-dd HH:mm'";
    public static final String TODOTIME_VALIDATION_REGEX = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d";
    public static final String TIME_STANDARD_FORMAT = "yyyy-MM-dd HH:mm";
    
    /**
    * Validates given rawTime.
    *
    * @throws IllegalValueException if given rawTime string is invalid.
    */
   public TodoTime(String rawTime) throws IllegalValueException {
       DateTimeFormatter formatter = DateTimeFormat.forPattern(TIME_STANDARD_FORMAT);
       try {
           value = DateTime.parse(rawTime, formatter); 
       } catch (IllegalArgumentException ire) {
           throw new IllegalValueException(MESSAGE_TODOTIME_CONSTRAINTS);
       }
   }

   /**
    * Returns true if a given string is a valid time.
    */
   public static boolean isValidTime(String test) {
       return test != null && test.matches(TODOTIME_VALIDATION_REGEX);
   }

   /**
    * getter method for value
    */
   public DateTime getTime() {
       return value;
   }


   @Override
   public String toString() {
       return value.toString();
   }

   @Override
   public boolean equals(Object other) {
       return other == this // short circuit if same object
               || (other instanceof TodoTime // instanceof handles nulls
               && this.value.isEqual(((TodoTime) other).value)); // state check
   }


   @Override
   public int hashCode() {
       return value.hashCode();
   }

}
