//@@author A0139168W
package guitests;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.MarkCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.TodoTime;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;

public class MarkCommandTest extends DoerListGuiTest {

    @Test
    public void mark() throws IllegalValueException {
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8, 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 5, 0)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2, 0),
                new TestCategory("CS2103", 1, 0),
                new TestCategory("MA1101R", 1, 0)
        );
        
        List<TestCategory> expectedPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task2,
                        td.task4, td.task5, td.task8)
        );
        
        assertMarkSuccess(1, td.task2, expectedPanel, expectedBuildInCategoryList, expectedCategoryList);
              
    }
    
    @Test
    public void mark_taskGetUpdated() throws IllegalValueException {
        TestTask updatedTask = new TestTask(td.task3); // task3 has only start time
        updatedTask.setStartTime(new TodoTime(updatedTask.getStartTime().value.plusDays(1)));
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8, 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 1, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 3, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 5, 0)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2, 0),
                new TestCategory("CS2103", 1, 0),
                new TestCategory("MA1101R", 1, 0)
        );
        
        List<TestCategory> expectedPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.NEXT.categoryName, updatedTask, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task2,
                        td.task4, td.task5, td.task8)
        );
        
        commandBox.runCommand("mark " + 1);
        assertMarkSuccess(1, td.task3, expectedPanel, expectedBuildInCategoryList, expectedCategoryList);
              
    }
    
    @Test
    public void mark_unsuccessful() {
        //marks invalid task
        commandBox.runCommand("mark " + 9);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //marks empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("mark 1");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    
    private void assertMarkSuccess(int index, TestTask taskToMark,
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand("mark " + index);
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark));
        
        // check whether the UI contains the desired data
        checkUiMatching(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
    
    }
       
}
