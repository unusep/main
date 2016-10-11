package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.logic.commands.EditCommand;
import seedu.doerList.model.task.Title;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.testutil.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.doerList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;

public class EditCommandTest extends DoerListGuiTest {

    @Test
    public void edit() {
        // edit task successfully
        TestTask[] expectedList = td.getTypicalTasks();
        expectedList[0] = td.task;
        assertEditSuccess(1, td.task, expectedList);


        // edit task result in duplicate
        commandBox.runCommand(td.task.getAddCommand().replace("add", "edit 2"));
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(expectedList));

        // edit to empty list
        commandBox.runCommand("clear");
        commandBox.runCommand(td.task.getAddCommand().replace("add", "edit 1"));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertEquals(taskListPanel.getAllTasksInTaskCards().size(), 0);

        //invalid command
        commandBox.runCommand("edit Do Homework");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    private void assertEditSuccess(int index, TestTask editedTask, TestTask... currentList) {
        commandBox.runCommand(editedTask.getAddCommand().replace("add", "edit " + index));

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().fullTitle);
        assertMatching(editedTask, editedCard);

        //confirm the list now contains all previous tasks withUpdated task
        currentList[index - 1] = editedTask; 
        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
