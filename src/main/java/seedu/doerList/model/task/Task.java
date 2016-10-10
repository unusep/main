package seedu.doerList.model.task;

import java.util.Objects;

import seedu.doerList.commons.util.CollectionUtil;
import seedu.doerList.model.category.UniqueCategoryList;

/**
 * Represents an task in the to-do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Title title;
    private Description description;
    private TodoTime startTime;
    private TodoTime endTime;

    private UniqueCategoryList categories;

    /**
     * Title must be presented.
     */
    public Task(Title title, Description description, TodoTime startTime, TodoTime endTime, UniqueCategoryList categories) {
        assert title != null;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.categories = new UniqueCategoryList(categories); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), 
                source.getDescription(), 
                source.getStartTime(), 
                source.getEndTime(), 
                source.getCategories());
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Description getDescription() {
        return description;
    }
    
    @Override
    public UniqueCategoryList getCategories() {
        return categories;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setCategories(UniqueCategoryList replacement) {
        categories.setCategories(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, description, startTime, endTime, categories);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public TodoTime getStartTime() {
        return startTime;
    }

    @Override
    public TodoTime getEndTime() {
        return endTime;
    }

}
