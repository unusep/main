package seedu.doerList.model.task;

import java.time.LocalDateTime;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import seedu.doerList.commons.exceptions.IllegalValueException;

import org.joda.time.DateTime;

/**
 * Represents an Task's time interval in the to-do List with Start Time and End Time.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TimeInterval {

	public final DateTime startTime;
	public final DateTime endTime;

	public static final String MESSAGE_TIME_INTERVAL_CONSTRAINTS = "Start Time should be in this format 'yyyy-MM-dd HH:mm'";
	public static final String NAME_VALIDATION_REGEX = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d";
	public static final String TIME_STANDARD_FORMAT = "yyyy-MM-dd HH:mm";
	
	/**
    * Validates given startTime.
    *
    * @throws IllegalValueException if given startTime string is invalid.
    */
   public TimeInterval(String startingTime, String endingTime) throws IllegalValueException {
       DateTimeFormatter formatter = DateTimeFormat.forPattern(TIME_STANDARD_FORMAT);
	   if ((startingTime == null) && !isValidTime(startingTime)) {
           throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
       }
       if (!isValidTime(endingTime)) {
           throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
       }
       if(startingTime == null) {
    	       startTime = DateTime.now();
       } else {
    	       startTime = DateTime.parse(startingTime, formatter);
       }

       endTime = DateTime.parse(endingTime, formatter);
   }
   
   /**
    * For quickly created time interval
    * 
    * @param startingTime
    * @param endTime
    */
   public TimeInterval(DateTime startingTime, DateTime endTime) {
       this.startTime = startingTime;
       this.endTime = endTime;
   }

   /**
    * Returns true if a given string is a valid task startTime.
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
       return startTime + "->" + endTime;
   }

   @Override
   public boolean equals(Object other) {
       return other == this // short circuit if same object
               || (other instanceof TimeInterval // instanceof handles nulls
               && this.startTime.isEqual(((TimeInterval) other).startTime) // state check
               && this.endTime.isEqual(((TimeInterval) other).endTime)); // state check
   }


   @Override
   public int hashCode() {
       return startTime.hashCode() * 31 + endTime.hashCode();
   }

}
