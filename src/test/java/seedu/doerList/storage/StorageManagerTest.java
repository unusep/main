package seedu.doerList.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.doerList.testutil.TypicalTestTasks;
import seedu.doerList.commons.core.Config;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.ReadOnlyDoerList;
import seedu.doerList.model.UserPrefs;
import seedu.doerList.storage.StorageManager;

import static org.junit.Assert.assertEquals;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setup() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"), getTempFilePath("config"));
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link JsonUserPrefStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefStorageTest} class.
     */

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void doerListReadSave() throws Exception {
        DoerList original = new TypicalTestTasks().getTypicalDoerList();
        storageManager.saveDoerList(original);
        ReadOnlyDoerList retrieved = storageManager.readDoerList().get();
        assertEquals(original, new DoerList(retrieved));
        //More extensive testing of DoerList saving/reading is done in XmlDoerListStorageTest
    }
    
    @Test
    public void configReadSave() throws Exception {
        Config orginal = new Config();
        storageManager.saveConfig(orginal);
        Config retrieved = storageManager.readConfig();
        assertEquals(orginal, retrieved);
        //More extensive testing of DoerList saving/reading is done in ConfigUtilTest
    }


}
