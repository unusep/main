package seedu.doerList.logic.commands;

public class UndoCommand extends Command{

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Undo the most recent operation that modified the data";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        return null;
    }
}
