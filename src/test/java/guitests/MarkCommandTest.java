package guitests;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.logic.commands.MarkCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;

import org.junit.Test;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertTrue;

import java.util.List;

public class MarkCommandTest extends DoerListGuiTest {

    @Test
    public void mark() throws IllegalValueException {
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8, 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 7, 0)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2, 0),
                new TestCategory("CS2103", 1, 0),
                new TestCategory("MA1101R", 1, 0)
        );
        
        List<TestCategory> expectedPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task2, td.task3,
                        td.task4, td.task5, td.task6, td.task8)
        );
        
        commandBox.runCommand("mark " + 1);
        commandBox.runCommand("mark " + 1);
        assertMarkSuccess(1, td.task6, expectedPanel, expectedBuildInCategoryList, expectedCategoryList);
              
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
        
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        //confirm the list now contains all previous persons plus the new person
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));
    
    }
       
}
