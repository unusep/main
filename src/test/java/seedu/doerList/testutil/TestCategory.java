package seedu.doerList.testutil;

import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

import edu.emory.mathcs.backport.java.util.Arrays;
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
