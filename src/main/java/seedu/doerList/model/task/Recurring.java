//@@author A0139401N
package seedu.doerList.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.parser.TimeParser;

/**
 * Represents an Task's recurring time in the to-do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Recurring {
    public final LocalDateTime value;
    public boolean isRecurring = true;
    
    public static final String MESSAGE_RECURRING_CONSTRAINTS = "Time should be in this format 'yy-MM-dd' or natural language such as 'daily', 'weekly'";
    public static final String TIME_STANDARD_FORMAT = "yy-MM-dd";
    public static final String DAYS = "daily";
    public static final String WEEKS = "weekly";
    public static final String MONTHS = "monthly";
    public static final String YEARS = "yearly"; 
 
    /**
     * Stores given interval. Validation of interval is done by TimeInterval class.
     *
     * @throws IllegalValueException if given information string is invalid.
     */
    public Recurring(String recurring) throws IllegalValueException {
        recurring = recurring.trim();
        recurring = formattingNaturalLanguage(recurring);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STANDARD_FORMAT);
        String time = new TimeParser().parse(recurring);
        this.value = LocalDateTime.parse(time, formatter);
    }
    
    
    /**
     * Generates the local date of the recurring time interval
     */
    public LocalDateTime getValue(){
        return this.value;
    }
    
    
    /**
     * Checks the natural language and returns out the time equivalent
     * 
     * @throws IllegalValueException if given input string is invalid.
     * 
     * @param Original input in natural language (Time in English)
     * @return Time in "yy-MM-dd" format
     * 
     */
    public String formattingNaturalLanguage(String input) throws IllegalValueException{
        String checker = input.toLowerCase(); // to make it case insensitive 
        
        if (checker.equals(DAYS)){
            return "00-00-01";
        } else if (checker.equals(WEEKS)){
            return "00-00-07";
        } else if (checker.equals(MONTHS)){
            return "00-01-00";
        } else if (checker.equals(YEARS)){
            return "01-00-00";
        } else {
            return input;
        }
    }
    
    // TODO: ascertain this before adding/removing it
    /**
     * mutator method for value
     */
    public void setRecurring(int addition, String determinant){
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
        if(!isRecurring){
            return NO_RECURRING;
        }else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STANDARD_FORMAT);
            return this.value.format(formatter).toString();
        }
    }

    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoTime // instanceof handles nulls
                && this.toString().equals(((TodoTime) other).toString())); // state check
    }

}
