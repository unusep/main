//@@author A0147978E 
package guitests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.RedoCommand;
import seedu.doerList.logic.commands.UndoCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TypicalTestTasks;

public class UndoRedoCommandTest extends DoerListGuiTest {

    @Test
    public void redoUndo_addEditDeleteClear_successful() throws IllegalValueException {
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
        commandBox.runCommand(td.task9.getAddCommand());
        assertUndoCommandSuccess(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
        
        // redo add task
        // define expected output
        List<TestCategory> expectedDisplayTaskPanel_add = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task9, td.task2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task4, td.task5, td.task8)
        );
        List<TestCategory> expectedBuildInCategoryList_add = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 9, 2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4, 0)
        );
        List<TestCategory> expectedCategoryList_add = Lists.newArrayList(
                new TestCategory("CS2101", 2, 1),
                new TestCategory("CS2103", 1, 1),
                new TestCategory("MA1101R", 1, 0),
                new TestCategory("Urgent", 1, 1)
        );
        assertRedoCommandSuccess(expectedDisplayTaskPanel_add, 
                expectedBuildInCategoryList_add, expectedCategoryList_add);
        
        commandBox.runCommand("undo");
     
        List<TestCategory> expectedDisplayTaskPanel_edit = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task9),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task4, td.task5, td.task8)
        );
        List<TestCategory> expectedBuildInCategoryList_edit = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8, 1),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4, 0)
        );
        List<TestCategory> expectedCategoryList_edit = Lists.newArrayList(
                new TestCategory("CS2101", 1, 0),
                new TestCategory("MA1101R", 1, 0),
                new TestCategory("Urgent", 1, 1)
        );
        // edit task
        expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2, 1),
                new TestCategory("MA1101R", 1, 0),
                new TestCategory("CS2103", 1, 1)
        );
        commandBox.runCommand(td.task9.getEditCommand(1));
        assertUndoCommandSuccess(expectedDisplayTaskPanel, 
                expectedBuildInCategoryList, expectedCategoryList);
        assertRedoCommandSuccess(expectedDisplayTaskPanel_edit, 
                expectedBuildInCategoryList_edit, expectedCategoryList_edit);

        commandBox.runCommand("undo");
        // delete task
        List<TestCategory> expectedDisplayTaskPanel_delete = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task4, td.task5, td.task8)
        );
        List<TestCategory> expectedBuildInCategoryList_delete = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 7, 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4, 0)
        );
        List<TestCategory> expectedCategoryList_delete = Lists.newArrayList(
                new TestCategory("CS2101", 1, 0),
                new TestCategory("MA1101R", 1, 0)
        );
        commandBox.runCommand("delete 1");
        assertUndoCommandSuccess(expectedDisplayTaskPanel, 
                expectedBuildInCategoryList, expectedCategoryList);
        assertRedoCommandSuccess(expectedDisplayTaskPanel_delete, 
                expectedBuildInCategoryList_delete, expectedCategoryList_delete);
        
        commandBox.runCommand("undo");
        
        // clear task
        commandBox.runCommand("clear");
        assertUndoCommandSuccess(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
        List<TestCategory> expectedBuildInCategoryList_empty = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 0, 0)
        );
        assertRedoCommandSuccess(Lists.newArrayList(), expectedBuildInCategoryList_empty, Lists.newArrayList());            
    }
    
    @Test
    public void redoUndo_markUnmark_successful() throws IllegalValueException {
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
        commandBox.runCommand("mark 1");
        commandBox.runCommand("unmark 4");
        
        commandBox.runCommand("undo");
        assertUndoCommandSuccess(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
        
        commandBox.runCommand("redo");
        // define expected output
        List<TestCategory> expectedDisplayTaskPanel_after = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task1),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task2, td.task4, td.task5, td.task8)
        );
        List<TestCategory> expectedBuildInCategoryList_after = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8, 1),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4, 0)
        );
        List<TestCategory> expectedCategoryList_after = Lists.newArrayList(
                new TestCategory("CS2101", 2, 0),
                new TestCategory("CS2103", 1, 0),
                new TestCategory("MA1101R", 1, 0)
        );
        assertRedoCommandSuccess(expectedDisplayTaskPanel_after, 
                expectedBuildInCategoryList_after, expectedCategoryList_after);
    }
    

    /**
     * Validating the given undo operation is successful.
     * Validating the rest of data has been updated accordingly
     * 
     * @param expectedDisplayTaskPanel
     * @param expectedBuildInCategoryList
     * @param expectedCategoryList
     */
    private void assertUndoCommandSuccess(
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS);
        
        checkSameList(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
    }
    
    /**
     * Validating the given redo operation is successful.
     * Validating the rest of data has been updated accordingly
     * 
     * @param expectedDisplayTaskPanel
     * @param expectedBuildInCategoryList
     * @param expectedCategoryList
     */
    private void assertRedoCommandSuccess(
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_REDO_SUCCESS);
        
        checkSameList(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
    }

    /**
     * Make sure the buildInCategory, category, all tasks are displayed correctly
     */
    private void checkSameList(List<TestCategory> expectedDisplayTaskPanel,
            List<TestCategory> expectedBuildInCategoryList, List<TestCategory> expectedCategoryList) {
        // confirm the list now contains accurate buildInCategory and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        // confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        // confirm the list now contains all tasks
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));
    }
    
}
