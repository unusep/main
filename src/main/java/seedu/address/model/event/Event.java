package seedu.address.model.event;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.category.UniqueCategoryList;

import java.util.Objects;

/**
 * Represents an event in the to-do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private Title title;
    private Description description;
    private TimeInterval timeInterval;

    private UniqueCategoryList categories;

    /**
     * Every field must be present and not null.
     */
    public Event(Title title, Description description, TimeInterval timeInterval, UniqueCategoryList categories) {
        assert title != null;
        this.title = title;
        this.description = description;
        this.timeInterval = timeInterval;
        this.categories = new UniqueCategoryList(categories); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getDescription(), source.getTimeInterval(), source.getCategories());
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
    public TimeInterval getTimeInterval() {
        return timeInterval;
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
        return Objects.hash(title, description, timeInterval, categories);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
