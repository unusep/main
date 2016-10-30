package guitests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.testutil.TestUtil;

public class SaveLocationCommandTest extends DoerListGuiTest{

    @Test
    public void change_save_location_successful() throws IllegalValueException, IOException {
        
        //Test if file and its content is in the SANDBOX_FOLDER
        commandBox.runCommand("saveto /st " + TestUtil.SANDBOX_FOLDER + " /n " + "sampleData");
        String expectedSaveLocation = getDataFileLocation();
        assertSaveLocationSuccess(TestUtil.SANDBOX_FOLDER + "sampleData.xml", expectedSaveLocation);
        
        //Test if file and its content is in a random folder
        commandBox.runCommand("saveto /st " + TestUtil.SANDBOX_FOLDER + "TestLocation /n " + "MyDoerList");
        expectedSaveLocation = getDataFileLocation(TestUtil.SANDBOX_FOLDER + "TestLocation\\MyDoerList.xml");
        assertSaveLocationSuccess(TestUtil.SANDBOX_FOLDER + "TestLocation\\MyDoerList.xml", expectedSaveLocation);
        
        //Test if no file name is specified
        commandBox.runCommand("saveto /st " + TestUtil.SANDBOX_FOLDER);
        expectedSaveLocation = getDataFileLocation(TestUtil.SANDBOX_FOLDER + "MyDoerList.xml");
        assertSaveLocationSuccess(TestUtil.SANDBOX_FOLDER + "MyDoerList.xml", expectedSaveLocation);
        
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
        
        File fileOfPath = new File(path);
        File fileOfExpectedPath = new File(expectedFilePath);
        assertEquals(fileOfPath, fileOfExpectedPath); //compares the content of the file
    }
}
