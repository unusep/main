package guitests;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;

public class TaskdueCommandTest extends DoerListGuiTest {

    @Test
    public void taskdue_nonEmptyList() throws IllegalValueException {
        List<TestCategory> expectedBuildInCategoryList = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.ALL.categoryName + " (filtered)", 1, 1),
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
        
        // task due today
        List<TestCategory> expectedDisplayTaskPanel_Today = Lists.newArrayList(
                new TestCategory(BuildInCategoryList.DUE.categoryName, td.task2)
        );
        assertTaskdueResult("taskdue today", expectedDisplayTaskPanel_Today, expectedBuildInCategoryList, expectedCategoryList);
    
        // task due empty list
        List<TestCategory> expectedDisplayTaskPanel_LastWeek = Lists.newArrayList();
        expectedBuildInCategoryList.get(0).setExpectedNumTasks(0);
        expectedBuildInCategoryList.get(0).setExpectedDueTasks(0);
        assertTaskdueResult("taskdue last week", expectedDisplayTaskPanel_LastWeek, expectedBuildInCategoryList, expectedCategoryList);
    }


    @Test
    public void taskdue_invalidCommand_fail() {
        commandBox.runCommand("taskdue2015george");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertTaskdueResult(String command, 
            List<TestCategory> expectedDisplayTaskPanel, 
            List<TestCategory> expectedBuildInCategoryList, 
            List<TestCategory> expectedCategoryList) {
        
        commandBox.runCommand(command);
        int numTasks = expectedDisplayTaskPanel.stream().mapToInt((c) -> c.getPreDefinedTasks().size()).sum();
        assertResultMessage(Command.getMessageForTaskListShownSummary(numTasks));
        
        // check whether the UI contains the desired data
        checkUiMatching(expectedDisplayTaskPanel, expectedBuildInCategoryList, expectedCategoryList);
    
    }
}
