package seedu.doerList.logic.commands;

/**
 * List out all the tasks in the doerList to the user.
 */
public class ViewAllCommand extends Command {

    public static final String COMMAND_WORD = "view-all";

    public static final String MESSAGE_SUCCESS = "Viewing all tasks";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all tasks in the Do-erList.";

    public ViewAllCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

