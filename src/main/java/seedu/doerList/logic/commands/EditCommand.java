package seedu.doerList.logic.commands;

import java.util.Set;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the
 * doerList.
 */
public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": edit the task identified by the index number used in the last task listing.\n"
			+ "Parameters: INDEX (must be a positive integer) [-t TASK] [-d DESCRIPTION] [{[START]->[END]}] [-c CATEGORY]...\n"
			+ "Example: " + COMMAND_WORD + " 1 -t Go to lecture -d study";

	public static final String MESSAGE_DELETE_TASK_SUCCESS = "edit task: %1$s";

	public final int targetIndex;

	public EditCommand(int targetIndex, String title, String description,
			String startTime, String endTime, Set<String> categories) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model
				.getFilteredTaskList();

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(
					Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask personToDelete = lastShownList.get(targetIndex - 1);

		try {
			model.deleteTask(personToDelete);
		} catch (TaskNotFoundException pnfe) {
			assert false : "The target person cannot be missing";
		}

		return new CommandResult(
				String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete));
	}

}
