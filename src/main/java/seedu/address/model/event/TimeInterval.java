package seedu.address.model.event;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Event's time interval in the to-do List with Start Time and End Time.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TimeInterval {

	private final DateTime startTime;
	private final DateTime endTime;

	public static final String MESSAGE_NAME_CONSTRAINTS = "Start Time should be in this format 'yyyy-MM-dd HH:mm'";
	public static final String NAME_VALIDATION_REGEX = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d";

	/**
    * Validates given startTime.
    *
    * @throws IllegalValueException if given startTime string is invalid.
    */
   public TimeInterval(String startingTime, String endingTime) throws IllegalValueException {
	   DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

	   if ((startingTime == null) && !isValidTime(startingTime)) {
           throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
       }
       if (!isValidTime(endingTime)) {
           throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
       }
       if(startingTime == null) {
    	   startTime = new DateTime();
       } else {
    	   startTime = formatter.parseDateTime(startingTime);
       }

       endTime = formatter.parseDateTime(endingTime);
   }

   /**
    * Returns true if a given string is a valid event startTime.
    */
   public static boolean isValidTime(String test) {
       return test.matches(NAME_VALIDATION_REGEX);
   }

   /**
    * getter method for startTime
    */
   public DateTime getStartTime() {
	   return this.startTime;
   }

   /**
    * getter method for endTime
    */
   public DateTime getEndTime() {
	   return this.endTime;
   }


   @Override
   public String toString() {
       return startTime + " -> " + endTime;
   }

   @Override
   public boolean equals(Object other) {
       return other == this // short circuit if same object
               || (other instanceof TimeInterval // instanceof handles nulls
               && this.startTime.equals(((TimeInterval) other).startTime)); // state check
   }


   @Override
   public int hashCode() {
       return startTime.hashCode();
   }

}
