package seedu.doerList.logic.commands;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.events.ui.JumpToListRequestEvent;
import seedu.doerList.model.task.ReadOnlyTask;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View a specific task in the Do-erList."
            + "Parameters: [TASK_NUMBER]"
            + "Example: " + COMMAND_WORD
            + " 5";
    
    public static final String MESSAGE_SUCCESS_TASK = "View task: ";
    
    public final int targetIndex;
    
    public ViewCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SUCCESS_TASK, targetIndex));

    }
}
