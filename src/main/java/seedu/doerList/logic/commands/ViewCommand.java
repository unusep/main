package seedu.doerList.logic.commands;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.events.ui.JumpToListRequestEvent;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.ui.TaskListPanel;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View a specific task in the Do-erList."
            + "Parameters: [TASK_NUMBER]"
            + "Example: " + COMMAND_WORD
            + " 5";
    
    public static final String MESSAGE_VIEW_TASK_SUCCESS = "Viewing task: %1$s";
    
    public final int targetIndex;
    
    public ViewCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        try {
            ReadOnlyTask target = BuildInCategoryList.getTaskWhenCategorizedByBuildInCategory(targetIndex, lastShownList, TaskListPanel.categorizedBy);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
            return new CommandResult(String.format(MESSAGE_VIEW_TASK_SUCCESS, target));
        } catch (TaskNotFoundException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

    }
}
