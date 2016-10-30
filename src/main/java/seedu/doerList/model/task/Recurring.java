//@@author A0139401N
package seedu.doerList.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Time;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.parser.TimeParser;

/**
 * Represents an Task's recurring time in the to-do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Recurring {
    public final LocalDateTime value;
    public boolean isRecurring = true;
    private String timeType;
    
    public static final String MESSAGE_RECURRING_CONSTRAINTS = "Time should be in this format 'yyyy-MM-dd' or natural language such as 'daily', 'weekly'";
    public static final String TIME_STANDARD_FORMAT = "yy-MM-dd";
    public static final String DAYS = "daily";
    public static final String WEEKS = "weekly";
    public static final String MONTHS = "monthly";
    public static final String YEARS = "yearly"; // subtract 2000
    public static final String NO_RECURRING = "";
    public static final String DEFAULT_TIME_INTERVAL = "01-01-01";
 
    /**
     * Stores given interval. Validation of interval is done by TimeInterval class.
     *
     * @throws IllegalValueException if given information string is invalid.
     */
    public Recurring(String recurring) throws IllegalValueException {
        assert recurring != null;
        recurring = recurring.trim();

        if(recurring.equals(NO_RECURRING)){
            isRecurring = false;
            recurring = DEFAULT_TIME_INTERVAL;
        }
        
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STANDARD_FORMAT);
        String time = new TimeParser().parse(recurring);
        this.value = LocalDateTime.parse(time, formatter);
    }
    
    /**
     * Generates the local date of the reccurance time interval
     */
    public LocalDateTime getValue(){
        return this.value;
    }
    
    /**
     * Generates the local type of time of the reccurance time interval
     */
    public String getTimeType(){
        return this.timeType;
    }
    
    /**
     * Checks the natural language and returns out the hours equivalent
     */
    public long formattingNaturalLanguage(String input){
        String checker = input.toLowerCase();
        
        if (checker.equals(DAYS)){
            this.timeType = DAYS;
            return 24; 
        } else if (checker.equals(WEEKS)){
            this.timeType = DAYS;
            return 24; 
        } else if (checker.equals(MONTHS)){
            this.timeType = DAYS;
            return 24; 
        } else if (checker.equals(YEARS)){
            this.timeType = DAYS;
            return 24; 
        } else {
            return -1;
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
