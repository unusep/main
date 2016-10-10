package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.doerList.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String START_TIME_FIELD_ID = "#startTime";
    private static final String END_TIME_FIELD_ID = "#endTime";

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

    public String getStartTime() {
        return getTextFromLabel(START_TIME_FIELD_ID);
    }
    
    public String getEndTime() {
        return getTextFromLabel(END_TIME_FIELD_ID); 
    }

    public boolean isSameTask(ReadOnlyTask task){
        return task.getTitle().fullTitle.equals(this.getFullTitle())
                && (!task.hasDescription() || task.getDescription().value.equals(this.getDescription()))
                && (!task.hasStartTime() || task.getStartTime().toString().equals(this.getStartTime()))
                && (!task.hasEndTime() || task.getEndTime().toString().equals(this.getEndTime()));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullTitle().equals(handle.getFullTitle())
                    && getDescription().equals(handle.getDescription())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndTime().equals(handle.getEndTime());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullTitle() + " " 
                + getDescription() + " " 
                + getStartTime() + " "
                + getEndTime();
                
    }
}
