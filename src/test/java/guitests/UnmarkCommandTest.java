package guitests;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.logic.commands.MarkCommand;
import seedu.doerList.logic.commands.UnmarkCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;

import org.junit.Test;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertTrue;

import java.util.List;

public class UnmarkCommandTest extends DoerListGuiTest {

    @Test
    public void unmark() throws IllegalValueException {
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 1)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2),
                new TestCategory("CS2103", 1),
                new TestCategory("MA1101R", 1)
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
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));       
        //confirm the list now contains all previous persons plus the new person
        assertTrue(taskListPanel.isListMatching(expectedPanel_all));
    }
    
    //@@author A0139168W
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
        
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        //confirm the list now contains all previous persons plus the new person
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));
    
    }
       
}
