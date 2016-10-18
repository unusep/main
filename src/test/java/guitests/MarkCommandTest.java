package guitests;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.MarkCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MarkCommandTest extends DoerListGuiTest {

    @Test
    public void mark() throws IllegalValueException {
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
        expectedBuildInCateogires[4].expectedNumTasks = 2;
        assertMarkSuccess(targetIndex, expectedBuildInCateogires);
        
        //marks one task from middle
        targetIndex = currentList.length;
        expectedBuildInCateogires[4].expectedNumTasks = 3;
        assertMarkSuccess(targetIndex / 2, expectedBuildInCateogires);
        
        //marks one task from end
        targetIndex = currentList.length - 1;
        expectedBuildInCateogires[4].expectedNumTasks = 4;
        assertMarkSuccess(targetIndex, expectedBuildInCateogires);
        
        //marks invalid task
        commandBox.runCommand("mark " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
    }
    
    private void assertMarkSuccess(int indexToBeMarked, TestCategory[] expectedBuildInCateogires) {
        commandBox.runCommand("mark " + indexToBeMarked);
        
        ReadOnlyTask taskToMark = taskListPanel.getTask(indexToBeMarked - 1);
        
        assertTrue(taskToMark.getBuildInCategories().contains(BuildInCategoryList.COMPLETE));
        
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark));
        
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCateogires));
    }
    
    
}
