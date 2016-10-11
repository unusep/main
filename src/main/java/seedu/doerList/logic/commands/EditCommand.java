package seedu.doerList.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.category.*;
import seedu.doerList.model.task.*;
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

	public static final String MESSAGE_EDIT_TASK_SUCCESS = "edit task: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Do-erlist";

	public final int targetIndex;

	private Title toUpdateTitle = null;
	private Description toUpdateDescription = null;
	private TodoTime toUpdateStartTime = null;
	private TodoTime toUpdateEndTime = null;
	private UniqueCategoryList toUpdateCategories = null;

	public EditCommand(int targetIndex, String title, String description,
			String startTime, String endTime, Set<String> categories) throws IllegalValueException {
		this.targetIndex = targetIndex;

        if (title != null) {
            this.toUpdateTitle = new Title(title);
        }
        if (description != null) {
            this.toUpdateDescription = new Description(description);
        }
        if (startTime != null) {
            this.toUpdateStartTime = new TodoTime(startTime);
        }
        if (endTime != null) {
            this.toUpdateEndTime = new TodoTime(endTime);
        }

        if (!categories.isEmpty()) {
            final Set<Category> categorySet = new HashSet<>();
            for (String categoryName : categories) {
                categorySet.add(new Category(categoryName));
            }
            this.toUpdateCategories = new UniqueCategoryList(categorySet);
        }
    }

	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        try {
        	    ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        	    Task newTask = generateUpdatedTask(taskToEdit);

        	    model.deleteTask(taskToEdit);
        	    model.addTask(newTask);

        	    lastShownList.remove(taskToEdit);
        	    lastShownList.add(newTask);

        	    return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, newTask));
        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_TASK_NOT_IN_DOERLIST);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
	}

	/**
     * Generate new task based on updated information
     *
     * @param original original Person (Person before update)
     * @return Person with updated information
     */
    private Task generateUpdatedTask(ReadOnlyTask original) {
        return new Task(
                toUpdateTitle != null ? toUpdateTitle : original.getTitle(),
                toUpdateDescription != null ? toUpdateDescription : original.getDescription(),
                toUpdateStartTime != null ? toUpdateStartTime : original.getStartTime(),
                toUpdateEndTime != null ? toUpdateEndTime : original.getEndTime(),
                toUpdateCategories != null ? toUpdateCategories : original.getCategories()
        );
    }
}
