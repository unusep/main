package seedu.doerList.logic.commands;

import java.util.Optional;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

public class UnmarkCommand extends Command {
    
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": marks a 'done' task as 'not done'.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "unmark task: %1$s";
    public static final String MESSAGE_DUPLICATE_UNMARK = "The task is still not done yet.";
    
    private int targetIndex;
    
    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    public CommandResult execute() {
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUnmark = (Task) lastShownList.get(targetIndex - 1);
        
        if (!taskToUnmark.getBuildInCategories().contains(BuildInCategoryList.COMPLETE))
            return new CommandResult(String.format(MESSAGE_DUPLICATE_UNMARK));
        
        try {
            model.unmarkTask(taskToUnmark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));    
    }
}
