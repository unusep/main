//@@author A0147978E
package seedu.doerList.testutil;

import java.util.List;

import com.google.common.collect.Lists;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.BuildInCategory;

/**
 * TestCategory to compare the displayed data with actual data.
 * Used in GUI test
 */
public class TestCategory extends BuildInCategory {
    public List<TestTask> tasks;
    
    public int expectedNumTasks;

    public TestCategory(String name, int expected) throws IllegalValueException {
        super(name, null);
        this.expectedNumTasks = expected;
    }
    
    public TestCategory(String name, TestTask... tasks) throws IllegalValueException {
        super(name, null);
        this.tasks = Lists.newArrayList(tasks);
    }
    
    public void setExpectedNumTasks(int num) {
        expectedNumTasks = num;
    } 
    
    public void setTasks(TestTask... tasks) {
        this.tasks = Lists.newArrayList(tasks);
    }
    
    public List<TestTask> getPreDefinedTasks() {
        return this.tasks;
    }
}
