package guitests;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.logic.commands.DeleteCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.testutil.TestUtil;
import seedu.doerList.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import java.util.List;

public class DeleteCommandTest extends DoerListGuiTest {

    @Test
    public void delete() throws IllegalValueException {

        //delete the first in the list
        int targetIndex = 1;
        commandBox.runCommand("delete " + targetIndex);

        //delete the last in the list
        targetIndex = 7;
        commandBox.runCommand("delete " + targetIndex);

        // add task in the middle
        commandBox.runCommand(td.task10.getAddCommand());
        
        //delete from the middle of the list
        List<TestCategory> expectedDisplayTaskPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3, td.task10),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task4, td.task5)
        );
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 6),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 1),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 2)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 1),
                new TestCategory("CS2103", 1),
                new TestCategory("MA1101R", 1)
        );
        targetIndex = 5;
        assertDeleteSuccess(targetIndex, td.task1, expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
        

        //invalid index
        commandBox.runCommand("delete 7");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }
    
    private void assertDeleteSuccess(int targetIndexOneIndexed, TestTask deletedTask,
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand("delete " + targetIndexOneIndexed);
        assertResultMessage(String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, deletedTask));
        
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        //confirm the list now contains all previous persons plus the new person
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));
    
    }

}
