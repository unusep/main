package guitests;

import guitests.guihandles.CategoryCardHandle;
import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.doerList.logic.commands.AddCommand;
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
        TestTask[] currentList = td.getTypicalTasks();
        TestCategory[] expectedBuildInCateogires = {
                new TestCategory(BuildInCategoryList.ALL.categoryName, 7),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 1),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 1)
        };
        TestCategory[] expectedCategories = td.getTypicalTestCategory();
        
        // list category CS2101
        TestTask[] expectedList_c_1 = {TypicalTestTasks.task1, TypicalTestTasks.task6};
        assertListSuccess(new TestCategory("CS2101", 2), 
                expectedList_c_1, expectedBuildInCateogires, expectedCategories);
        
        // list category CS2103
        TestTask[] expectedList_c_2 = {TypicalTestTasks.task6};
        assertListSuccess(new TestCategory("CS2103", 1), 
                expectedList_c_2, expectedBuildInCateogires, expectedCategories);
        
        // add one task check all command
        commandBox.runCommand(TypicalTestTasks.task8.getAddCommand());      
        expectedBuildInCateogires[0].setExpectedNumTasks(8);
        expectedBuildInCateogires[1].setExpectedNumTasks(3);
        expectedCategories[1].setExpectedNumTasks(2);
        ArrayList<TestTask> allTasks = new ArrayList<TestTask>(Arrays.asList(currentList));
        allTasks.add(TypicalTestTasks.task8);
        TestTask[] expectedList1 = new TestTask[allTasks.size()];
        allTasks.toArray(expectedList1);
        assertListSuccess(new TestCategory(BuildInCategoryList.ALL.categoryName, 8), 
                expectedList1, expectedBuildInCateogires, expectedCategories);
         
        
        // today
        TestTask[] expectedList2 = {TypicalTestTasks.task1, TypicalTestTasks.task2, TypicalTestTasks.task8};
        assertListSuccess(new TestCategory(BuildInCategoryList.TODAY.categoryName, 3), 
                expectedList2, expectedBuildInCateogires, expectedCategories);
        
        
        // next7days
        // add one task
        commandBox.runCommand(TypicalTestTasks.task9.getAddCommand());
        expectedBuildInCateogires[0].setExpectedNumTasks(9);
        expectedBuildInCateogires[2].setExpectedNumTasks(3);
        TestTask[] expectedList3 = {TypicalTestTasks.task3, TypicalTestTasks.task4, TypicalTestTasks.task9};
        assertListSuccess(new TestCategory(BuildInCategoryList.NEXT.categoryName, 3), 
                expectedList3, expectedBuildInCateogires, expectedCategories);
        
        
        // Inbox
        commandBox.runCommand(TypicalTestTasks.task10.getAddCommand());
        expectedBuildInCateogires[0].setExpectedNumTasks(10);
        expectedBuildInCateogires[3].setExpectedNumTasks(2);
        // update category list
        ArrayList<TestCategory> allCategories = new ArrayList<TestCategory>(Arrays.asList(expectedCategories));
        allCategories.add(new TestCategory("Life", 1));
        TestCategory[] expectedList4_c = new TestCategory[allCategories.size()];
        allCategories.toArray(expectedList4_c);
        TestTask[] expectedList4 = {TypicalTestTasks.task5, TypicalTestTasks.task10};
        assertListSuccess(new TestCategory(BuildInCategoryList.INBOX.categoryName, 2), 
                expectedList4, expectedBuildInCateogires, expectedList4_c);
        
        // list category Life
        TestTask[] expectedList_c_3 = {TypicalTestTasks.task10};
        assertListSuccess(new TestCategory("Life", 1), 
                expectedList_c_3, expectedBuildInCateogires, expectedList4_c);
        
        // Complete
        TestTask[] expectedList5 = {TypicalTestTasks.task2};
        assertListSuccess(new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 1), 
                expectedList5, expectedBuildInCateogires, expectedList4_c);
    }

    private void assertListSuccess(TestCategory category, TestTask[] expectedList, 
            TestCategory[] expectedBuildInCateogires, TestCategory[] expectedCateogires) {
        commandBox.runCommand("list " + category.categoryName);
        //confirm the category list select the desired category
        CategoryCardHandle selectedCard = categorySideBar.getSelection(category.categoryName);
        assertMatching(category, selectedCard);

        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCateogires));
        //confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCateogires));
        
        //confirm list view now show all desired tasks
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
