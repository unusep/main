package guitests;

import org.junit.Test;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.MarkCommand;
import seedu.doerList.logic.commands.UnmarkCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.testutil.TestUtil;
import seedu.doerList.testutil.TypicalTestTasks;

import static org.junit.Assert.*;

public class UnmarkCommandTest extends DoerListGuiTest {
    
    @Test
    public void unmark() throws IllegalValueException {
        // reset predicate for buildInCategory
        BuildInCategoryList.resetBuildInCategoryPredicate();
        
        TestCategory[] expectedBuildInCateogires = {
                new TestCategory(BuildInCategoryList.ALL.categoryName, 7),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 1),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 1)
        };
        
        //marks one task from top
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertUnmarkSuccess(targetIndex, expectedBuildInCateogires);
        
        //marks one task from middle
        targetIndex = currentList.length;
        assertUnmarkSuccess(targetIndex / 2, expectedBuildInCateogires);
        
        //marks one task from end
        targetIndex = currentList.length - 1;
        assertUnmarkSuccess(targetIndex, expectedBuildInCateogires);
        
        // nothing happened
        
        // mark the complete task
        targetIndex = 2;
        expectedBuildInCateogires[4].expectedNumTasks = 0;
        assertUnmarkSuccess(targetIndex, expectedBuildInCateogires);
        
        //marks invalid task
        commandBox.runCommand("mark " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
    }
    
    private void assertUnmarkSuccess(int indexToBeUnmarked, TestCategory[] expectedBuildInCateogires) {
        commandBox.runCommand("unmark " + indexToBeUnmarked);
        
        ReadOnlyTask taskToUnmark = taskListPanel.getTask(indexToBeUnmarked - 1);
        
        assertFalse(taskToUnmark.getBuildInCategories().contains(BuildInCategoryList.COMPLETE));
        
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
        
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCateogires));
    }
}
