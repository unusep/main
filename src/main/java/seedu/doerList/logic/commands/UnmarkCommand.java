package seedu.doerList.logic.commands;

import java.util.Optional;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.events.ui.JumpToListRequestEvent;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.ui.TaskListPanel;

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

        try {
            ReadOnlyTask target = TaskListPanel.getTaskWhenCategorizedByBuildInCategory(targetIndex, lastShownList);
            model.unmarkTask(target);
            return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, target)); 
        } catch (TaskNotFoundException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
                
    }
}
