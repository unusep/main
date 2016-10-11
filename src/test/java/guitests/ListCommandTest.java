package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class ListCommandTest extends DoerListGuiTest {

    @Test
    public void list() {
        TestTask[] currentList = td.getTypicalTasks();
        assertListSuccess("", currentList);
    }

    private void assertListSuccess(String categoryName, TestTask... currentList) {
        commandBox.runCommand("list " + categoryName);

        //confirm the list now show all task
        TestTask[] expectedList = currentList;
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
