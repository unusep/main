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

    public TaskBuilder withTimeInterval(String startTime, String endTime) throws IllegalValueException {
        this.task.setTimeInterval(new TimeInterval(startTime, endTime));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
