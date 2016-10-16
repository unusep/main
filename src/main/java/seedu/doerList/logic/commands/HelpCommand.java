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

    public static final String SHOWING_HELP_ADD_MESSAGE = AddCommand.MESSAGE_USAGE;

    public static final String SHOWING_HELP_EDIT_MESSAGE = EditCommand.MESSAGE_USAGE;

    //public static final String SHOWING_HELP_MARK_MESSAGE = MarkCommand.MESSAGE_USAGE;

    //public static final String SHOWING_HELP_UNMARK_MESSAGE = UnmarkCommand.MESSAGE_USAGE;

    public static final String SHOWING_HELP_LIST_MESSAGE = ListCommand.MESSAGE_USAGE;

    public static final String SHOWING_HELP_FIND_MESSAGE = FindCommand.MESSAGE_USAGE;

    public static final String SHOWING_HELP_VIEW_MESSAGE = ViewCommand.MESSAGE_USAGE;

    public static final String SHOWING_HELP_DELETE_MESSAGE = DeleteCommand.MESSAGE_USAGE;

    //public static final String SHOWING_HELP_UNDO_MESSAGE = UndoCommand.MESSAGE_USAGE;

    //public static final String SHOWING_HELP_REDO_MESSAGE = RedoCommand.MESSAGE_USAGE;

    //public static final String SHOWING_HELP_TASKDUE_MESSAGE = TaskdueCommand.MESSAGE_USAGE;

    private final String command;

    public HelpCommand(String command) {
        this.command = command;
    }

    @Override
    public CommandResult execute() {
        switch (command) {

        case AddCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_ADD_MESSAGE);

        case EditCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_EDIT_MESSAGE);

        //case MarkCommand.COMMAND_WORD:
        //    return new CommandResult(SHOWING_HELP_MARK_MESSAGE);

        //case UnmarkCommand.COMMAND_WORD:
        //    return new CommandResult(SHOWING_HELP_UNMARK_MESSAGE);

        case ListCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_LIST_MESSAGE);

        case FindCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_FIND_MESSAGE);

        case ViewCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_VIEW_MESSAGE);

        case DeleteCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_DELETE_MESSAGE);

        //case UndoCommand.COMMAND_WORD:
        //    return new CommandResult(SHOWING_HELP_UNDO_MESSAGE);

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
