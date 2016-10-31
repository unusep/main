package seedu.doerList.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.doerList.MainApp;
import seedu.doerList.commons.core.ComponentManager;
import seedu.doerList.commons.core.Config;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.model.DoerListChangedEvent;
import seedu.doerList.commons.events.storage.DataSavingExceptionEvent;
import seedu.doerList.commons.events.storage.StoragePathChangedEvent;
import seedu.doerList.commons.events.ui.CategorySelectionChangedEvent;
import seedu.doerList.commons.events.ui.JumpToCategoryEvent;
import seedu.doerList.commons.events.ui.JumpToListRequestEvent;
import seedu.doerList.commons.events.ui.ShowHelpRequestEvent;
import seedu.doerList.commons.events.ui.TaskPanelArrowKeyPressEvent;
import seedu.doerList.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.doerList.commons.util.StringUtil;
import seedu.doerList.logic.Logic;
import seedu.doerList.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/doerList_32.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code =================================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }
    
    //@@author A0147978E
    @Subscribe
    private void handleDoerListChangedEvent(DoerListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().displayTasks(); // redraw the task panel
        mainWindow.getCategorySideBar().refreshCategories(); // to update category count
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }
    
    //@@author A0147978E
    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().scrollTo(event.targetIndex + 1); // index is offset by 1
    }
    
    //@@author A0147978E
    @Subscribe
    private void handleTaskPanelArrowKeyPressEvent(TaskPanelArrowKeyPressEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().selectionMove(event.getDirection()); // move to the task card
    }
    
    //@@author A0147978E
    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().selectionChanged(event.getNewSelectedCard());
    }
    
    //@@author A0147978E
    @Subscribe
    private void handleCategorySelectionChangedEvent(CategorySelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getCategorySideBar().clearOtherSelectionExcept(event.getNewSelection()); // move to the selection
        logic.setPredicateForTaskList(event.getNewSelection().getPredicate()); // set predicate for taskpanel to filter task
    }
    
    //@@author A0147978E
    @Subscribe
    private void handleJumpToCategoryEvent(JumpToCategoryEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getCategorySideBar().refreshCategories(); // refresh categories UI
        mainWindow.getCategorySideBar().categoryScrollTo(event.target); // scroll to the category
    }
    
    //@@author A0139168W
    @Subscribe
    private void handleStoragePathChangedEvent(StoragePathChangedEvent event) {
        assert event != null;
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().displayTasks(); // redraw the task panel
        mainWindow.getCategorySideBar().refreshCategories(); // to update category count
        mainWindow.getStatusBarFooter().setSaveLocation(event.getFilePath()); //update footerbar
    }

}
