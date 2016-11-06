//@@author A0139168W
package guitests;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.UnmarkCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.TodoTime;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;

public class UnmarkCommandTest extends DoerListGuiTest {

    @Test
    public void unmark() throws IllegalValueException {
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8, 2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 1, 0)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2, 1),
                new TestCategory("CS2103", 1, 1),
                new TestCategory("MA1101R", 1, 0)
        );
        
        List<TestCategory> expectedPanel_complete = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task1),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task4),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task5),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task8)
        );
        
        commandBox.runCommand("list " + BuildInCategoryList.COMPLETE.categoryName);
        commandBox.runCommand("unmark " + 1);
        commandBox.runCommand("unmark " + 2);
        assertUnmarkSuccess(3, td.task5, expectedPanel_complete, expectedBuildInCategoryList, expectedCategoryList);
        
        
        // list all
        commandBox.runCommand("list " + BuildInCategoryList.ALL.categoryName);
        List<TestCategory> expectedPanel_all = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task1, td.task2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3, td.task4),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task5, td.task6),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task8)
        );
        // check whether the UI contains the desired data
        checkUiMatching(expectedPanel_all, expectedBuildInCategoryList, expectedCategoryList);
    }
    
    @Test
    public void unmark_taskGetUpdated() throws IllegalValueException {
        TestTask updatedTask = new TestTask(td.task3); // task3 has only start time
        updatedTask.setStartTime(new TodoTime(updatedTask.getStartTime().value.minusDays(1)));
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8, 1),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4, 0)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2, 1),
                new TestCategory("CS2103", 1, 1),
                new TestCategory("MA1101R", 1, 0)
        );
        
        List<TestCategory> expectedPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, updatedTask),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1,
                        td.task4, td.task5, td.task8)
        );
        
        assertUnmarkSuccess(2, td.task3, expectedPanel, expectedBuildInCategoryList, expectedCategoryList);              
    }
    
    @Test
    public void mark_unsuccessful() {
        //marks invalid task
        commandBox.runCommand("unmark " + 9);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //marks empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("unmark 1");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    
    private void assertUnmarkSuccess(int index, TestTask taskToUnmark,
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand("unmark " + index);
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
        
        // check whether the UI contains the desired data
        checkUiMatching(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
    
    }
       
}
