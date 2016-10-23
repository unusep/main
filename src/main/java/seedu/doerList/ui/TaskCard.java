//@@author A0147978E
package seedu.doerList.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.doerList.commons.util.FxViewUtil;
import seedu.doerList.model.task.ReadOnlyTask;

/** Card represents a specific task */
public class TaskCard extends UiPart {
    public static final String DESCRIPTION_FIELD_ID = "description";
    public static final String TIME_FIELD_ID = "taskTime";
    public static final String CATEGORY_FIELD_ID = "taskCategory";
    public static final String ACTIVE_STATUS_BACKGROUND = "-fx-background-color: #deeff5;";
    public static final String INACTIVE_STATUS_BACKGROUD = "-fx-background-color: #e6e6e6;";
    
    public static TaskCard selectedTaskController;
    
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskCard.fxml";

    private VBox rootPanel;
    private int displayIndex;
    
    @FXML
    private VBox taskPanel;
    @FXML
    private AnchorPane descriptionPanel;
    @FXML
    private Label title;
    @FXML
    private Label index;
    @FXML
    private VBox rightBar; // hold taskCategory Label and taskTime Label
    @FXML
    private Label taskCategory;
    @FXML
    private Label taskTime;

    private ReadOnlyTask task;
    private AnchorPane placeHolderPane;

    public TaskCard(){

    }

    public static TaskCard load(Stage primaryStage, AnchorPane taskCardPlaceholder,
                                    ReadOnlyTask task, int displayIndex){
        TaskCard taskCard =
                UiPartLoader.loadUiPart(primaryStage, taskCardPlaceholder, new TaskCard());
        taskCard.task = task;
        taskCard.configure(displayIndex);
        return taskCard;
    }

    public void configure(int displayIndex) {
        taskPanel.setUserData(this); // store the controller
        displayTask(displayIndex);
        this.displayIndex = displayIndex;
        addToPlaceholder();
    }

    private void displayTask(int displayIndex) {
        title.setText(task.getTitle().fullTitle);
        index.setText(displayIndex + "");
        taskCategory.setText(task.getCategories().toString());
        displayTime();
        displayCategories();
        // TODO need to parse to human readable time interval
    }
    
    /**
     * Display the time of task, if the task is floating task, simply remove the node.
     */
    private void displayTime() {
        if (task.isFloatingTask()) {
            rightBar.getChildren().remove(taskTime);
        } else {
            taskTime.setText(task.getTime());
        }
    }
    
    /**
     * Display the categories of task, if the task has no categories, simply remove the node.
     */
    private void displayCategories() {
        if (task.getCategories().isEmpty()) {
            rightBar.getChildren().remove(taskCategory);
        } else {
            taskCategory.setText(task.getCategories().toString());
        }
    }

    private void addToPlaceholder() {       
        placeHolderPane.getChildren().add(rootPanel);
    }

    public VBox getLayout() {
        return rootPanel;
    }
    
    public ReadOnlyTask getTask() {
        return task;
    }

    @Override
    public void setNode(Node node) {
        rootPanel = (VBox)node;
    }
    
    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        placeHolderPane = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    /**
     * Set this TaskCard as being selected.
     */
    public void setActive() {
        // to ensure that there is only one task activated
        if (selectedTaskController != null) {
            selectedTaskController.setInactive();
        }
        // change the background color
        taskPanel.setStyle(ACTIVE_STATUS_BACKGROUND);
        selectedTaskController = this;
        expandDetails();
    }
    
    /**
     * Unselect this TaskCard.
     */
    public void setInactive() {
        // change the background color
        taskPanel.setStyle(INACTIVE_STATUS_BACKGROUD);
        closeDetails();
    }
    
    /**
     * Expend the details of the task in UI.
     */
    private void expandDetails() {
        showDescription();
    }
    
    /**
     * Hide the details of the task in UI.
     */
    private void closeDetails() {
        hideDescription();
    }
    
    /**
     * Show the description of task in UI.
     */
    private void showDescription() {
        if (task.hasDescription()) {
            Text descriptionField = new Text();
            descriptionField.setId(DESCRIPTION_FIELD_ID);
            descriptionField.setText(task.getDescription().toString());
            descriptionPanel.getChildren().add(descriptionField);
            FxViewUtil.applyAnchorBoundaryParameters(descriptionField, 0, 0, 0, 0);
        }
    }
    
    /**
     * Hide the description of task in UI.
     */
    private void hideDescription() {
        descriptionPanel.getChildren().clear();
    }
    
    public int getDisplayIndex() {
        return displayIndex;
    }
    
    public static TaskCard getSeletedTaskCard() {
        return selectedTaskController;
    }
    
    public static void clearSelection() {
        if (selectedTaskController != null) {
            selectedTaskController.setInactive();
        }
        selectedTaskController = null;
    }
    
    
    @FXML
    public void handleClickAction(MouseEvent event) {
        logger.fine("Selection in task list panel changed to : '" + event.getSource() + "'");
        raise(new TaskPanelSelectionChangedEvent(this));
    }
      
}
