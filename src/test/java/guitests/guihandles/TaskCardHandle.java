//@@author A0147978E
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.ui.TaskCard;
import seedu.doerList.ui.TaskCardRecurringBar;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    /** Some fields id in the UI. These IDs can be find in {@code /src/main/resources/view/*.fxml} */
    private static final String TITLE_FIELD_ID = "title";
    private static final String INDEX_FIELD_ID = "index";
    
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

    public String getDisplayIndex() {
        return getTextFromLabel("#" + INDEX_FIELD_ID);
    }
    
    public String getDescription() {
        return getContentFromText("#" + TaskCard.DESCRIPTION_FIELD_ID);
    }

    public String getTime() {
        return getTextFromLabel("#" + TaskCard.TIME_FIELD_ID);
    }
    
    public String getRecurringText() {
        return getTextFromLabel("#" + TaskCardRecurringBar.INTERVAL_FIELD_ID);
    }
    
    public String getCategory() {
        return getTextFromLabel("#" + TaskCard.CATEGORY_FIELD_ID);
    }
    
    /**
     * Check whether the TaskCard represents the same task as it in the parameter {@code task}.
     * 
     * @param task
     * @return boolean
     */
    public boolean isSameTask(ReadOnlyTask task){
        return task.getTitle().fullTitle.equals(this.getFullTitle())
                && (!task.hasDescription() || task.getDescription().value.equals(this.getDescription()))
                && (task.isFloatingTask() || task.getTime().equals(this.getTime()))
                && (task.getCategories().isEmpty() || task.getCategories().toString().equals(this.getCategory()))
                && (!task.hasRecurring() || task.getRecurring().toHumanReadable().equals(this.getRecurringText()));
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
