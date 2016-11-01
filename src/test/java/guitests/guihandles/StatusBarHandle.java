package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handler for the StatusBar of the UI
 */
public class StatusBarHandle extends GuiHandle {
    /** Some fields id in the UI. These IDs can be find in {@code /src/main/resources/view/*.fxml} */
    public static final String FILE_LOCATION_DISPLAY_ID = "#saveLocationField";
    
    public StatusBarHandle(GuiRobot guiRobot, Stage primaryStage){
        super(guiRobot, primaryStage, null);
    }

    public String getSaveLocation() {
        return getSavedLocationStatusBar().getText();
    }

    private StatusBar getSavedLocationStatusBar() {
        return (StatusBar) getNode(FILE_LOCATION_DISPLAY_ID);
    }
}
