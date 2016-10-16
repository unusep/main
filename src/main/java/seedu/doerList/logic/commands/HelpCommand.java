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

    public static final String SHOWING_HELP_ADD_MESSAGE =
            "add: Add a task to the Do-er List. \n"
                    + "Parameters: /t TITLE [/d DESCRIPTION] [{[START]->[END]}] [/c CATEGORY] ...\n"
                    + "Example: add /t Do post-lecture quiz {today->tomorrow} /c CS2103";

    public static final String SHOWING_HELP_EDIT_MESSAGE =
            "edit: Edit an existing task in the Do-er List. \n"
                    + "Parameters: INDEX [/t TITLE] [/d DESCRIPTION] [{[START]->[END]}] [/c CATEGORY] ...\n"
                    + "edit 1 /t Do ST2334 quiz /c ST2334";

    public static final String SHOWING_HELP_MARK_MESSAGE =
            "mark: Mark a certain task as done in the Doer-list. \n"
                    + "Parameters: INDEX \n"
                    + "mark 5";

    public static final String SHOWING_HELP_UNMARK_MESSAGE =
            "unmark: Mark a certain task as undone in the Doer-list. \n"
                    + "Parameters: INDEX \n"
                    + "unmark 5";

    public static final String SHOWING_HELP_LIST_MESSAGE =
            "list: Show a list of all tasks in the Doer-list under the specific category. \n"
                    + "Parameters: [CATEGORY] \n"
                    + "list CS2101";

    public static final String SHOWING_HELP_FIND_MESSAGE =
            "find: Find tasks whose names contain any of the given keywords. \n"
                    + "Parameters: KEYWORD [MORE_KEYWORDS] \n"
                    + "find david";

    public static final String SHOWING_HELP_VIEW_MESSAGE =
            "view: View the task identified by the index number used in the last task listing. \n"
                    + "Parameters: INDEX \n"
                    + "view 3";

    public static final String SHOWING_HELP_DELETE_MESSAGE =
            "delete: Delete a specified task from the Do-erList. Irreversible. \n"
                    + "Parameters: INDEX \n"
                    + "delete 2";

    public static final String SHOWING_HELP_UNDO_MESSAGE =
            "undo: Undo the most recent operation which modify the data in the Doer-list. \n"
                    + "Parameters: \n"
                    + "undo";

    public static final String SHOWING_HELP_REDO_MESSAGE =
            "redo: Redo the most recent undo \n"
                    + "Parameters: \n"
                    + "redo";

    public static final String SHOWING_HELP_TASKDUE_MESSAGE =
            "taskdue: Find all tasks due on and before the date specified in the Doer-list. \n"
                    + "Parameters: END_DATE \n"
                    + "taskdue tomorrow";

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

        case "mark":
            return new CommandResult(SHOWING_HELP_MARK_MESSAGE);

        case "unmark":
            return new CommandResult(SHOWING_HELP_UNMARK_MESSAGE);

        case ListCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_LIST_MESSAGE);

        case FindCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_FIND_MESSAGE);

        case ViewCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_VIEW_MESSAGE);

        case DeleteCommand.COMMAND_WORD:
            return new CommandResult(SHOWING_HELP_DELETE_MESSAGE);

        case "undo":
            return new CommandResult(SHOWING_HELP_UNDO_MESSAGE);

        case "redo":
            return new CommandResult(SHOWING_HELP_REDO_MESSAGE);

        case "taskdue":
            return new CommandResult(SHOWING_HELP_TASKDUE_MESSAGE);

        case "":
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);

        default:
            return new CommandResult(INVALID_HELP_MESSAGE);
        }
    }
}
