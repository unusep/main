//@@author A0147978E
package guitests;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.ListCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.testutil.TestCategory;

public class ListCommandTest extends DoerListGuiTest {

    // case sensitive test is included in the logicManagerTest.java
    
    @Test
    public void list_category_taskShown() throws IllegalValueException {
        // generate exptected result
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
    public void list_afterRunAddCommand_successful() throws IllegalValueException {
        // expected result
        List<TestCategory> expectedCategoryList = Lists.newArrayList(
                new TestCategory("CS2101", 2, 1),
                new TestCategory("CS2103", 1, 1),
                new TestCategory("MA1101R", 1, 0)
        );
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 8, 1),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4, 0)
        );
        
        // list Today
        List<TestCategory> expected_TODAY = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task4)
        );
        assertListSuccess(BuildInCategoryList.TODAY, expected_TODAY, expectedBuildInCategoryList, expectedCategoryList);
        
        // add task that should belong to `Today` category
        commandBox.runCommand(td.task10.getAddCommand());

        // expected result
        List<TestCategory> expectedBuildInCategoryList_after = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 9, 1),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 3, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 2, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 4, 0)
        );
        List<TestCategory> expectedCategoryList_after = Lists.newArrayList(
                new TestCategory("CS2101", 2, 1),
                new TestCategory("CS2103", 2, 1),
                new TestCategory("MA1101R", 1, 0)
        );
        // list Today again
        List<TestCategory> expected_TODAY_after = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3, td.task10),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, td.task4)
        );
        assertListSuccess(BuildInCategoryList.TODAY, expected_TODAY_after, expectedBuildInCategoryList_after, expectedCategoryList_after);
    }
    
    /**
     * Validating the tasks under specific category have been listed
     * Validating the rest of data has been updated accordingly
     * 
     * @param categoryToShow
     * @param expectedDisplayTaskPanel
     * @param expectedBuildInCategoryList
     * @param expectedCategoryList
     */
    private void assertListSuccess(Category categoryToShow, 
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand("list " + categoryToShow.categoryName);
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS, categoryToShow.categoryName));
        
        // check whether the UI contains the desired data
        checkUiMatching(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
    }

}
