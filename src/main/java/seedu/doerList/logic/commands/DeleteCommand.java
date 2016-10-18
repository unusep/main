package seedu.doerList.logic.commands;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.events.ui.JumpToListRequestEvent;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.ui.TaskListPanel;

/**
 * Deletes a task identified using it's last displayed index from the doerList.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        try {
            ReadOnlyTask target = BuildInCategoryList.getTaskWhenCategorizedByBuildInCategory(targetIndex, lastShownList, TaskListPanel.categorizedBy);
            model.deleteTask(target);
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, target));
        } catch (TaskNotFoundException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

    }

}
