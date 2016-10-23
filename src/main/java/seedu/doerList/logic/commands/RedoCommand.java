package seedu.doerList.logic.commands;

import seedu.doerList.logic.UndoRedoManager.DataPair;
import seedu.doerList.logic.UndoRedoManager.NotRedoableException;

public class RedoCommand extends Command{

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " the most recent operation that modified the data";
    public static final String MESSAGE_REDO_FAILURE = "Redo operation is not available in this situation";

    public RedoCommand() {}

    @Override
    public CommandResult execute() {
        try {
            DataPair data = undoRedoManager.getToRedo();
            Command command = data.getCommand();
            //add the redo command to the undoRedoManager
            undoRedoManager.storeToUndo(command, model);
            return command.execute();
        } catch (NotRedoableException nue) {
            return new CommandResult(MESSAGE_REDO_FAILURE);
        }
    }
}
