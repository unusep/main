package guitests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.testutil.TestUtil;

public class SaveLocationCommandTest extends DoerListGuiTest{

    @Test
    public void change_save_location_successful() throws IllegalValueException {
        
        commandBox.runCommand("saveto /st " + TestUtil.SANDBOX_FOLDER + " /n " + "sampleData.xml");
        String expectedSaveLocation = getDataFileLocation();
        assertSaveLocationSuccess(TestUtil.SANDBOX_FOLDER + "\\sampleData.xml", expectedSaveLocation);
        
        // invalid command
        commandBox.runCommand("savesto /st C:\\Documents /n MyList");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     * Validating the location of data has been changed successfully.
     * Validating the rest of data has been updated accordingly
     * 
     * @param file path
     */
    private void assertSaveLocationSuccess(String path, String expectedFilePath) {    
        assertEquals(path, expectedFilePath);
    }
}
