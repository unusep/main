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
import seedu.doerList.commons.events.ui.TaskPanelArrowKeyPressEvent.Direction;
import seedu.doerList.model.task.ReadOnlyTask;

import java.util.ArrayList;
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
    
    private ArrayList<SectionPanel> sectionPanelControllers;

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
        sectionPanelControllers = new ArrayList<SectionPanel>();
        sectionList.getChildren().clear();
        AnchorPane container_temp = new AnchorPane();
        sectionList.getChildren().add(container_temp);
        SectionPanel controller = SectionPanel.load(primaryStage, container_temp, allTasks);
        sectionPanelControllers.add(controller);
    }

    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
    }
    
    public void selectionMove(Direction direction) {
        switch(direction) {
            case UP:
                selectionMoveUp();
                break;
            case DOWN:
                selectionMoveDown();
                break;
        }
    }
    
    private void selectionMoveDown() {
        if (TaskCard.selectedTaskController == null) {
            if (sectionPanelControllers.size() > 0) {
                sectionPanelControllers.get(0).setActive(0);
            }
        } else {
            TaskCard orginalSelection = TaskCard.selectedTaskController;
            int selectionIndex = findSelectionSection(orginalSelection);
            if (selectionIndex != -1) {
                SectionPanel selectedSection = sectionPanelControllers.get(selectionIndex);
                int targetIndex = selectedSection.findSelectionIndex(orginalSelection);
                if (targetIndex == selectedSection.getTaskControllers().size() - 1) {
                    // last item in section
                    if (selectionIndex != sectionPanelControllers.size() - 1) {
                        sectionPanelControllers.get(selectionIndex + 1).setActive(0); 
                    }
                } else {
                    selectedSection.setActive(targetIndex + 1);
                }
            }
        }
    }
    
    private void selectionMoveUp() {
        if (TaskCard.selectedTaskController == null) {
            if (sectionPanelControllers.size() > 0) {
                sectionPanelControllers.get(0).setActive(0);
            }
        } else {
            TaskCard orginalSelection = TaskCard.selectedTaskController;
            int selectionIndex = findSelectionSection(orginalSelection);
            if (selectionIndex != -1) {
                SectionPanel selectedSection = sectionPanelControllers.get(selectionIndex);
                int targetIndex = selectedSection.findSelectionIndex(orginalSelection);
                if (targetIndex == 0) {
                    // first item in section
                    if (selectionIndex != 0) {
                        SectionPanel previousSection = sectionPanelControllers.get(selectionIndex - 1);
                        previousSection.setActive(previousSection.getTaskControllers().size() - 1); 
                    }
                } else {
                    selectedSection.setActive(targetIndex - 1);
                }
            }
        }
    }
    
    private int findSelectionSection(TaskCard target) {
        int selectionIndex = -1;
        for(SectionPanel sp: sectionPanelControllers) {
            if (sp.getTaskControllers().contains(target)) {
                selectionIndex++;
                break;
            }
            selectionIndex++;
        }
        return selectionIndex;
    }
    
    
    public void selectionChanged(TaskCard newSelectedCard) {
        newSelectedCard.setActive();
    }

}
