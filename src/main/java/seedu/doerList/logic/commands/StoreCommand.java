//@@author A0139168W

package seedu.doerList.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.storage.StoragePathChangedEvent;
import seedu.doerList.commons.util.FileUtil;

/**
 * Adds a task to the to-do List.
 */
public class StoreCommand extends Command {


    public static final String COMMAND_WORD = "saveto";
    
        public static final String MESSAGE_USAGE = COMMAND_WORD 
                + ": Change the save location of your data." 
                + "Parameters: FILE_PATH \n"
                + "Example: " + COMMAND_WORD
                + " ~/Desktop/MyDoerList.xml";
    
        public static final String MESSAGE_SUCCESS = "Data saved under: %1$s";
        public static final String MESSAGE_INVALID_SAVE_LOCATION = "The save location is not found.\n"
                + "It is either protected or it contains invalid inputs.\n"
                + "Please enter another location.";

        private String savedFilePath;
        
        public StoreCommand(String savedFilePath) {
                    this.savedFilePath = savedFilePath;
                }
            
        public CommandResult execute() {
            try {
                File file = new File(savedFilePath);
                FileUtil.createIfMissing(file);
                EventsCenter.getInstance().post(new StoragePathChangedEvent(savedFilePath, model.getDoerList()));
                return new CommandResult(String.format(MESSAGE_SUCCESS, savedFilePath).toString());
            } catch (IOException e) {
                return new CommandResult(String.format(MESSAGE_INVALID_SAVE_LOCATION));
            }
        }

}
