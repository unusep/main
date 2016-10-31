package seedu.doerList.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.doerList.commons.core.Config;
import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.commons.util.ConfigUtil;
import seedu.doerList.model.ReadOnlyDoerList;

public class JsonConfigStorage implements ConfigStorage {

    private String configFilePath;
     
    public JsonConfigStorage(String path) {
        this.configFilePath = path;
    }

    @Override
    public Config readConfig() throws DataConversionException, IOException {
        Optional<Config> tryConfig = ConfigUtil.readConfig(this.configFilePath);
        if (tryConfig.isPresent()) {
            return tryConfig.get();
        } else {
            throw new IOException("File Path invalid");
        }
    }

    @Override
    public void saveConfig(Config config) throws IOException {
        ConfigUtil.saveConfig(config, configFilePath);
    }

}
