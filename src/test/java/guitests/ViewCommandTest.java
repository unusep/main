package guitests;

import guitests.guihandles.TaskCardHandle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import static seedu.doerList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.Assert.assertNotNull;
import seedu.doerList.logic.commands.ViewCommand;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.ui.TaskCard;

public class ViewCommandTest extends DoerListGuiTest {

    @Test
    public void viewTask_invalidComamnd() {
        //invalid view command
        commandBox.runCommand("view");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void viewTask_nonEmptyList() {
        assertViewInvalid(10); //invalid index
        assertNoTaskSelected();

        assertViewSuccess(1); //first person in the list
        int taskCount = td.getTypicalTasks().length;
        assertViewSuccess(taskCount); //last person in the list
        int middleIndex = taskCount / 2;
        assertViewSuccess(middleIndex); //a person in the middle of the list

        assertViewInvalid(taskCount + 1); //invalid index
        assertTaskSelected(middleIndex); //assert previous selection remains
        /* Testing other invalid indexes such as -1 should be done when testing the ViewCommand */
    }

    @Test
    public void viewTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertViewInvalid(1); //invalid index
    }

    private void assertViewInvalid(int index) {
        commandBox.runCommand("view " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertViewSuccess(int index) {
        commandBox.runCommand("view " + index);
        assertResultMessage(String.format(ViewCommand.MESSAGE_VIEW_TASK_SUCCESS, index));
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
