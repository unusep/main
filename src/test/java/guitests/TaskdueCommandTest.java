package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.testutil.TestTask;

public class TaskdueCommandTest extends DoerListGuiTest {

    @Test
    public void taskdue_nonEmptyList() {
        assertTaskdueResult("taskdue yesterday", td.task6, td.task7); //multiple results
        assertTaskdueResult("taskdue today 23pm", td.task1, td.task6, td.task7); //one results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        assertTaskdueResult("taskdue today 23pm"); //no results
        
        //add one
        commandBox.runCommand(td.task8.getAddCommand());
        assertTaskdueResult("taskdue today 23pm", td.task8); //1 results
    }

    @Test
    public void taskdue_emptyList(){
        commandBox.runCommand("clear");
        assertTaskdueResult("taskdue tomorrow"); //no results
    }

    @Test
    public void taskdue_invalidCommand_fail() {
        commandBox.runCommand("taskdue2015george");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertTaskdueResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(Command.getMessageForTaskListShownSummary(expectedHits.length));
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
