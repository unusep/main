//@@author A0147978E 
package guitests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.testutil.TypicalTestTasks;

public class AddCommandTest extends DoerListGuiTest {

    @Test
    public void add_tasks_successful() throws IllegalValueException {
        // define expected output
        List<TestCategory> expectedDisplayTaskPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task9, td.task2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3, td.task10),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task11, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task12, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task4, td.task5, td.task8)
        );
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 12),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 3),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 3),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2),
                new TestCategory("CS2103", 2),
                new TestCategory("MA1101R", 1),
                new TestCategory("Urgent", 1),
                new TestCategory("Life", 1)
        );
        // add tasks at once
        commandBox.runCommand(TypicalTestTasks.task9.getAddCommand());
        commandBox.runCommand(TypicalTestTasks.task10.getAddCommand());
        commandBox.runCommand(TypicalTestTasks.task11.getAddCommand());
        TestTask taskToAdd = TypicalTestTasks.task12;
        assertAddSuccess(taskToAdd, expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);


        // add duplicate task no change
        commandBox.runCommand(TypicalTestTasks.task3.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));

        // add to empty todo list
        commandBox.runCommand("clear");
        List<TestCategory> expectedDisplayTaskPanel2 = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task2)
        );
        List<TestCategory> expectedBuildInCategoryList2 = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 1),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 0)
        );
        List<TestCategory> expectedCategoryList2 = Lists.newArrayList(
                new TestCategory("CS2101", 1),
                new TestCategory("CS2103", 1)
        );
        assertAddSuccess(td.task2, expectedDisplayTaskPanel2, expectedBuildInCategoryList2, expectedCategoryList2);
              
        // invalid command
        commandBox.runCommand("adds Do Homework");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    

    /**
     * Validating the given task has been successfully added.
     * Validating the rest of data has been updated accordingly
     * 
     * @param taskToAdd
     * @param expectedDisplayTaskPanel
     * @param expectedBuildInCategoryList
     * @param expectedCategoryList
     */
    private void assertAddSuccess(TestTask taskToAdd, 
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        // confirm the list now contains accurate buildInCategory and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        // confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        // confirm the list now contains all tasks
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));
    }

}
