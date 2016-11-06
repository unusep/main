//@@author A0139401N
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

    public static final String SHOWING_HELP_MESSAGE = "Opened help window to User Guide.";

    public static final String INVALID_HELP_MESSAGE = "Invalid Command Name after 'help' - type 'help' to bring up the User Guide";

    private final String command;

    public HelpCommand(String command) {
        this.command = command;
    }
  //@@author

    @Override
    public CommandResult execute() {
        switch (command) {
            //@@author A0140905M
            case AddCommand.COMMAND_WORD:
                return new CommandResult(AddCommand.MESSAGE_USAGE);

            case EditCommand.COMMAND_WORD:
                return new CommandResult(EditCommand.MESSAGE_USAGE);

            case MarkCommand.COMMAND_WORD:
                return new CommandResult(MarkCommand.MESSAGE_USAGE);

            case UnmarkCommand.COMMAND_WORD:
                return new CommandResult(UnmarkCommand.MESSAGE_USAGE);

            case ListCommand.COMMAND_WORD:
                return new CommandResult(ListCommand.MESSAGE_USAGE);

            case FindCommand.COMMAND_WORD:
                return new CommandResult(FindCommand.MESSAGE_USAGE);

            case ViewCommand.COMMAND_WORD:
                return new CommandResult(ViewCommand.MESSAGE_USAGE);

            case DeleteCommand.COMMAND_WORD:
                return new CommandResult(DeleteCommand.MESSAGE_USAGE);

            case RedoCommand.COMMAND_WORD:
                return new CommandResult(RedoCommand.MESSAGE_USAGE);

            //@@author A0147978E
            case UndoCommand.COMMAND_WORD:
                return new CommandResult(UndoCommand.MESSAGE_USAGE);

            case TaskdueCommand.COMMAND_WORD:
                return new CommandResult(TaskdueCommand.MESSAGE_USAGE);

            case SaveCommand.COMMAND_WORD:
                return new CommandResult(SaveCommand.MESSAGE_USAGE);

            case "":
                EventsCenter.getInstance().post(new ShowHelpRequestEvent());
                return new CommandResult(SHOWING_HELP_MESSAGE);

            default:
                return new CommandResult(INVALID_HELP_MESSAGE);
        }
    }
}
