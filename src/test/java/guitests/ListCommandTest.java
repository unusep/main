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

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

public class ListCommandTest extends DoerListGuiTest {

    @Test
    public void list() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        TestCategory[] expectedBuildInCateogires = {
                new TestCategory(BuildInCategoryList.ALL.categoryName, 7),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 2),
                new TestCategory(BuildInCategoryList.NEXT7DAYS.categoryName, 2),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 1),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 1)
        };
        TestCategory[] expectedCategories = td.getTypicalTestCategory();
        assertListSuccess(null, currentList, expectedBuildInCateogires, expectedCategories);
    }

    private void assertListSuccess(Category category, TestTask[] expectedList, 
            TestCategory[] expectedBuildInCateogires, TestCategory[] expectedCateogires) {
        String listCategoryName = "";
        if (category == null) {
            category = BuildInCategoryList.ALL;
        } else {
            listCategoryName = category.categoryName;
        }
        commandBox.runCommand("list " + listCategoryName);
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
