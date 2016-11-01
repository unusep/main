package seedu.doerList.storage;

import java.io.IOException;

import seedu.doerList.commons.core.Config;
import seedu.doerList.commons.exceptions.DataConversionException;

public interface ConfigStorage { 
    
    Config readConfig() throws DataConversionException;

    void saveConfig(Config config) throws IOException;
}
