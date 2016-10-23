package seedu.doerList.logic;

import seedu.doerList.logic.commands.*;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.Model;
import seedu.doerList.model.ReadOnlyDoerList;

public class UndoRedoManager {

    private DataPair toUndo;

    /**
     * Update the state to be undone before a command that mutates data is called
     */
    public void storeToUndo(Command command, Model model) {
        if (isMutated(command)) {
            toUndo = new DataPair(command, model);
        }
    }

    /**
     * return the sate to be undone
     * @throws NotUndoableException
     */
    public DataPair getToUndo() throws NotUndoableException {
        if (toUndo == null) {
            throw new NotUndoableException();
        }
        DataPair getUndo = this.toUndo;
        //clear the data as we only allow 1 undo
        this.toUndo = null;
        return getUndo;
    }

    /**
     * Return true if the command mutates the data
     */
    public boolean isMutated (Command command) {
        return command instanceof AddCommand ||
               command instanceof EditCommand ||
               command instanceof DeleteCommand ||
               command instanceof MarkCommand ||
               command instanceof UnmarkCommand;
    }

    public class NotUndoableException extends Exception{}

    //===================================
    /**
     * Inner class to store the data and command before a command mutates data.
     * Command is also stored as some commands do not mutate the data
     * And also because of the implementation of LogicManager
     */
    public class DataPair {

        private ReadOnlyDoerList doerList;
        private Command command;

        DataPair(Command command, Model model) {
            this.command = command;
            this.doerList = new DoerList(model.getDoerList());
        }

        public Command getCommand() {
            return this.command;
        }

        public ReadOnlyDoerList getList() {
            return this.doerList;
        }
    }
}
