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
    
    public static final String MESSAGE_RECURRING_CONSTRAINTS = "Time should be in this format 'yyyy-MM-dd HH:mm' or natural language such as 'daily', 'weekly'";
    public static final String TIME_STANDARD_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_YEARS = "20";
    public static final String DEFAULT_TIME = " 12:00";
    public static final String DAILY = "daily";
    public static final String WEEKLY = "weekly";
    public static final String MONTHLY = "monthly";
    public static final String YEARLY = "yearly"; 
    public static final String NO_RECURRING = "";

 
    /**
     * Stores given interval. Validation of interval is done by TimeInterval class.
     *
     * @throws IllegalValueException if given information string is invalid.
     */
    public Recurring(String unformattedTime) throws IllegalValueException {
        unformattedTime = unformattedTime.trim();
        
        /*
        if (unformattedTime.equals(NO_RECURRING) || unformattedTime == null){
            this.isRecurring = false;
        }
        */
        String languageTime = formattingNaturalLanguage(unformattedTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STANDARD_FORMAT);
        String time = new TimeParser().parse(languageTime);
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
        
        if (checker.equals(DAILY)){
            return DEFAULT_YEARS + "00-00-01" + DEFAULT_TIME;
        } else if (checker.equals(WEEKLY)){
            return DEFAULT_YEARS + "00-00-07" + DEFAULT_TIME;
        } else if (checker.equals(MONTHLY)){
            return DEFAULT_YEARS + "00-01-00" + DEFAULT_TIME;
        } else if (checker.equals(YEARLY)){
            return DEFAULT_YEARS + "01-00-00" + DEFAULT_TIME;
        } else { // if value doesn't fit any of the above natural language, return original output
            return DEFAULT_YEARS + input + DEFAULT_TIME;
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
