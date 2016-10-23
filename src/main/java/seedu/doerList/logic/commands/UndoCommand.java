package seedu.doerList.logic.commands;

import seedu.doerList.logic.UndoRedoManager.DataPair;
import seedu.doerList.logic.UndoRedoManager.NotUndoableException;

public class UndoCommand extends Command{

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " the most recent operation that modified the data";
    public static final String MESSAGE_UNDO_SUCCESS = "Undo the last operation successfully";
    public static final String MESSAGE_UNDO_FAILURE = "Undo operation is not available in this situation";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        try {
            DataPair data = undoRedoManager.getToUndo();
            model.resetData(data.getList());
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_UNDO_SUCCESS);
        } catch (NotUndoableException nue) {
            return new CommandResult(MESSAGE_UNDO_FAILURE);
        }
    }
}
