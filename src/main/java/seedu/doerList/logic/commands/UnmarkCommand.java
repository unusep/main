package seedu.doerList.logic.commands;

import java.util.Optional;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;

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

        ReadOnlyTask taskToUnmark = lastShownList.get(targetIndex - 1);
        BuildInCategoryList taskCategories = taskToUnmark.getBuildInCategories();
        if (taskCategories.contains(BuildInCategoryList.COMPLETE)) {
            taskToUnmark.removeBuildInCategory(BuildInCategoryList.COMPLETE);
            return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
        } else {
            return new CommandResult(String.format(MESSAGE_DUPLICATE_UNMARK));
        }
            
    }
}
