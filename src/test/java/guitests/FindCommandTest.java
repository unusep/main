package guitests;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;

import static org.junit.Assert.assertTrue;

import java.util.List;

public class FindCommandTest extends DoerListGuiTest {

    @Test
    public void find_nonEmptyList() throws IllegalValueException {
        List<TestCategory> expectedDisplayTaskPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task2),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task8)
        );
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName + " (filtered)", 4),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2),
                new TestCategory("CS2103", 1),
                new TestCategory("MA1101R", 1)
        );
        assertFindResult("find Math", expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList); //multiple results
        
        expectedBuildInCategoryList.get(0).setExpectedNumTasks(0);
        assertFindResult("find Love", Lists.newArrayList(), expectedBuildInCategoryList, expectedCategoryList); //no results
        

        //find after deleting one result
        commandBox.runCommand("find Math");
        commandBox.runCommand("delete 1");
        expectedBuildInCategoryList.get(0).setExpectedNumTasks(3);
        List<TestCategory> expectedDisplayTaskPanel2 = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task8)
        );
        List<TestCategory> expectedCategoryList2 = Lists.newArrayList(
                new TestCategory("CS2101", 1),
                new TestCategory("CS2103", 0),
                new TestCategory("MA1101R", 1)
        );
        assertFindResult("find Math", expectedDisplayTaskPanel2, expectedBuildInCategoryList, expectedCategoryList2);
    }

    @Test
    public void find_emptyList() throws IllegalValueException{
        commandBox.runCommand("clear");
        List<TestCategory> expectedDisplayTaskPanel = Lists.newArrayList();
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName + " (filtered)", 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2),
                new TestCategory("CS2103", 1),
                new TestCategory("MA1101R", 1)
        );
        assertFindResult("find CA", expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList); //no results
    }

    @Test
    public void find_invalidCommand_fail() throws IllegalValueException {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    private void assertFindResult(String command, 
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand(command);
        int numTasks = expectedDisplayTaskPanel.stream().mapToInt((c) -> c.getPreDefinedTasks().size()).sum();
        assertResultMessage(Command.getMessageForTaskListShownSummary(numTasks));
        
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        //confirm the list now contains all previous persons plus the new person
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));
    
    }
}
