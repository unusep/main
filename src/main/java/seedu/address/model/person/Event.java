package seedu.address.model.person;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueCategoryList;

import java.util.Objects;

/**
 * Represents an event in the to-do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private Title title;
    private Description description;
    private StartTime startTime;
    private EndTime endTime;

    private UniqueCategoryList categories;

    /**
     * Every field must be present and not null.
     */
    public Event(Title title, Description description, StartTime startTime, EndTime endTime, UniqueCategoryList categories) {
        assert !CollectionUtil.isAnyNull(title, description, startTime, endTime, categories);
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.categories = new UniqueCategoryList(categories); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getDescription(), source.getStartTime(), source.getEndTime(), source.getCategories());
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
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
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
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));

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

}
