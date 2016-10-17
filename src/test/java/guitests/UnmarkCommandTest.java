package guitests;

import org.junit.Test;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.logic.commands.UnmarkCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.testutil.TestUtil;
import seedu.doerList.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class UnmarkCommandTest extends DoerListGuiTest {
    
    @Test
    public void unmark() {
        
        //unmarks one task from top
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        currentList[targetIndex-1].addBuildInCategory(BuildInCategoryList.COMPLETE);
        assertUnmarkSuccess(targetIndex, currentList);
        
        //unmarks one task from middle
        targetIndex = currentList.length / 2;
        currentList[targetIndex-1].addBuildInCategory(BuildInCategoryList.COMPLETE);
        assertUnmarkSuccess(targetIndex, currentList);
        
        //unmarks one task from the end
        targetIndex = currentList.length;
        currentList[targetIndex-1].addBuildInCategory(BuildInCategoryList.COMPLETE);
        assertUnmarkSuccess(targetIndex, currentList);
        
        //invalid index
        commandBox.runCommand("unmark " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
    }
    
    private void assertUnmarkSuccess(int targetIndex, TestTask[] currentList) {
        TestTask taskToUnmark = currentList[targetIndex - 1];
        
        commandBox.runCommand("unmark " + targetIndex);
        
        System.out.println(taskToUnmark.getBuildInCategories().contains(BuildInCategoryList.COMPLETE));
        
        assertTrue(!taskToUnmark.getBuildInCategories().contains(BuildInCategoryList.COMPLETE));
        
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
    }
}
