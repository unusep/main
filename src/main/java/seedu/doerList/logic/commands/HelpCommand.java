package seedu.doerList.logic.commands;


import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public static final String INVALID_HELP_MESSAGE = "Invalid Command Name after 'help' - type 'help' to bring up the User Guide";

    private final String command;

    public HelpCommand(String command) {
        this.command = command;
    }

    @Override
    public CommandResult execute() {
        switch (command) {

        case AddCommand.COMMAND_WORD:
            return new CommandResult(AddCommand.MESSAGE_USAGE);

        case EditCommand.COMMAND_WORD:
            return new CommandResult(EditCommand.MESSAGE_USAGE);

        //case MarkCommand.COMMAND_WORD:
        //    return new CommandResult(SHOWING_HELP_MARK_MESSAGE);

        //case UnmarkCommand.COMMAND_WORD:
        //    return new CommandResult(SHOWING_HELP_UNMARK_MESSAGE);

        case ListCommand.COMMAND_WORD:
            return new CommandResult(ListCommand.MESSAGE_USAGE);

        case FindCommand.COMMAND_WORD:
            return new CommandResult(FindCommand.MESSAGE_USAGE);

        case ViewCommand.COMMAND_WORD:
            return new CommandResult(ViewCommand.MESSAGE_USAGE);

        case DeleteCommand.COMMAND_WORD:
            return new CommandResult(DeleteCommand.MESSAGE_USAGE);

        case UndoCommand.COMMAND_WORD:
            return new CommandResult(UndoCommand.MESSAGE_USAGE);

        //case RedoCommand.COMMAND_WORD:
        //    return new CommandResult(SHOWING_HELP_REDO_MESSAGE);

        //case TaskdueCommand.COMMAND_WORD:
        //    return new CommandResult(SHOWING_HELP_TASKDUE_MESSAGE);

        case "":
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);

        default:
            return new CommandResult(INVALID_HELP_MESSAGE);
        }
    }
}
