package seedu.doerList.model.task;

import java.util.Objects;

import seedu.doerList.model.category.BuildInCategory;
import seedu.doerList.model.category.BuildInCategoryList;
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
    private Recurring recurring;

    private UniqueCategoryList categories;
    private BuildInCategoryList buildInCategoires;

    /**
     * Title must be presented.
     */
    public Task(Title title, Description description, TodoTime startTime, TodoTime endTime, Recurring recurring, UniqueCategoryList categories) {
        assert title != null;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recurring = recurring;
        this.categories = new UniqueCategoryList(categories); // protect internal tags from changes in the arg list
        this.buildInCategoires = new BuildInCategoryList();
    }

    /**
     * Copy constructor that takes in a Read-Only Task.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), 
                source.getDescription(), 
                source.getStartTime(), 
                source.getEndTime(), 
                source.getRecurring(),
                source.getCategories());
        buildInCategoires.replaceWith(source.getBuildInCategories());
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
    public Recurring getRecurring() {
        return recurring;
    }
    
    @Override
    public UniqueCategoryList getCategories() {
        return categories;
    }
    
    @Override
    public BuildInCategoryList getBuildInCategories() {
        return buildInCategoires;
    }
    
    @Override
    public void addBuildInCategory(BuildInCategory category) {
        buildInCategoires.add(category);
    }

    @Override
    public void removeBuildInCategory(BuildInCategory category) {
        buildInCategoires.remove(category);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setCategories(UniqueCategoryList replacement) {
        categories.setCategories(replacement);
    }
    
    /**
     * Replace this task's build in category with given build in categories
     * 
     * @param theBuildInCategories
     */
    public void setBuildInCategories(BuildInCategoryList theBuildInCategories) {
        buildInCategoires.replaceWith(theBuildInCategories);
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
