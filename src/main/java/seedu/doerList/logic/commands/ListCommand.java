package seedu.doerList.logic.commands;


/**
 * Lists all persons in the doerList to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks in the Do-erlist with or without category. "
            + "Parameters: [CATEGORY] \n"
            + "Example: " + COMMAND_WORD
            + " CS2102";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
