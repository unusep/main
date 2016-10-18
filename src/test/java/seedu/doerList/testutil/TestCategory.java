package seedu.doerList.testutil;

import java.util.function.Predicate;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.BuildInCategory;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;

/**
 * TestCategory for comparing the count
 * 
 * @author XP
 *
 */
public class TestCategory extends BuildInCategory {
    public int expectedNumTasks;
    
    public TestCategory(String name, int expectedNumTasks) throws IllegalValueException {
        super(name, null);
        this.expectedNumTasks = expectedNumTasks;
    }
    
    public void setExpectedNumTasks(int num) {
        expectedNumTasks = num;
    } 
}
