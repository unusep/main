package seedu.doerList.logic.commands;

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

    private final String endTime;

    public TaskdueCommand(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }
}
