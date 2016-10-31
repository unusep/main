package seedu.doerList.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.parser.TimeParser;

/**
 * Represents an Task's time in the to-do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TodoTime {

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
   
   //@@author A0139401N
   /**
    * mutator method for value
    */
   public void setTime(int addition, String determinant){
       if (determinant.equals("daily") || determinant.equals("weekly")){
           this.value.plusDays(addition);
       } else if (determinant.equals("monthly")){
           this.value.plusMonths(addition);
       } else if (determinant.equals("yearly")){
           this.value.plusYears(addition);
       }
   }

   @Override
   public String toString() {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STANDARD_FORMAT);
       return value.format(formatter).toString();
   }

   @Override
   public boolean equals(Object other) {
       return other == this // short circuit if same object
               || (other instanceof TodoTime // instanceof handles nulls
               && this.toString().equals(((TodoTime) other).toString())); // state check
   }

}
