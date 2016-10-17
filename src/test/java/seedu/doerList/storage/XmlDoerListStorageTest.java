package seedu.doerList.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.doerList.testutil.TypicalTestTasks;
import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.commons.util.FileUtil;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.ReadOnlyDoerList;
import seedu.doerList.model.task.Task;
import seedu.doerList.storage.XmlDoerListStorage;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlDoerListStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlDoerListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readDoerList_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readDoerList(null);
    }

    private java.util.Optional<ReadOnlyDoerList> readDoerList(String filePath) throws Exception {
        return new XmlDoerListStorage(filePath).readDoerList(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDoerList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readDoerList("NotXmlFormatDoerList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveDoerList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempDoerList.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        DoerList original = td.getTypicalDoerList();
        XmlDoerListStorage xmlDoerListStorage = new XmlDoerListStorage(filePath);

        //Save in new file and read back
        xmlDoerListStorage.saveDoerList(original, filePath);
        ReadOnlyDoerList readBack = xmlDoerListStorage.readDoerList(filePath).get();
        assertEquals(original, new DoerList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.task8));
        original.removeTask(new Task(TypicalTestTasks.task2));
        xmlDoerListStorage.saveDoerList(original, filePath);
        readBack = xmlDoerListStorage.readDoerList(filePath).get();
        assertEquals(original, new DoerList(readBack));

    }

    @Test
    public void saveDoerList_nullDoerList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveDoerList(null, "SomeFile.xml");
    }

    private void saveDoerList(ReadOnlyDoerList doerList, String filePath) throws IOException {
        new XmlDoerListStorage(filePath).saveDoerList(doerList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveDoerList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveDoerList(new DoerList(), null);
    }


}
