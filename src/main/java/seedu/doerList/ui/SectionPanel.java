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
import seedu.doerList.commons.util.FxViewUtil;
import seedu.doerList.model.task.ReadOnlyTask;

import java.util.ArrayList;
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
    
    private ArrayList<TaskCard> taskCardControllers;

    @FXML
    private VBox taskListBox;
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
        setTaskList(taskList);
        setHeaderTitle();
        addToPlaceholder();
    }
    
    private void setTaskList(ObservableList<ReadOnlyTask> taskList) {
        taskCardControllers = new ArrayList<TaskCard>();
        int i = 1;
        for(ReadOnlyTask task: taskList) {
            AnchorPane container_temp = new AnchorPane();
            taskListBox.getChildren().add(container_temp);
            TaskCard taskCard = TaskCard.load(primaryStage, container_temp, task, i);
            taskCardControllers.add(taskCard);
            FxViewUtil.applyAnchorBoundaryParameters(taskCard.getLayout(), 0.0, 0.0, 0.0, 0.0);
            i++;
        }
    }


    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
    }

    public ArrayList<TaskCard> getTaskControllers() {
        return taskCardControllers;
    }

    public void scrollTo(int index) {
        
    }
    
    public int findSelectionIndex(TaskCard target) {
        int taskCardIndex = -1;
        for(TaskCard tc : this.getTaskControllers()) {
            if (tc == target) {
                taskCardIndex++;
                break;
            }
            taskCardIndex++;
        }
        return taskCardIndex;
    }
    
    public void setActive(int index) {
        if (index >= 0 && index < this.getTaskControllers().size()) {
            this.getTaskControllers().get(index).setActive();
            raise(new TaskPanelSelectionChangedEvent(this.getTaskControllers().get(index)));
        }
    }

}
