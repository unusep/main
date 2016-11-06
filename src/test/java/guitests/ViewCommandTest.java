//@@author A0139168W
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static seedu.doerList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.logic.commands.ViewCommand;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.ui.TaskCard;

public class ViewCommandTest extends DoerListGuiTest {

    @Test
    public void viewTask_invalidComamnd() {
        // invalid view command
        commandBox.runCommand("view");
        assertNoTaskSelected();
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void viewTask_notEmptyList() {
        // invalid index
        assertViewInvalid(10);
        assertNoTaskSelected();
        
        assertViewSuccess(1, td.task2); //first task in the list
        assertViewSuccess(4, td.task7); //last last in the list
        assertViewSuccess(8, td.task8); //a task in the middle of the list

        assertViewInvalid(9); //invalid index
        assertTaskSelected(8); //assert previous selection remains
        /* Testing other invalid indexes such as -1 should be done when testing the ViewCommand */
    }

    @Test
    public void viewTask_emptyList() {
        commandBox.runCommand("clear");
        assertViewInvalid(1); //invalid index
    }

    private void assertViewInvalid(int index) {
        commandBox.runCommand("view " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertViewSuccess(int index, TestTask task) {
        commandBox.runCommand("view " + index);
        assertResultMessage(String.format(ViewCommand.MESSAGE_VIEW_TASK_SUCCESS, task));
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertNotNull(TaskCard.getSeletedTaskCard());
        assertEquals(TaskCard.getSeletedTaskCard().getDisplayIndex(), index);
    }

    private void assertNoTaskSelected() {
        assertNull(TaskCard.getSeletedTaskCard());
    }

}
