package seedu.doerList.storage;

import com.google.common.eventbus.Subscribe;

import seedu.doerList.commons.core.ComponentManager;
import seedu.doerList.commons.core.Config;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.model.DoerListChangedEvent;
import seedu.doerList.commons.events.storage.DataPathChangedEvent;
import seedu.doerList.commons.events.storage.DataSavingExceptionEvent;
import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.commons.util.ConfigUtil;
import seedu.doerList.commons.util.FileUtil;
import seedu.doerList.model.ReadOnlyDoerList;
import seedu.doerList.model.UserPrefs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of DoerList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static XmlDoerListStorage doerListStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String doerListFilePath, String userPrefsFilePath) {
        super();
        this.doerListStorage = new XmlDoerListStorage(doerListFilePath);
        this.userPrefStorage = new JsonUserPrefStorage(userPrefsFilePath);
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefStorage.saveUserPrefs(userPrefs);
    }


    // ================ DoerList methods ==============================

    @Override
    public String getDoerListFilePath() {
        return doerListStorage.getDoerListFilePath();
    }

    @Override
    public Optional<ReadOnlyDoerList> readDoerList() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + doerListStorage.getDoerListFilePath());

        return doerListStorage.readDoerList(doerListStorage.getDoerListFilePath());
    }

    @Override
    public void saveDoerList(ReadOnlyDoerList doerList) throws IOException {
        doerListStorage.saveDoerList(doerList, doerListStorage.getDoerListFilePath());
    }


    @Override
    @Subscribe
    public void handleDoerListChangedEvent(DoerListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveDoerList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    @Override
    @Subscribe
    public void handleDataPathChangedEvent(DataPathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data save location changing."));
        try {
            changeSaveLocation(event.getSaveLocation(), event.getFileName());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    public static void setSaveLocation(String saveLocation) throws InvalidPathException {
        doerListStorage.setDoerListFilePath(saveLocation);
    }
    
    public void changeSaveLocation(String saveLocation, String fileName) throws IOException, InvalidPathException {
        String filePath = saveLocation + "\\" + fileName + ".xml";
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        
        Config config = new Config();
        config.setDoerListName(fileName);
        config.setDoerListFilePath(saveLocation);
        
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
    }

}
