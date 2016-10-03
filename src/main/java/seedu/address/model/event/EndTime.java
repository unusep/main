package seedu.address.model.event;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Event's EndTime in the to-do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EndTime {

	public static final String MESSAGE_NAME_CONSTRAINTS = "End Time should be in this format 'yyyy-MM-dd HH:mm'";
	public static final String NAME_VALIDATION_REGEX = "\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d";

	public final String endTime;

	/**
     * Validates given endTime.
     *
     * @throws IllegalValueException if given endTime string is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        assert endTime != null;
        endTime = endTime.trim();
        if (!isValidEndTime(endTime)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.endTime = endTime;
    }

    /**
     * Returns true if a given string is a valid event endTime.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Store the endTime string as a DateTime Object for possible future use
     * If it is not needed, ignore it
     */

    public static DateTime EndingTime(String endTime) {
    	DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    	DateTime dt = formatter.parseDateTime(endTime);
    	return dt;
    }

    @Override
    public String toString() {
        return endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }

}
