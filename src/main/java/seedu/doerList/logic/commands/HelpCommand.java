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

    private static final String SHOWING_HELP_ADD_MESSAGE =
            "add: Add a task to the Do-er List. \n"
                    + "Parameters: -t TITLE [-d DESCRIPTION] [{[START]->[END]}] [-c CATEGORY] ...\n"
                    + "Example: add -t Do post-lecture quiz {today->tomorrow} -c CS2103";

    private static final String SHOWING_HELP_EDIT_MESSAGE =
            "edit: Edit an existing task in the Do-er List. \n"
                    + "Parameters: INDEX [-t TITLE] [-d DESCRIPTION] [{[START]->[END]}] [-c CATEGORY] ...\n"
                    + "edit 1 -t Do ST2334 quiz -c ST2334";

    private static final String SHOWING_HELP_MARK_MESSAGE =
            "mark: Mark a certain task as done in the Doer-list. \n"
                    + "Parameters: INDEX \n"
                    + "mark 5";

    private static final String SHOWING_HELP_UNMARK_MESSAGE =
            "unmark: Mark a certain task as undone in the Doer-list. \n"
                    + "Parameters: INDEX \n"
                    + "unmark 5";

    private static final String SHOWING_HELP_LIST_MESSAGE =
            "list: Show a list of all tasks in the Doer-list under the specific category. \n"
                    + "Parameters: [CATEGORY] \n"
                    + "list CS2101";

    private static final String SHOWING_HELP_FIND_MESSAGE =
            "find: Find tasks whose names contain any of the given keywords. \n"
                    + "Parameters: KEYWORD [MORE_KEYWORDS] \n"
                    + "find david";

    private static final String SHOWING_HELP_VIEW_MESSAGE =
            "view: View the task identified by the index number used in the last task listing. \n"
                    + "Parameters: INDEX \n"
                    + "view 3";

    private static final String SHOWING_HELP_DELETE_MESSAGE =
            "delete: Delete a specified task from the Do-erList. Irreversible. \n"
                    + "Parameters: INDEX \n"
                    + "delete 2";

    private static final String SHOWING_HELP_UNDO_MESSAGE =
            "undo: Undo the most recent operation which modify the data in the Doer-list. \n"
                    + "Parameters: \n"
                    + "undo";

    private static final String SHOWING_HELP_REDO_MESSAGE =
            "undo: Redo the most recent undo \n"
                    + "Parameters: \n"
                    + "redo";

    private static final String SHOWING_HELP_TASKDUE_MESSAGE =
            "taskdue: Find all tasks due on and before the date specified in the Doer-list. \n"
                    + "Parameters: END_DATE \n"
                    + "taskdue tomorrow";

    public String command = "";

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

        default:
            //EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        }
    }
}
