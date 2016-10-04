package seedu.address.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Task's time interval in the to-do List with Start Time and End Time.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TimeInterval {

	public final LocalDateTime startTime;
	public final LocalDateTime endTime;

	public static final String MESSAGE_TIME_INTERVAL_CONSTRAINTS = "Start Time should be in this format 'yyyy-MM-dd HH:mm'";
	public static final String NAME_VALIDATION_REGEX = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d";

	/**
    * Validates given startTime.
    *
    * @throws IllegalValueException if given startTime string is invalid.
    */
   public TimeInterval(String startingTime, String endingTime) throws IllegalValueException {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
	   if ((startingTime == null) && !isValidTime(startingTime)) {
           throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
       }
       if (!isValidTime(endingTime)) {
           throw new IllegalValueException(MESSAGE_TIME_INTERVAL_CONSTRAINTS);
       }
       if(startingTime == null) {
    	       startTime = LocalDateTime.now();
       } else {
    	       startTime = LocalDateTime.parse(startingTime, formatter);
       }

       endTime = LocalDateTime.parse(endingTime, formatter);
   }
   
   /**
    * For quickly created time interval
    * 
    * @param startingTime
    * @param endTime
    */
   public TimeInterval(LocalDateTime startingTime, LocalDateTime endTime) {
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
   public LocalDateTime getStartTime() {
	   return this.startTime;
   }

   /**
    * getter method for endTime
    */
   public LocalDateTime getEndTime() {
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
       return startTime.hashCode() * 31 + endTime.hashCode();
   }

}
