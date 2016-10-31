package seedu.doerList.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import seedu.doerList.commons.core.Config;
import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.commons.util.ConfigUtil;
import seedu.doerList.commons.util.FileUtil;

public class JsonConfigStorage implements ConfigStorage {

    public final String configFilePath;
     
    public JsonConfigStorage(String path) {
        this.configFilePath = path;
    }

    @Override
    public Config readConfig() throws DataConversionException {
        Optional<Config> tryConfig = ConfigUtil.readConfig(this.configFilePath);
        return tryConfig.get();
    }

    @Override
    public void saveConfig(Config config) throws IOException {
        assert config != null;
        assert configFilePath != null;

        File file = new File(configFilePath);
        FileUtil.createIfMissing(file);
        ConfigUtil.saveConfig(config, configFilePath);
    }

}
