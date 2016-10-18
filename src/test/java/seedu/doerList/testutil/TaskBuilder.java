package seedu.doerList.testutil;

import org.joda.time.DateTime;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.BuildInCategory;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.doerList.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withTitle(String title) throws IllegalValueException {
        this.task.setTitle(new Title(title));
        return this;
    }

    public TaskBuilder withCategories(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getCategories().add(new Category(tag));
        }
        return this;
    }
    
    public TaskBuilder withCategories(Category ... categories) {
        for (Category category: categories) {
            try {
                task.getCategories().add(category);
            } catch (DuplicateCategoryException e) {
                // impossible
            };
        }
        return this;
    }
    
    public TaskBuilder withBuildInCategories(BuildInCategory ... categories) {
        for (BuildInCategory category: categories) {
            task.getBuildInCategories().add(category);
        }
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new Description(description));
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new TodoTime(startTime));
        return this;
    }
    
    public TaskBuilder withStartTime(DateTime startTime) throws IllegalValueException {
        this.task.setStartTime(new TodoTime(startTime));
        return this;
    }
    
    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new TodoTime(endTime));
        return this;
    }
    
    public TaskBuilder withEndTime(DateTime endTime) throws IllegalValueException {
        this.task.setEndTime(new TodoTime(endTime));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
