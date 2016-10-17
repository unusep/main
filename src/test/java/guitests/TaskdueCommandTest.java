package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.testutil.TestTask;

public class TaskdueCommandTest extends DoerListGuiTest {

    //@Test
    //public void taskdue_nonEmptyList() {
    //    assertTaskdueResult("taskdue 2016-10-07 12:00"); //no results
    //    assertTaskdueResult("taskdue 2016-10-07 12:00", td.alice, td.benson, td.carl, td.daniel, td.elle, td.fiona, td.george); //multiple results

        //find after deleting one result
     //   commandBox.runCommand("delete 1");
    //    assertTaskdueResult("taskdue 2016-10-07 12:00", td.benson, td.carl, td.daniel, td.elle, td.fiona, td.george);
    //}

    @Test
    public void taskdue_emptyList(){
        commandBox.runCommand("clear");
        assertTaskdueResult("taskdue 2016-10-07 12:00"); //no results
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
