package seedu.doerList.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.exceptions.IllegalValueException;
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

	public static final String MESSAGE_DELETE_TASK_SUCCESS = "edit task: %1$s";

	public final int targetIndex;

	 private Title toUpdateTitle = null;
	 private Description toUpdateDescription = null;
	private TimeInterval toUpdateTimeInterval = null;
	 private UniqueCategoryList toUpdateCategories = null;

	public EditCommand(int targetIndex, String title, String description,
			String startTime, String endTime, Set<String> categories) throws IllegalValueException {
		this.targetIndex = targetIndex;

        if (title.trim().length() != 0) {
            this.toUpdateTitle = new Title(title);
        }
        if (description.trim().length() != 0) {
            this.toUpdateDescription = new Description(description);
        }

        //time

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
		return null;
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
                toUpdateTimeInterval != null ? toUpdateTimeInterval : original.getTimeInterval(),
                toUpdateCategories != null ? toUpdateCategories : original.getCategories()
        );
    }
}
