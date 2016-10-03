package seedu.address.model.event;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Event's StartTime in the to-do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class StartTime {

	public static final String MESSAGE_NAME_CONSTRAINTS = "Start Time should be in this format 'yyyy-MM-dd HH:mm'";
	public static final String NAME_VALIDATION_REGEX = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d";

	public final String startTime;

	/**
     * Validates given startTime.
     *
     * @throws IllegalValueException if given startTime string is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        assert startTime != null;
        startTime = startTime.trim();
        if (!isValidStartTime(startTime)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.startTime = startTime;
    }

    /**
     * Returns true if a given string is a valid event startTime.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Store the startTime string as a DateTime Object for possible future use
     * If it is not needed, ignore it
     */

    public static DateTime StartingTime(String startTime) {
    	DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    	DateTime dt = formatter.parseDateTime(startTime);
    	return dt;
    }

    @Override
    public String toString() {
        return startTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }

}
