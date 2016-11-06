package seedu.doerList.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.doerList.commons.core.Config;
import seedu.doerList.commons.events.model.DoerListChangedEvent;
import seedu.doerList.commons.events.storage.DataSavingExceptionEvent;
import seedu.doerList.commons.events.storage.StoragePathChangedEvent;
import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.model.ReadOnlyDoerList;
import seedu.doerList.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends DoerListStorage, UserPrefsStorage, ConfigStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;
    
    @Override
    Config readConfig() throws DataConversionException;

    @Override
    void saveConfig(Config config) throws IOException;
    
    @Override
    String getDoerListFilePath();

    @Override
    Optional<ReadOnlyDoerList> readDoerList() throws DataConversionException, FileNotFoundException;

    @Override
    void saveDoerList(ReadOnlyDoerList doerList) throws IOException;

    /**
     * Saves the current version of the doerList to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleDoerListChangedEvent(DoerListChangedEvent abce);
    
    void handleStoragePathChangedEvent(StoragePathChangedEvent event) throws DataConversionException;
}
