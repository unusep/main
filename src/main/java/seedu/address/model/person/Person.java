package seedu.address.model.person;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents an event in the to-do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private Name name;
    private Description description;
    private StartTime startTime;
    private EndTime endTime;

    private Category category;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Description description, StartTime startTime, EndTime endTime, Category category) {
        assert !CollectionUtil.isAnyNull(name, description, startTime, endTime, category);
        this.name = name;
        this.description = phone;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
    }

    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getDescription(), source.getStartTime(), source.getEndTime(), source.getCategory());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Phone getDescription() {
        return description;
    }

    @Override
    public Email getStartTime() {
        return startTime;
    }

    @Override
    public Address getendTime() {
        return endTime;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setCategory(Category replacement) {
        category.setCategory(replacement);
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
        return Objects.hash(name, description, startTime, endTime, category);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
