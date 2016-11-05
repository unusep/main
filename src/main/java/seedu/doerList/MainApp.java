package seedu.doerList;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.doerList.commons.core.Config;
import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.core.Version;
import seedu.doerList.commons.events.ui.ExitAppRequestEvent;
import seedu.doerList.commons.exceptions.DataConversionException;
import seedu.doerList.commons.util.ConfigUtil;
import seedu.doerList.commons.util.FileUtil;
import seedu.doerList.commons.util.StringUtil;
import seedu.doerList.logic.Logic;
import seedu.doerList.logic.LogicManager;
import seedu.doerList.model.*;
import seedu.doerList.storage.Storage;
import seedu.doerList.storage.StorageManager;
import seedu.doerList.ui.Ui;
import seedu.doerList.ui.UiManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing DoerList ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getDoerListFilePath(), 
                config.getUserPrefsFilePath(), 
                initConfigFilePath(getApplicationParameter("config")));

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyDoerList> doerListOptional;
        ReadOnlyDoerList initialData;
        try {
            doerListOptional = storage.readDoerList();
            if(!doerListOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty DoerList");
            }
            initialData = doerListOptional.orElse(new DoerList());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DoerList");
            initialData = new DoerList();
        } catch (FileNotFoundException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty DoerList");
            initialData = new DoerList();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }
    
    protected String initConfigFilePath(String configFilePath) {
        if (configFilePath == null) {
            return Config.DEFAULT_CONFIG_FILE;
        }
        try {
            FileUtil.createIfMissing(new File(configFilePath));
            return configFilePath;
        } catch (IOException e) {
            return Config.DEFAULT_CONFIG_FILE;
        }
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = initConfigFilePath(configFilePath);

        if(configFilePathUsed.equals(configFilePath)) {
            logger.info("Custom Config file specified " + configFilePath);
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty DoerList");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting DoerList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping doerList ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
