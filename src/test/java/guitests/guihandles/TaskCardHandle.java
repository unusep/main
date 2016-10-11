package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.ui.TaskCard;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "title";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
    
    protected String getContentFromText(String fieldId) { 
        return getTextFromText(fieldId, node);
    }

    public String getFullTitle() {
        return getTextFromLabel("#" + TITLE_FIELD_ID);
    }

    public String getDescription() {
        return getContentFromText("#" + TaskCard.DESCRIPTION_FIELD_ID);
    }

    public String getTime() {
        return getContentFromText("#" + TaskCard.TIME_FIELD_ID);
    }
    

    public boolean isSameTask(ReadOnlyTask task){
        return task.getTitle().fullTitle.equals(this.getFullTitle())
                && (!task.hasDescription() || task.getDescription().value.equals(this.getDescription()))
                && (task.isFloatingTask() || task.getTime().equals(this.getTime()));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullTitle().equals(handle.getFullTitle())
                    && getDescription().equals(handle.getDescription())
                    && getTime().equals(handle.getTime());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullTitle() + " " 
                + getDescription() + " " 
                + getTime() + " ";
                
    }
}
