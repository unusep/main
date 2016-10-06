package seedu.doerList.testutil;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code DoerList ab = new DoerListBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class DoerListBuilder {

    private DoerList doerList;

    public DoerListBuilder(DoerList doerList){
        this.doerList = doerList;
    }

    public DoerListBuilder withTask(Task person) throws UniqueTaskList.DuplicateTaskException {
        doerList.addTask(person);
        return this;
    }

    public DoerListBuilder withCategory(String categoryName) throws IllegalValueException {
        doerList.addCategory(new Category(categoryName));
        return this;
    }

    public DoerList build(){
        return doerList;
    }
}
