package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String TIMEINTERVAL_FIELD_ID = "#timeInterval";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullTitle() {
        return getTextFromLabel(TITLE_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getTimeInterval() {
        return getTextFromLabel(TIMEINTERVAL_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getFullTitle().equals(task.getTitle().fullTitle) && 
                ((!task.hasDescription() && getDescription().isEmpty()) || task.getDescription().value.equals(getDescription())) &&
                ((!task.hasTimeInterval() && getTimeInterval().isEmpty()) || (task.getTimeInterval().toString().equals(getTimeInterval())));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullTitle().equals(handle.getFullTitle())
                    && getDescription().equals(handle.getDescription())
                    && getTimeInterval().equals(handle.getTimeInterval());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullTitle() + " " + getDescription() + " " + getTimeInterval();
    }
}
