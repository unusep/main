package seedu.doerList.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.doerList.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of events.
 */
public class SectionPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(SectionPanel.class);
    private static final String FXML = "SectionPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    private TaskListHeader header;

    @FXML
    private ListView<ReadOnlyTask> taskListView;
    @FXML
    private AnchorPane sectionHeaderPlaceholder;

    public SectionPanel() {
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
    
    public void setHeaderTitle(String title) {
        //
    }
    
    public void setHeaderTitle() {
        header = TaskListHeader.load(primaryStage, sectionHeaderPlaceholder, "Test");
    }

    public static SectionPanel load(Stage primaryStage, AnchorPane sectionPanelPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        SectionPanel sectionPanel =
                UiPartLoader.loadUiPart(primaryStage, sectionPanelPlaceholder, new SectionPanel());
        sectionPanel.configure(taskList);
        return sectionPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        setHeaderTitle();
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        public TaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

}
