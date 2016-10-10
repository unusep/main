package seedu.doerList.testutil;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.Category;
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

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getCategories().add(new Category(tag));
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
    
    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new TodoTime(endTime));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
