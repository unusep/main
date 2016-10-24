package seedu.doerList.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.model.ReadOnlyDoerList;

/**
 * Represents a storage for {@link seedu.doerList.model.DoerList}.
 */
public interface DoerListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getDoerListFilePath();

    /**
     * Returns DoerList data as a {@link ReadOnlyDoerList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDoerList> readDoerList() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDoerList} to the storage.
     * @param doerList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDoerList(ReadOnlyDoerList doerList) throws IOException;
    
    void changeSaveLocation(String saveLocation, String fileName) throws IOException;

}
