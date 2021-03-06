//@@author A0139401N
package seedu.doerList.model.task;

import seedu.doerList.model.category.BuildInCategory;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.UniqueCategoryList;

/**
 * A read-only immutable interface for an Task in the to-do list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Title getTitle();
    Description getDescription();
    Recurring getRecurring();
    
    default boolean hasDescription() {
        return getDescription() != null;
    }
    
    default boolean hasRecurring(){
        return getRecurring() != null;
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
    
    default boolean isDueTask() {
        return !hasStartTime() && hasEndTime();
    }
    
    default boolean isBeginAtTask() {
        return hasStartTime() && !hasEndTime();
    }
    
    default boolean isStartEndTask() {
        return hasStartTime() && hasEndTime();
    }
    
    default String getTime() {
        final StringBuilder builder = new StringBuilder();
        if (hasStartTime() && !hasEndTime()) {
            builder
            .append(" Begin At: ")
            .append(getStartTime().toReadableTime());
        }
        if (!hasStartTime() && hasEndTime()) {
            builder
            .append(" Due: ")
            .append(getEndTime().toReadableTime());
        }
        if (hasStartTime() && hasEndTime()) {
            builder
            .append(" Time: ")
            .append(getStartTime().toReadableTime() + " -> " + getEndTime().toReadableTime());
        }
        return builder.toString();
    }

    /**
     * The returned CategoryList is a deep copy of the internal CategoryList,
     * changes on the returned list will not affect the person's internal categories.
     */
    UniqueCategoryList getCategories();
    
    BuildInCategoryList getBuildInCategories();   
    void addBuildInCategory(BuildInCategory category);
    void removeBuildInCategory(BuildInCategory category);

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
                && ((!other.hasRecurring() && !this.hasRecurring()) 
                        || (other.hasRecurring() && this.hasRecurring() 
                                && other.getRecurring().equals(this.getRecurring())))
                && this.getBuildInCategories().equals(other.getBuildInCategories()));
    }

    /**
     * Formats the task as text, showing all the task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle());
        if (hasDescription()) {
            builder.append(" Description: ").append(getDescription());
        }
        builder.append(getTime());
        if (hasRecurring()) {
            builder.append(" Recurring: " + getRecurring().toReadableText());
        }
        if (!getCategories().getInternalList().isEmpty()) {
            builder.append(" Categories: ");
            getCategories().forEach(builder::append);
        }
        return builder.toString();
    }
}
