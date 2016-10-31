package seedu.doerList.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.commons.util.FileUtil;
import seedu.doerList.model.ReadOnlyDoerList;

/**
 * A class to access DoerList data stored as an xml file on the hard disk.
 */
public class XmlDoerListStorage implements DoerListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlDoerListStorage.class);

    private String filePath;

    public XmlDoerListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getDoerListFilePath(){
        return filePath;
    }
    
    public void setDoerListFilePath(String filePath) throws InvalidPathException {
        try {
            this.filePath = filePath;
        } catch (InvalidPathException e) {
            throw new InvalidPathException(filePath, "Invalid Input");
        }
    }

    /**
     * Similar to {@link #readDoerList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDoerList> readDoerList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File doerListFile = new File(filePath);

        if (!doerListFile.exists()) {
            logger.info("DoerList file "  + doerListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyDoerList doerListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(doerListOptional);
    }

    /**
     * Similar to {@link #saveDoerList(ReadOnlyDoerList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveDoerList(ReadOnlyDoerList doerList, String filePath) throws IOException {
        assert doerList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableDoerList(doerList));
    }

    @Override
    public Optional<ReadOnlyDoerList> readDoerList() throws DataConversionException, IOException {
        return readDoerList(filePath);
    }

    @Override
    public void saveDoerList(ReadOnlyDoerList doerList) throws IOException {
        saveDoerList(doerList, filePath);
    }

}
