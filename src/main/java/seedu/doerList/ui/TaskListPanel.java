package seedu.doerList.ui;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.ui.TaskPanelArrowKeyPressEvent;
import seedu.doerList.commons.events.ui.TaskPanelArrowKeyPressEvent.Direction;
import seedu.doerList.commons.util.FxViewUtil;
import seedu.doerList.model.task.ReadOnlyTask;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Panel containing the list of events.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private ScrollPane panel;
    private AnchorPane placeHolderPane;
    private ObservableList<ReadOnlyTask> allTasks;
    
    private ArrayList<SectionPanel> sectionPanelControllers;

    @FXML
    private VBox sectionList;
    
    @FXML
    private ScrollPane scrollPane;

    public TaskListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (ScrollPane) node;
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
        remapArrowKeysForScrollPane();
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
        // TODO add filter to category task based on timecategory
    }

    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
        FxViewUtil.applyAnchorBoundaryParameters(panel, 0.0, 0.0, 0.0, 0.0);
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
        if (TaskCard.getSeletedTaskCard() == null) {
            if (sectionPanelControllers.size() > 0) {
                sectionPanelControllers.get(0).setActive(0);
            }
        } else {
            TaskCard orginalSelection = TaskCard.getSeletedTaskCard();
            int sectionIndex = findSelectionSection(orginalSelection);
            if (sectionIndex != -1) {
                SectionPanel selectedSection = sectionPanelControllers.get(sectionIndex);
                int targetIndex = selectedSection.findSelectionIndex(orginalSelection);
                if (targetIndex == selectedSection.getTaskControllers().size() - 1) {
                    // last item in section
                    if (sectionIndex != sectionPanelControllers.size() - 1) {
                        sectionPanelControllers.get(sectionIndex + 1).setActive(0); 
                    }
                } else {
                    selectedSection.setActive(targetIndex + 1);
                }
            }
        }
    }
    
    private void selectionMoveUp() {
        if (TaskCard.getSeletedTaskCard() == null) {
            if (sectionPanelControllers.size() > 0) {
                sectionPanelControllers.get(0).setActive(0);
            }
        } else {
            TaskCard orginalSelection = TaskCard.getSeletedTaskCard();
            int sectionIndex = findSelectionSection(orginalSelection);
            if (sectionIndex != -1) {
                SectionPanel selectedSection = sectionPanelControllers.get(sectionIndex);
                int targetIndex = selectedSection.findSelectionIndex(orginalSelection);
                if (targetIndex == 0) {
                    // first item in section
                    if (sectionIndex != 0) {
                        SectionPanel previousSection = sectionPanelControllers.get(sectionIndex - 1);
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
    
    private void remapArrowKeysForScrollPane() {
        scrollPane.addEventFilter(KeyEvent.ANY, (KeyEvent event) -> {
            event.consume();
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (event.getCode()) {
                    case UP:
                        raise(new TaskPanelArrowKeyPressEvent(TaskPanelArrowKeyPressEvent.Direction.UP));
                        break;
                    case DOWN:
                        raise(new TaskPanelArrowKeyPressEvent(TaskPanelArrowKeyPressEvent.Direction.DOWN));
                        break;
                    default:
                        break;
                }
            }
            
        });
    }
    
    
    public void selectionChanged(TaskCard newSelectedCard) {
        newSelectedCard.setActive();
        ensureTaskVisible(newSelectedCard);
    }
    
    private void ensureTaskVisible(TaskCard taskcard) {
        double height = scrollPane.getContent().getBoundsInLocal().getHeight();
        double y = taskcard.getLayout().getParent().getBoundsInParent().getMaxY();
        int sectionIndex = findSelectionSection(taskcard);
        SectionPanel section = sectionPanelControllers.get(sectionIndex);
        int selectionIndex = section.findSelectionIndex(taskcard);
        
        // offset for the first element
        if (sectionIndex == 0 && selectionIndex == 0) {
            y -= taskcard.getLayout().getHeight();
        }
        
        // offset for the second section and so on
        if (sectionIndex > 0) {
            y += sectionPanelControllers.get(sectionIndex - 1).getLayout().getBoundsInParent().getMaxY();
        }
        
        // offset for the last element
        if ((sectionIndex == sectionPanelControllers.size() - 1)
                && (selectionIndex == section.getTaskControllers().size() - 1)) {
            y += taskcard.getLayout().getHeight();
        }

        // scrolling values range from 0 to 1
        scrollPane.setVvalue(y/height);

        // just for usability
        scrollPane.requestFocus();
    }


}
