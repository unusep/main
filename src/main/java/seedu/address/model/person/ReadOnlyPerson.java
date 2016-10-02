package seedu.address.model.person;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Name getName();
    Description getDescription();
    StartTime getStartTime();
    EndTime getEndTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndtime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" StartTime: ")
                .append(getStartTime())
                .append(" EndTime: ")
                .append(getEndTime())
                .append(" Category: ")
                .append(getCategory: ");
        return builder.toString();
    }
}
