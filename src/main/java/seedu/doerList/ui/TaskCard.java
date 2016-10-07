package seedu.doerList.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.doerList.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

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
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        title.setText(task.getTitle().fullTitle);
        index.setText("#" + displayedIndex);
        // don't display description by default
        // TODO need to parse to human readable time interval
        // TODO currently just support floating task
        // TODO need to find way to display category
    }

    public VBox getLayout() {
        return taskPanel;
    }

    @Override
    public void setNode(Node node) {
        taskPanel = (VBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
