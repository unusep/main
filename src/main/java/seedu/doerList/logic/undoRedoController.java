package seedu.doerList.logic;

import seedu.doerList.logic.commands.Command;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.Model;
import seedu.doerList.model.ReadOnlyDoerList;

public class undoRedoController {



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
