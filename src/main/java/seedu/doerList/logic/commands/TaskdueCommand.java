package seedu.doerList.logic.commands;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.task.TodoTime;

/**
 * Finds and lists all tasks that have end time before or on the time specified
 * by the argument
 */
public class TaskdueCommand extends Command {

    public static final String COMMAND_WORD = "taskdue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all tasks that have deadline before or on the time specified by the end time.\n"
            + "Parameters: END_TIME \n" + "Example: " + COMMAND_WORD
            + " tomorrow";

    private final TodoTime endTime;

    public TaskdueCommand(String endTime) throws IllegalValueException {
        this.endTime = new TodoTime(endTime);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(endTime);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
