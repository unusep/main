package seedu.doerList.logic.commands;

import seedu.doerList.model.DoerList;

/**
 * Clears the doerlist.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(DoerList.getEmptyDoerList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
