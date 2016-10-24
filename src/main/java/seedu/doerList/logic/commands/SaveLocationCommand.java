package seedu.doerList.logic.commands;

import java.io.File;
import java.nio.file.InvalidPathException;

public class SaveLocationCommand extends Command {
    public static final String COMMAND_WORD = "saveto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the save location of your data. "
            + "Parameters: /s SAVE_LOCATION /n FILE_NAME \n"
            + "Example: " + COMMAND_WORD
            + " /st C:\\Documents\\MyDoerList /n MyNewDoerList";

    public static final String MESSAGE_SUCCESS = "Data saved under: %1$s \nName of file: %2$s";
    public static final String MESSAGE_INVALID_SAVE_LOCATION = "The save location is not found.\n"
            + "It is either protected or it does not exist.\n"
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
        if (fileName == null)
            fileName = DEFAULT_SAVE_FILE_NAME;
        try {
            storage.setSaveLocation(saveLocation);
            model.changeSaveLocation(saveLocation, fileName);
            return new CommandResult(String.format(MESSAGE_SUCCESS, (Object)(new String[] {saveLocation, fileName})));
        } catch (InvalidPathException e) {
            return new CommandResult(String.format(MESSAGE_INVALID_SAVE_LOCATION));
        }
        
    }
}