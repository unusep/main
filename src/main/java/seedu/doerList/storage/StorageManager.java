package seedu.doerList.storage;

import com.google.common.eventbus.Subscribe;

import seedu.doerList.commons.core.ComponentManager;
import seedu.doerList.commons.core.Config;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.model.DoerListChangedEvent;
import seedu.doerList.commons.events.storage.DataSavingExceptionEvent;
import seedu.doerList.commons.events.storage.StoragePathChangedEvent;
import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.model.ReadOnlyDoerList;
import seedu.doerList.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of DoerList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlDoerListStorage doerListStorage;
    private JsonUserPrefStorage userPrefStorage;
    private ConfigStorage configStorage;


    public StorageManager(String doerListFilePath, 
            String userPrefsFilePath, String configFilePath) {
        super();
        this.doerListStorage = new XmlDoerListStorage(doerListFilePath);
        this.userPrefStorage = new JsonUserPrefStorage(userPrefsFilePath);
        this.configStorage = new JsonConfigStorage(configFilePath);
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
    
    // ================ Config method ==============================

    @Override
    public Config readConfig() throws DataConversionException {
        return configStorage.readConfig();
    }

    @Override
    public void saveConfig(Config config) throws IOException {
        configStorage.saveConfig(config);
    }

    // ================ DoerList methods ==============================

    @Override
    public String getDoerListFilePath() {
        return doerListStorage.getDoerListFilePath();
    }
    
    @Override
    public void setDoerListFilePath(String filePath) {
        doerListStorage.setDoerListFilePath(filePath);
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
    
    //@@author A0139168W
    @Override
    @Subscribe
    public void handleStoragePathChangedEvent(StoragePathChangedEvent event) throws DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, 
                "Data path changed, saving to file :" + event.getFilePath()));
        setDoerListFilePath(event.getFilePath());
        try {
            saveDoerList(event.getData());
            saveFilePathInConfig(event.getFilePath());
        } catch (IOException e) {
            // shouldn't happen here as we check the expection in save command
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    private void saveFilePathInConfig(String filePath) throws DataConversionException, IOException {
        Config currentConfig = configStorage.readConfig();
        currentConfig.setDoerListFilePath(filePath);
        configStorage.saveConfig(currentConfig);
    }
}
