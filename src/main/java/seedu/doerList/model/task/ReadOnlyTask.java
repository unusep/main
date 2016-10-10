package seedu.doerList.model.task;

import seedu.doerList.model.category.UniqueCategoryList;

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
    
    TodoTime getStartTime();
    default boolean hasStartTime() {
        return getStartTime() != null;
    }
    
    TodoTime getEndTime();
    default boolean hasEndTime() {
        return getEndTime() != null;
    }
    
    default boolean isFloatingTask() {
        return !hasStartTime() && !hasEndTime();
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
                && ((!other.hasDescription() && !this.hasDescription()) 
                        || (other.hasDescription() && this.hasDescription() 
                                && other.getDescription().equals(this.getDescription())))
                && ((!other.hasStartTime() && !this.hasStartTime()) 
                        || (other.hasStartTime() && this.hasStartTime() 
                                && other.getStartTime().equals(this.getStartTime())))
                && ((!other.hasEndTime() && !this.hasEndTime()) 
                        || (other.hasEndTime() && this.hasEndTime() 
                                && other.getEndTime().equals(this.getEndTime())))
                   );
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
        if (hasStartTime() && !hasEndTime()) {
            builder
            .append(" Begin At: ")
            .append(getStartTime());
        }
        if (!hasStartTime() && hasEndTime()) {
            builder
            .append(" Due: ")
            .append(getEndTime());
        }
        if (hasStartTime() && hasEndTime()) {
            builder
            .append(" Time: ")
            .append(getStartTime() + "->" + getEndTime());
        }
        if (!getCategories().getInternalList().isEmpty()) {
            builder.append(" Categories: ");
            getCategories().forEach(builder::append);
        }
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
