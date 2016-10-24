//@@author A0147978E
package guitests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;

public class FindCommandTest extends DoerListGuiTest {

    @Test
    public void find_nonEmptyList() throws IllegalValueException {
        // find get multiple results
        List<TestCategory> expectedDisplayTaskPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task2),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task8)
        );
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName + " (filtered)", 4, 1),
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
        assertFindResult("find Math", expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
        
        // find and there is no result
        expectedBuildInCategoryList.get(0).setExpectedNumTasks(0);
        expectedBuildInCategoryList.get(0).setExpectedDueTasks(0);
        assertFindResult("find Love", Lists.newArrayList(), expectedBuildInCategoryList, expectedCategoryList);
        

        //find after deleting one result
        commandBox.runCommand("find Math");
        commandBox.runCommand("delete 1");
        expectedBuildInCategoryList.get(0).setExpectedNumTasks(3);
        expectedBuildInCategoryList.get(0).setExpectedDueTasks(0);
        List<TestCategory> expectedDisplayTaskPanel2 = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task8)
        );
        List<TestCategory> expectedCategoryList2 = Lists.newArrayList(
                new TestCategory("CS2101", 1, 0),
                new TestCategory("MA1101R", 1, 0)
        );
        assertFindResult("find Math", expectedDisplayTaskPanel2, expectedBuildInCategoryList, expectedCategoryList2);
    }

    @Test
    public void find_emptyList() throws IllegalValueException{
        commandBox.runCommand("clear");
        List<TestCategory> expectedDisplayTaskPanel = Lists.newArrayList();
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName + " (filtered)", 0, 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 0, 0)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList();
        assertFindResult("find CA", expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList); //no results
    }

    @Test
    public void find_invalidCommand_fail() throws IllegalValueException {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     * Validating the tasks with `keyword` have been listed
     * Validating the rest of data has been updated accordingly
     * 
     * @param command
     * @param expectedDisplayTaskPanel
     * @param expectedBuildInCategoryList
     * @param expectedCategoryList
     */
    private void assertFindResult(String command, 
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand(command);
        int numTasks = expectedDisplayTaskPanel.stream().mapToInt((c) -> c.getPreDefinedTasks().size()).sum();
        assertResultMessage(Command.getMessageForTaskListShownSummary(numTasks));
        
        // confirm the list now contains accurate buildInCategory and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        // confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        // confirm the list now contains all tasks
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel)); 
    }
}
