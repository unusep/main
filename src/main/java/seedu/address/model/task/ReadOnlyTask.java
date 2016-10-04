package seedu.address.model.task;

import seedu.address.model.category.UniqueCategoryList;

/**
 * A read-only immutable interface for an Task in the to-do list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();
    Description getDescription();
    default boolean hasDescription() {
        return getDescription() != null;
    }
    
    TimeInterval getTimeInterval();
    default boolean hasTimeInterval() {
        return getTimeInterval() != null;
    }

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueCategoryList getCategories();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.hasDescription() && this.hasDescription() && other.getDescription().equals(this.getDescription())
                && other.hasTimeInterval() && this.hasTimeInterval() && other.getTimeInterval().equals(this.getTimeInterval()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle());
        if (hasDescription()) {
            builder.append(" Description: ").append(getDescription());
        }
        if (hasTimeInterval()) {
            builder.append(" Description: ")
            .append(getDescription())
            .append(" StartTime: ")
            .append(getTimeInterval());
        }
        builder.append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this task's categories
     */
    default String categoriesString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getCategories().forEach(category -> buffer.append(category).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }
}
