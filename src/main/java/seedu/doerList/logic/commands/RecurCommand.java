//@@author A0139401N
package seedu.doerList.logic.commands;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.task.*;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.ui.TaskListPanel;

/**
 * Modifies an existing task identified using it's last displayed index into a recurring task.
 */
public class RecurCommand extends Command {

    public static final String COMMAND_WORD = "recur";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": To mark this task as recurring.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RECUR_TASK_SUCCESS = "recurring task: \nBefore: %1$s\nAfter: %2$s";
    public static final String MESSAGE_DUPLICATE_TASK = "The recurring task already exists in the Do-erlist";
    public static final String RECURRING_NAME = "Recurring";

    public final int targetIndex;

    public RecurCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {       
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        try {
            ReadOnlyTask target = TaskListPanel.getDisplayedIndexWhenCategorizedByBuildInCategory(targetIndex, lastShownList);
            model.recurTask(target);
            return new CommandResult(String.format(MESSAGE_RECUR_TASK_SUCCESS, target));  
        } catch (TaskNotFoundException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
          
    }
    
}