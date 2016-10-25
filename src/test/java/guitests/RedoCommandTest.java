//@@author A0147978E 
package guitests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.logic.commands.UndoCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.testutil.TypicalTestTasks;

public class RedoCommandTest extends DoerListGuiTest {

    @Test
    public void add_tasks_successful() throws IllegalValueException {
        // define expected output
        List<TestCategory> expectedDisplayTaskPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task4, td.task5, td.task8)
        );
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
        // add task
        commandBox.runCommand(TypicalTestTasks.task9.getAddCommand());
        assertUndoSuccess(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
        
        // edit task
        expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2, 1),
                new TestCategory("MA1101R", 1, 0),
                new TestCategory("CS2103", 1, 1)
        );
        commandBox.runCommand(TypicalTestTasks.task9.getEditCommand(1));
        assertUndoSuccess(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
        
        // delete task
        commandBox.runCommand("delete 1");
        assertUndoSuccess(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
        
        // clear task
        commandBox.runCommand("clear");
        assertUndoSuccess(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);

    }
    

    /**
     * Validating the given undo operation is successful.
     * Validating the rest of data has been updated accordingly
     * 
     * @param expectedDisplayTaskPanel
     * @param expectedBuildInCategoryList
     * @param expectedCategoryList
     */
    private void assertUndoSuccess( 
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS);
        
        // confirm the list now contains accurate buildInCategory and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        // confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        // confirm the list now contains all tasks
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));
    }

}
