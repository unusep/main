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
        
        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);
        
        try {
            model.unmarkTask(taskToMark);
            return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToMark)); 
        } catch (TaskNotFoundException tnf) {
            // impossible
            assert false;
            return null;
        }
        
    }
}
