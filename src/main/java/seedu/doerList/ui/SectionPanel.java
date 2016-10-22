//@@author A0147978E
package seedu.doerList.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.doerList.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.doerList.commons.util.FxViewUtil;
import seedu.doerList.model.task.ReadOnlyTask;

/**
 * Panel containing the list of sections.
 */
public class SectionPanel extends UiPart {
    private static final String FXML = "SectionPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    
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
    
    public VBox getLayout() {
        return panel;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    public void setHeaderTitle(String headerName) {
        TaskListHeader.load(primaryStage, sectionHeaderPlaceholder, headerName);
    }

    public static SectionPanel load(Stage primaryStage, AnchorPane sectionPanelPlaceholder,
                                       List<ReadOnlyTask> taskList, String headerName, int displayIndexStart) {
        SectionPanel sectionPanel =
                UiPartLoader.loadUiPart(primaryStage, sectionPanelPlaceholder, new SectionPanel());
        sectionPanel.configure(taskList, headerName, displayIndexStart);
        return sectionPanel;
    }

    private void configure(List<ReadOnlyTask> taskList, String headerName, int displayIndexStart) {
        setTaskList(taskList, displayIndexStart);
        setHeaderTitle(headerName);
        addToPlaceholder();
    }
    
    /**
     * Set tasklist to the section and create TaskCard for each task.
     * 
     * @param taskList
     * @param displayIndexStart
     */
    private void setTaskList(List<ReadOnlyTask> taskList, int displayIndexStart) {
        taskCardControllers = new ArrayList<TaskCard>();
        int i = displayIndexStart;
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
    
    /**
     * Find the displayedIndex of a TaskCard.
     * 
     * @param target
     * @return displayedIndex
     */
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
    
    /**
     * Select the first Task and set it as active.
     * 
     * @param index the displayedIndex of the task
     */
    public void setFirstTaskToActive(int index) {
        if (index >= 0 && index < this.getTaskControllers().size()) {
            this.getTaskControllers().get(index).setActive();
            raise(new TaskPanelSelectionChangedEvent(this.getTaskControllers().get(index)));
        }
    }

}
