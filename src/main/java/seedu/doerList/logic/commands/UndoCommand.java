package seedu.doerList.logic.commands;

import seedu.doerList.model.undo.UndoManager.OperationFailException;

public class UndoCommand extends Command{

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " the most recent operation that modified the data";
    public static final String MESSAGE_UNDO_SUCCESS = "Undo the last operation successfully";
    public static final String MESSAGE_UNDO_FAILURE = "Undo operation is not available in this situation";

    @Override
    public CommandResult execute() {
        try {
            model.undo();
            return new CommandResult(MESSAGE_UNDO_SUCCESS);
        } catch (OperationFailException e) {
            return new CommandResult(MESSAGE_UNDO_FAILURE);
        } 
    }
}