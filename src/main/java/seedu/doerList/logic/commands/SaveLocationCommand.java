package seedu.doerList.logic.commands;

import java.nio.file.InvalidPathException;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.storage.DataPathChangedEvent;
import seedu.doerList.commons.events.ui.JumpToCategoryEvent;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.storage.StorageManager;

public class SaveLocationCommand extends Command  {
    public static final String COMMAND_WORD = "saveto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the save location of your data. "
            + "Parameters: /s SAVE_LOCATION [/n FILE_NAME] \n"
            + "Example: " + COMMAND_WORD
            + " /st C:\\Documents\\MyDoerList /n MyNewDoerList";

    public static final String MESSAGE_SUCCESS = "Data saved under: %1$s";
    public static final String MESSAGE_INVALID_SAVE_LOCATION = "The save location is not found.\n"
            + "It is either protected or it contains invalid inputs.\n"
            + "Please enter another location.";
    public static final String DEFAULT_SAVE_FILE_NAME = "MyDoerList";
    
    private final String saveLocation;
    private String fileName;
    
    public SaveLocationCommand(String saveLocation, String fileName) {
        this.saveLocation = saveLocation;
        this.fileName = fileName;
    }
    
    public CommandResult execute() {
        assert model != null;
        assert storage != null;
        if (fileName == null)
            fileName = DEFAULT_SAVE_FILE_NAME;
        try {
            StorageManager.setSaveLocation(saveLocation);
            EventsCenter.getInstance().post(new DataPathChangedEvent(saveLocation, fileName));
            return new CommandResult(String.format(MESSAGE_SUCCESS, saveLocation));
        } catch (InvalidPathException e) {
            return new CommandResult(String.format(MESSAGE_INVALID_SAVE_LOCATION));
        }
        
    }
}