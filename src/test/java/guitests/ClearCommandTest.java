package guitests;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;

public class ClearCommandTest extends DoerListGuiTest {

    @Test
    public void clear() throws IllegalValueException {

        //verify a non-empty list can be cleared
        List<TestCategory> expectedDisplayTaskPanel1 = Lists.newArrayList();
        List<TestCategory> expectedBuildInCategoryList1 = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 0, 0)
        );
        List<TestCategory> expectedCategoryList1 = Lists.newArrayList();
        commandBox.runCommand("clear");
        checkUiMatching(expectedDisplayTaskPanel1, expectedBuildInCategoryList1, expectedCategoryList1);

        //verify other commands can work after a clear command
        List<TestCategory> expectedDisplayTaskPanel2 = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.TODAY.categoryName, td.task3)
        );
        List<TestCategory> expectedBuildInCategoryList2 = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName, 1, 0),
                new TestCategory(BuildInCategoryList.TODAY.categoryName, 1, 0),
                new TestCategory(BuildInCategoryList.NEXT.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.INBOX.categoryName, 0, 0),
                new TestCategory(BuildInCategoryList.COMPLETE.categoryName, 0, 0)
        );
        List<TestCategory> expectedCategoryList2 = Lists.newArrayList(
                new TestCategory("CS2101", 1, 0)
        );
        commandBox.runCommand(td.task3.getAddCommand());
        checkUiMatching(expectedDisplayTaskPanel2, expectedBuildInCategoryList2, expectedCategoryList2);
        commandBox.runCommand("delete 1");
        checkUiMatching(expectedDisplayTaskPanel1, expectedBuildInCategoryList1, expectedCategoryList1);

        //verify clear command works when the list is empty
        commandBox.runCommand("clear");
        checkUiMatching(expectedDisplayTaskPanel1, expectedBuildInCategoryList1, expectedCategoryList1);
    }

}
