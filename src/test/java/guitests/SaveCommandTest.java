//@@author A0147978E
package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.commands.SaveCommand;

public class SaveCommandTest extends DoerListGuiTest {

    @Test
    public void save_validPath_successful() throws IllegalValueException {
        String validPath = "data/test1.xml";
        assertSaveSuccess(validPath);          
    }
    
    @Test
    public void save_invalidPath_errorMessage() throws IllegalValueException {      
        commandBox.runCommand("saveto ");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_INVALID_SAVE_LOCATION));
    }
       
    private void assertSaveSuccess(String location) {  
        commandBox.runCommand("saveto " + location);
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, location));
        assertEquals(statusBar.getSaveLocation(), location);      
    }
       
}
