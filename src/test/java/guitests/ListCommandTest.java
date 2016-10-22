package guitests;

import guitests.guihandles.CategoryCardHandle;
import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.logic.commands.ListCommand;
import seedu.doerList.model.category.BuildInCategory;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.testutil.TestUtil;
import seedu.doerList.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListCommandTest extends DoerListGuiTest {

    @Test
    public void list() throws IllegalValueException {
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8),
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
        
        // list ALL
        List<TestCategory> expected_ALL = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task4, td.task5, td.task8)
        );
        assertListSuccess(BuildInCategoryList.ALL, expected_ALL, expectedBuildInCategoryList, expectedCategoryList);
        
        // list Today
        List<TestCategory> expected_TODAY = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task4)
        );
        assertListSuccess(BuildInCategoryList.TODAY, expected_TODAY, expectedBuildInCategoryList, expectedCategoryList);
        
        // list NEXT
        List<TestCategory> expected_NEXT = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.NEXT.categoryName, td.task6),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task5)
        );
        assertListSuccess(BuildInCategoryList.NEXT, expected_NEXT, expectedBuildInCategoryList, expectedCategoryList);
        
        // list INBOX
        List<TestCategory> expected_INBOX = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.INBOX.categoryName, td.task7),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task8)
        );
        assertListSuccess(BuildInCategoryList.INBOX, expected_INBOX, expectedBuildInCategoryList, expectedCategoryList);
        
        // list COMPLETE
        List<TestCategory> expected_COMPLETE = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task1, td.task4, td.task5, td.task8)
        );
        assertListSuccess(BuildInCategoryList.COMPLETE, expected_COMPLETE, expectedBuildInCategoryList, expectedCategoryList);
        
        // list CS2101
        List<TestCategory> expected_CS2101 = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task2),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3)
        );
        assertListSuccess(new Category("CS2101"), expected_CS2101, expectedBuildInCategoryList, expectedCategoryList);
        
        // list CS2103
        List<TestCategory> expected_CS2103 = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task2)
        );
        assertListSuccess(new Category("CS2103"), expected_CS2103, expectedBuildInCategoryList, expectedCategoryList);
        
        // list MA1101R
        List<TestCategory> expected_MA1101R = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task4)
        );
        assertListSuccess(new Category("MA1101R"), expected_MA1101R, expectedBuildInCategoryList, expectedCategoryList);
           
    }
    
    @Test
    public void list_add_list() throws IllegalValueException {
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2),
                new TestCategory("CS2103", 1),
                new TestCategory("MA1101R", 1)
        );
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4)
        );
        // list Today
        List<TestCategory> expected_TODAY = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task4)
        );
        assertListSuccess(BuildInCategoryList.TODAY, expected_TODAY, expectedBuildInCategoryList, expectedCategoryList);
        
        // add today task
        commandBox.runCommand(td.task10.getAddCommand());

        List<TestCategory> expectedBuildInCategoryList_after = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 9),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 3),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4)
        );
        List<TestCategory> expectedCategoryList_after = Lists.newArrayList(
                new TestCategory("CS2101", 2),
                new TestCategory("CS2103", 2),
                new TestCategory("MA1101R", 1)
        );
        // list Today
        List<TestCategory> expected_TODAY_after = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3, td.task10),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task4)
        );
        assertListSuccess(BuildInCategoryList.TODAY, expected_TODAY_after, expectedBuildInCategoryList_after, expectedCategoryList_after);
    }
    
    private void assertListSuccess(Category categoryToShow, 
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand("list " + categoryToShow.categoryName);
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS, categoryToShow.categoryName));
        
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        //confirm the list now contains all previous persons plus the new person
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));
    
    }

}
