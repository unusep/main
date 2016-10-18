package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;

public class TaskdueCommandTest extends DoerListGuiTest {

    @Test
    public void taskdue_nonEmptyList() throws IllegalValueException {
        TestCategory[] expectedBuildInCateogires = {
                new TestCategory("All (filtered)", 2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 1),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 1)
        };
        TestCategory[] expectedCategories = td.getTypicalTestCategory();
        
        TestTask[] expected1 = {td.task6, td.task7};
        TestTask[] expected2 = {td.task1, td.task6, td.task7};
        assertTaskdueResult("taskdue yesterday", expected1, expectedBuildInCateogires, expectedCategories); //multiple results
        expectedBuildInCateogires[0].expectedNumTasks = 3;
        assertTaskdueResult("taskdue today 23pm", expected2, expectedBuildInCateogires, expectedCategories); //one results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        TestCategory[] expectedBuildInCateogires2 = {
                new TestCategory("All (filtered)", 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 1),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 1),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 1)
        };
        expectedCategories[0].expectedNumTasks = 0;
        expectedCategories[1].expectedNumTasks = 0;
        TestTask[] expected3 = {};
        assertTaskdueResult("taskdue today 23pm", expected3, expectedBuildInCateogires2, expectedCategories); //no results
        
        //add one
        commandBox.runCommand(td.task8.getAddCommand());
        TestTask[] expected4 = {td.task8};
        expectedBuildInCateogires2[0].expectedNumTasks = 1;
        expectedBuildInCateogires2[1].expectedNumTasks = 2;
        expectedCategories[1].expectedNumTasks = 1;
        assertTaskdueResult("taskdue today 23pm", expected4, expectedBuildInCateogires2, expectedCategories); //1 results
    }


    @Test
    public void taskdue_invalidCommand_fail() {
        commandBox.runCommand("taskdue2015george");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertTaskdueResult(String command, TestTask[] expectedHits, TestCategory[] expectedBuildInCateogires, TestCategory[] expectedCateogires ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(Command.getMessageForTaskListShownSummary(expectedHits.length));
        
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCateogires));
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCateogires));
        
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
