package seedu.doerList.logic.commands;

import seedu.doerList.model.undo.UndoManager.OperationFailException;

public class RedoCommand extends Command{

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " the most recent operation that modified the data";
    public static final String MESSAGE_REDO_SUCCESS = "Redo the last operation successfully";
    public static final String MESSAGE_REDO_FAILURE = "Redo operation is not available in this situation";

    @Override
    public CommandResult execute() {
        try {
            model.redo();
            return new CommandResult(MESSAGE_REDO_SUCCESS);
        } catch (OperationFailException e) {
            return new CommandResult(MESSAGE_REDO_FAILURE);
        }       
    }
}