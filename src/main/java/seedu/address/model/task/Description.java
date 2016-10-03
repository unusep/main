package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Task's description in the Do-erlist.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Task descriptions can be in any format";
    public static final String DESCRIPTION_VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Description(String address) throws IllegalValueException {
        assert address != null;
        if (!isValidDescription(address)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.value = address;
    }

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
