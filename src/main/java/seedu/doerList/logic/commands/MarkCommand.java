package seedu.doerList.logic.commands;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;

public class MarkCommand extends Command {
    
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": To mark this task as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "mark task: %1$s";
    public static final String MESSAGE_DUPLICATE_MARK = "The task is already marked as done in the Do-erList";
    
    private int targetIndex;
    
    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    public CommandResult execute() {
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);
        if (taskToMark.getBuildInCategories().contains(BuildInCategoryList.COMPLETE)) {
            return new CommandResult(MESSAGE_DUPLICATE_MARK);
        }
            
        taskToMark.addBuildInCategory(BuildInCategoryList.COMPLETE);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToMark));
        
    }
}
