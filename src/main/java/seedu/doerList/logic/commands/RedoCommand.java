package seedu.doerList.logic.commands;

import seedu.doerList.logic.HistoryManager.DataPair;
import seedu.doerList.logic.HistoryManager.NotRedoableException;

public class RedoCommand extends Command{

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " the most recent operation that modified the data";
    public static final String MESSAGE_REDO_FAILURE = "Redo operation is not available in this situation";

    public RedoCommand() {}

    @Override
    public CommandResult execute() {
        try {
            DataPair data = historyManager.getToRedo();
            Command command = data.getCommand();
            //add the redo command to the historyManager
            historyManager.storeToUndo(command, model);
            return command.execute();
        } catch (NotRedoableException nue) {
            return new CommandResult(MESSAGE_REDO_FAILURE);
        }
    }
}
