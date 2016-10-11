package guitests;

import org.junit.Test;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends DoerListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Love"); //no results
        assertFindResult("find Math", td.benson, td.daniel, td.elle); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Math", td.daniel, td.elle);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find CA"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(Command.getMessageForTaskListShownSummary(expectedHits.length));
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
