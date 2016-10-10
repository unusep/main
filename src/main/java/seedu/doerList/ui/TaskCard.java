package seedu.doerList.ui;


import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.ui.JumpToListRequestEvent;
import seedu.doerList.commons.events.ui.TaskPanelArrowKeyPressEvent;
import seedu.doerList.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.doerList.model.task.ReadOnlyTask;

public class TaskCard extends UiPart {
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
    private AnchorPane timeIntervalPanel;
    @FXML
    private Label title;
    @FXML
    private Label index;

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
        title.setText(task.getTitle().fullTitle);
        index.setText("#" + displayIndex);
        // don't display description by default
        // TODO need to parse to human readable time interval
        // TODO currently just support floating task
        // TODO need to find way to display category
        this.displayIndex = displayIndex;
        addToPlaceholder();
    }
    
    private void addToPlaceholder() {
        
        placeHolderPane.getChildren().add(rootPanel);
    }

    public VBox getLayout() {
        return rootPanel;
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
    
    public void setActive() {
        // to ensure that there is only one task activated
        if (selectedTaskController != null) {
            selectedTaskController.setInactive();
        }
        taskPanel.setStyle("-fx-background-color: #deeff5;");
        selectedTaskController = this;
    }
    
    public void setInactive() {
        taskPanel.setStyle("-fx-background-color: #e6e6e6;");
    }
    
    public int getDisplayIndex() {
        return displayIndex;
    }
    
    public static TaskCard getSeletedTaskCard() {
        return selectedTaskController;
    }
    
    
    @FXML
    public void handleClickAction(MouseEvent event) {
        logger.fine("Selection in task list panel changed to : '" + event.getSource() + "'");
        raise(new TaskPanelSelectionChangedEvent(this));
    }
    
    
    
}
