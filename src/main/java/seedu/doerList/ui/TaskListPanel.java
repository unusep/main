package seedu.doerList.ui;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of events.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    private ObservableList<ReadOnlyTask> allTasks;

    @FXML
    private VBox sectionList;

    public TaskListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.allTasks = taskList;
        taskListPanel.configure();
        return taskListPanel;
    }

    private void configure() {
        // TODO create different section `overdue` `today` .. here
        // Need a new data structure to store
        displayTasks();
        addToPlaceholder();
        addListener(allTasks);
    }
    
    private void addListener(ObservableList<ReadOnlyTask> taskList) {
        taskList.addListener((ListChangeListener.Change<? extends ReadOnlyTask> c) -> {
            displayTasks();
        });
    }

    private void displayTasks() {
        sectionList.getChildren().clear();
        AnchorPane container_temp = new AnchorPane();
        sectionList.getChildren().add(container_temp);
        SectionPanel.load(primaryStage, container_temp, allTasks);
    }

    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
    }

}
