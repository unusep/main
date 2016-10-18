package seedu.doerList.logic.commands;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.*;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.ui.TaskListPanel;

public class MarkCommand extends Command {
    
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": To mark this task as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "mark task: %1$s";
    
    private int targetIndex;
    
    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    public CommandResult execute() {       
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        try {
            ReadOnlyTask target = BuildInCategoryList.getTaskWhenCategorizedByBuildInCategory(targetIndex, lastShownList, TaskListPanel.categorizedBy);
            model.markTask(target);
            return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, target));  
        } catch (TaskNotFoundException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
          
    }
}
