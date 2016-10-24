//@@author A0147978E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.doerList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.commons.util.TimeUtil;
import seedu.doerList.logic.commands.EditCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.Description;
import seedu.doerList.model.task.Title;
import seedu.doerList.model.task.TodoTime;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;

public class EditCommandTest extends DoerListGuiTest {

    @Test
    public void edit() throws IllegalValueException {
        // edit the first one in the list
        TestTask afterEdition = new TestTask(td.task2);
        afterEdition.setTitle(new Title("Test Task 2 Edit Title"));
        afterEdition.setDescription(new Description("Test Task 2 Edit Description"));
        afterEdition.setStartTime(new TodoTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(10).toString()));
        afterEdition.setEndTime(new TodoTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(13).toString()));
        afterEdition.setCategories(new UniqueCategoryList(new Category("CS2103")));
        
        // expected output
        List<TestCategory> expectedDisplayTaskPanel = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3, afterEdition),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task4, td.task5, td.task8)
        );
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4)
        );
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 1),
                new TestCategory("CS2103", 1),
                new TestCategory("MA1101R", 1)
        );
        assertEditSuccess("edit 1 /t Test Task 2 Edit Title /d Test Task 2 Edit Description /s today 10am /e today 1pm /c CS2103", 
                td.task2, afterEdition, expectedDisplayTaskPanel, 
                expectedBuildInCategoryList, expectedCategoryList);
        
       
        // edit task result in duplicate
        commandBox.runCommand(afterEdition.getEditCommand(1));
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));

        // edit to empty list
        commandBox.runCommand("clear");
        commandBox.runCommand(td.task8.getEditCommand(1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // invalid command
        commandBox.runCommand("edit Do Homework");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }
    
    /**
     * Validating the given task has been successfully edited.
     * Validating the rest of data has been updated accordingly
     * 
     * @param command
     * @param toEdit
     * @param afterEdit
     * @param expectedDisplayTaskPanel
     * @param expectedBuildInCategoryList
     * @param expectedCategoryList
     */
    private void assertEditSuccess(String command, TestTask toEdit, TestTask afterEdit,
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand(command);
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, toEdit, afterEdit));
        
        // confirm the list now contains accurate buildInCategory and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        // confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        // confirm the list now contains all tasks
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));  
    }

}
