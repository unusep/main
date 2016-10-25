//@@author A0139401N
package seedu.doerList.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.*;
import seedu.doerList.model.task.*;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.ui.TaskListPanel;

/**
 * Modifies an existing task identified using it's last displayed index into a recurring task.
 */
public class RecurCommand extends Command {

    public static final String COMMAND_WORD = "recur";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": creates a recurring the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [/t TASK] [/d DESCRIPTION] [/s START] [/e END] [/c CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 /t Go to lecture /d study";

    public static final String MESSAGE_RECUR_TASK_SUCCESS = "recurring task: \nBefore: %1$s\nAfter: %2$s";
    public static final String MESSAGE_DUPLICATE_TASK = "The recurring task already exists in the Do-erlist";
    public static final String RECURRING_NAME = "Recurring";

    public final int targetIndex;

    private UniqueCategoryList toUpdateCategories = null;

    public RecurCommand(int targetIndex) throws IllegalValueException {
        this.targetIndex = targetIndex;
        final Set<Category> categorySet = new HashSet<>();
        
        categorySet.add(new Category(RECURRING_NAME));
        this.toUpdateCategories = new UniqueCategoryList(categorySet);
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        try {
            ReadOnlyTask target = TaskListPanel.getDisplayedIndexWhenCategorizedByBuildInCategory(targetIndex, lastShownList);
            Task newTask = generateUpdatedTask(target);

            model.replaceTask(target, newTask);

            return new CommandResult(String.format(MESSAGE_RECUR_TASK_SUCCESS, target, newTask));
        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

    /**
     * Generate new task based on updated information
     *
     * @param original original Task (Task before update)
     * @return Task with updated information
     */
    private Task generateUpdatedTask(ReadOnlyTask original) {
        Task newTask = new Task(
                 original.getTitle(),
                 original.getDescription(),
                 original.getStartTime(),
                 original.getEndTime(),
                 toUpdateCategories
                );
        newTask.setBuildInCategories(original.getBuildInCategories());
        return newTask;
    }
}