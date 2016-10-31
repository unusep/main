//@@author A0139401N
package seedu.doerList.model.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doerList.commons.exceptions.IllegalValueException;

/**
 * Represents an Task's recurring time in the to-do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Recurring {
    public final long day;
    public final long month;
    public final long year;
    public boolean isRecurring = true;

    public static final Pattern RECUR_TITLE_FORMAT = Pattern.compile("\\d{2}-\\d{2}-\\d{2}");
    public static final String MESSAGE_RECURRING_CONSTRAINTS = "Time should be in this format 'yy-mm-dd' or natural language such as 'daily', 'weekly'";
    public static final String DAILY = "daily";
    public static final String WEEKLY = "weekly";
    public static final String MONTHLY = "monthly";
    public static final String YEARLY = "yearly"; 

    /**
     * Stores given interval. Validation of interval is done by TimeInterval class.
     *
     * @throws IllegalValueException if given information string is invalid.
     */
    public Recurring(String unformattedTime) throws IllegalValueException {
        unformattedTime = unformattedTime.trim();
        final Matcher recurTitleMatcher = RECUR_TITLE_FORMAT.matcher(unformattedTime);
        long[] processedTime = {0, 0, 0};
        if (isNaturalLanguage(unformattedTime, processedTime)){
            this.year = processedTime[0];
            this.month = processedTime[1];
            this.day = processedTime[2];
        } else if (recurTitleMatcher.find()){
            String[] parts = unformattedTime.split("-");
            this.year = Long.parseLong(parts[0]);
            this.month = Long.parseLong(parts[1]);
            this.day = Long.parseLong(parts[2]);
        } else {
            throw new IllegalValueException(MESSAGE_RECURRING_CONSTRAINTS);
        }
    }
    

    /**
     * Checks the natural language and returns out the time equivalent.
     * Processes the input if it matches into an array;
     * Returns a boolean to indicate if it is a language or not.
     */
    public boolean isNaturalLanguage(String input, long[] processedTime ){
        String checker = input.toLowerCase(); // to make it case insensitive 
        if (checker.equals(DAILY)){
            processedTime[2] = 1;
            return true;
        } else if (checker.equals(WEEKLY)){
            processedTime[2] = 7;
            return true;
        } else if (checker.equals(MONTHLY)){
            processedTime[1] = 1;
            return true;
        } else if (checker.equals(YEARLY)){
            processedTime[0] = 1;
            return true;
        } else { // if value doesn't fit any of the above natural language, return false
            return false;
        }
    }
    
    
    /**
     * Generates the local year of the recurring time interval
     */
    public long getYears(){
        return this.year;
    }
    
    
    /**
     * Generates the local month of the recurring time interval
     */
    public long getMonths(){
        return this.month;
    }

    
    /**
     * Generates the local day of the recurring time interval
     */
    public long getDays(){
        return this.day;
    }
    

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoTime // instanceOf handles nulls
                        && this.toString().equals(((TodoTime) other).toString())); // state check
    }

}
