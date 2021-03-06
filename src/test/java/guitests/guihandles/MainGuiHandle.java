package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.doerList.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public TaskListPanelHandle getTaskListPanel() {
        return new TaskListPanelHandle(guiRobot, primaryStage);
    }
    
    public CategorySideBarHandle getCategorySideBar() {
        return new CategorySideBarHandle(guiRobot, primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }
    
    public StatusBarHandle getStatusBar() {
        return new StatusBarHandle(guiRobot, primaryStage);
    }

}
