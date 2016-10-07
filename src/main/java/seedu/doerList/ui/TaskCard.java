package seedu.doerList.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.doerList.model.task.ReadOnlyTask;

public class TaskCard extends UiPart {

    private static final String FXML = "TaskCard.fxml";

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
        title.setText(task.getTitle().fullTitle);
        index.setText("#" + displayIndex);
        // don't display description by default
        // TODO need to parse to human readable time interval
        // TODO currently just support floating task
        // TODO need to find way to display category
        addToPlaceholder();
    }
    
    private void addToPlaceholder() {
        
        placeHolderPane.getChildren().add(taskPanel);
    }

    public VBox getLayout() {
        return taskPanel;
    }

    @Override
    public void setNode(Node node) {
        taskPanel = (VBox)node;
    }
    
    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        placeHolderPane = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
