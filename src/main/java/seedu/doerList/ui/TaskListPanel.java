//@@author A0147978E
package seedu.doerList.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
import seedu.doerList.commons.util.TimeUtil;
import seedu.doerList.model.category.BuildInCategory;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Panel containing the list of sections with tasks.
 */
public class TaskListPanel extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    
    /** Define the criteria to categorized the display tasks */
    public static final BuildInCategory[] categorizedBy = {
            BuildInCategoryList.DUE,
            BuildInCategoryList.TODAY, BuildInCategoryList.NEXT, BuildInCategoryList.INBOX,
            BuildInCategoryList.COMPLETE
    };
    
    private static final String FXML = "TaskListPanel.fxml";
    private ScrollPane panel;
    private AnchorPane placeHolderPane;
    private ObservableList<ReadOnlyTask> allTasks;
    
    private ArrayList<SectionPanel> sectionPanelControllers;

    @FXML
    private VBox sectionList;
    
    @FXML
    private ScrollPane tasksScrollPane;

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
        displayTasks();
        addToPlaceholder();
        addListener(allTasks);
        tasksScrollPane.setUserData(this); // store the controller
        remapArrowKeysForScrollPane();
    }
    
    /**
     * Add listener to {@code taskList}, when taskList changed, UI will be refreshed
     * 
     * @param taskList
     */
    private void addListener(ObservableList<ReadOnlyTask> taskList) {
        taskList.addListener((ListChangeListener.Change<? extends ReadOnlyTask> c) -> {
            displayTasks();
        });
    }

    public void displayTasks() {
        logger.info("TaskPanelUIController : Drawing the task panel UI");
        // clear selection first
        TaskCard.clearSelection();
        // categorized task based on `categorizedBy`
        Map<BuildInCategory, List<ReadOnlyTask>> categorized_tasks = 
                categorizedByBuildInCategory(allTasks);
        // prepare
        sectionPanelControllers = new ArrayList<SectionPanel>();
        sectionList.getChildren().clear();
        // generate new UI
        generateSectionsUI(categorized_tasks);
    }
    
    /**
     * Generate and draw different sections UI according to the 
     * {@code Map<BuildInCategory, List<ReadOnlyTask>> categorized_tasks}
     * 
     * @param categorized_tasks
     * @param displayIndexStart
     */
    private void generateSectionsUI(Map<BuildInCategory, List<ReadOnlyTask>> categorized_tasks) {
        int displayIndexStart = 1;
        for(BuildInCategory c : categorizedBy) {  
            if (categorized_tasks.get(c) == null) continue;
            // create new sections
            AnchorPane container_temp = new AnchorPane();
            sectionList.getChildren().add(container_temp);
            SectionPanel controller = SectionPanel.load(primaryStage, container_temp, 
                    categorized_tasks.get(c), c.categoryName, displayIndexStart);
            sectionPanelControllers.add(controller);
            
            displayIndexStart += categorized_tasks.get(c).size();
        }
    }

    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
        FxViewUtil.applyAnchorBoundaryParameters(panel, 0.0, 0.0, 0.0, 0.0);
    }
    
    /**
     * Move the selection of task according to the parameter {@code Direction}.
     * 
     * @param direction
     */
    public void selectionMove(Direction direction) {
        switch(direction) {
            case UP:
                selectionMoveUp();
                break;
            case DOWN:
                selectionMoveDown();
                break;
            default:
                break;
        }
    }
    
    /**
     * Move down the selection.
     */
    private void selectionMoveDown() {
        if (TaskCard.getSeletedTaskCard() == null && sectionPanelControllers.size() > 0) {
            // no selection currently, thus select the first task
            sectionPanelControllers.get(0).setTaskToActive(0);
        } else {
            TaskCard originalSelection = TaskCard.getSeletedTaskCard();
            int sectionIndex = findSelectionSection(originalSelection);
            if (sectionIndex != -1) {
                selectNextTaskCard(originalSelection, sectionIndex);
            }
        }
    }

    /**
     * Select next task card according to the {@code originaSelection} and {@code sectionIndex}
     * 
     * @param originalSelection
     * @param sectionIndex
     */
    private void selectNextTaskCard(TaskCard originalSelection, int sectionIndex) {
        assert sectionIndex < sectionPanelControllers.size();
        assert originalSelection != null;
        
        SectionPanel selectedSection = sectionPanelControllers.get(sectionIndex);
        int targetIndex = selectedSection.findSelectionIndex(originalSelection);
        
        if (targetIndex == selectedSection.getTaskControllers().size() - 1
                && sectionIndex != sectionPanelControllers.size() - 1) {
            // current selection is the last item in section
            sectionPanelControllers.get(sectionIndex + 1).setTaskToActive(0); 
        } else {
            selectedSection.setTaskToActive(targetIndex + 1);
        }
    }
    
    /**
     * Move up the selection.
     */
    private void selectionMoveUp() {
        if (TaskCard.getSeletedTaskCard() == null 
                && sectionPanelControllers.size() > 0) {
            // no selection currently, thus select the first task
            sectionPanelControllers.get(0).setTaskToActive(0);
        } else {
            TaskCard originalSelection = TaskCard.getSeletedTaskCard();
            int sectionIndex = findSelectionSection(originalSelection);
            if (sectionIndex != -1) {
                selectPrevTaskCard(originalSelection, sectionIndex);
            }
        }
    }

    /**
     * Select previous task card according to the {@code originaSelection} and {@code sectionIndex}
     * 
     * @param originalSelection
     * @param sectionIndex
     */
    private void selectPrevTaskCard(TaskCard originalSelection, int sectionIndex) {
        assert sectionIndex < sectionPanelControllers.size();
        assert originalSelection != null;
        
        SectionPanel selectedSection = sectionPanelControllers.get(sectionIndex);
        int targetIndex = selectedSection.findSelectionIndex(originalSelection);
        if (targetIndex == 0 && sectionIndex != 0) {
            SectionPanel previousSection = sectionPanelControllers.get(sectionIndex - 1);
            previousSection.setTaskToActive(previousSection.getTaskControllers().size() - 1); 
        } else {
            selectedSection.setTaskToActive(targetIndex - 1);
        }
    }
    
    /**
     * Find the section index that the {@code TaskCard target} belongs to.
     * If the TaskCard doesn't exist in any section, -1 is returned.
     * 
     * @param target
     * @return index of the section
     */
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
    
    /**
     * Find the {@TaskCard} by using the displayedIndex.
     * 
     * @param displayedIndex
     * @return
     */
    private TaskCard findTaskCardByIndex(int displayedIndex) {
        for(SectionPanel s : sectionPanelControllers) {
            for(TaskCard t : s.getTaskControllers()) {
                if (t.getDisplayIndex() == displayedIndex) {
                    return t;
                }
            }
        }
        return null;
    }
    
    /**
     * Map the Arrow Key in keyboard to response the scroll up/down event.
     */
    private void remapArrowKeysForScrollPane() {
        tasksScrollPane.addEventFilter(KeyEvent.ANY, (KeyEvent event) -> {
            event.consume();
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                responseArrowKeysEvent(event);
            }           
        });
    }

    /**
     * Raise {@code TaskPanelArrowKeyPressEvent} according to different arrow key pressed
     * 
     * @param event
     */
    private void responseArrowKeysEvent(KeyEvent event) {
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
    
    /**
     * Scroll to the given {@code targetIndex} and select it.
     * 
     * @param targetIndex
     */
    public void scrollTo(int targetIndex) {
        TaskCard target = findTaskCardByIndex(targetIndex);
        selectionChanged(target);
    }
    
    
    /**
     * Change the current selection of task to {@code newSelectedCard}.
     * 
     * @param newSelectedCard
     */
    public void selectionChanged(TaskCard newSelectedCard) {
        newSelectedCard.setActive();
        ensureTaskVisible(newSelectedCard);
    }
    
    /**
     * Ensure that a {@code TaskCard} is visible in the scroll panel.
     * 
     * @param taskcard
     */
    private void ensureTaskVisible(TaskCard taskcard) {
        double height = tasksScrollPane.getContent().getBoundsInLocal().getHeight();
        double y = taskcard.getLayout().getHeight();
        int sectionIndex = findSelectionSection(taskcard);
        SectionPanel section = sectionPanelControllers.get(sectionIndex);
        int selectionIndex = section.findSelectionIndex(taskcard);
        
        // offset for the first element
        if (sectionIndex == 0 && selectionIndex == 0) {
            y -= taskcard.getLayout().getHeight();
        }
        
        // offset for the second section and so on
        int k = sectionIndex;
        while (k > 0) {
            y += sectionPanelControllers.get(k - 1).getLayout().getHeight();
            k--;
        }
        
        // offset inside the section list
        int i = 0;
        while (!sectionPanelControllers.get(sectionIndex).getTaskControllers().get(i).equals(taskcard)) {
            y += sectionPanelControllers.get(sectionIndex).getTaskControllers().get(i).getLayout().getHeight();
            i++;
        }
        
        // offset for the last element
        if ((sectionIndex == sectionPanelControllers.size() - 1)
                && (selectionIndex == section.getTaskControllers().size() - 1)) {
            y = height;
        }
        
        // scrolling values range from 0 to 1
        tasksScrollPane.setVvalue(y/height);

        // just for usability
        tasksScrollPane.requestFocus();
    }
    
    /**
     * Categorized tasks based on {@code categorizedBy}.
     * 
     * @param tasks not modifiable
     * @return Map contain buildInCategory to List of tasks
     */
    public static Map<BuildInCategory, List<ReadOnlyTask>> categorizedByBuildInCategory(
        ObservableList<ReadOnlyTask> tasks) {
        HashMap<BuildInCategory, List<ReadOnlyTask>> results = new HashMap<BuildInCategory, List<ReadOnlyTask>>();
        for(BuildInCategory c : categorizedBy) {
            List<ReadOnlyTask> filteredTasks = new ArrayList<ReadOnlyTask>(tasks.filtered(c.getPredicate()));
            // remove complete task in other category except COMPLETE
            if (c != BuildInCategoryList.COMPLETE) {
                filteredTasks = filteredTasks.stream().filter((task) -> { 
                    return !BuildInCategoryList.COMPLETE.getPredicate().test(task);
                }).collect(Collectors.toList());
            }
            if (filteredTasks.size() > 0) {
                // sort the list before put in
                filteredTasks.sort(getTaskComparator());
                results.put(c, filteredTasks);
            }
        }
        return results;
    }

    /**
     * Get comparator for task
     * 
     * @return
     */
    private static Comparator<? super ReadOnlyTask> getTaskComparator() {
        return (t1, t2) -> {
            if (t1.isFloatingTask() && t2.isFloatingTask()) {
                return t1.getTitle().fullTitle.compareTo(t2.getTitle().fullTitle);
            } else {
                LocalDateTime t1_represent = t1.hasEndTime() ? t1.getEndTime().value : 
                    TimeUtil.getEndOfDay(LocalDateTime.now()).plusYears(2000);
                LocalDateTime t2_represent = t2.hasEndTime() ? t2.getEndTime().value : 
                    TimeUtil.getEndOfDay(LocalDateTime.now()).plusYears(2000);
                t1_represent = t1.hasStartTime() ? t1.getStartTime().value : t1_represent;
                t2_represent = t2.hasStartTime() ? t2.getStartTime().value : t2_represent;
                if (t1_represent.equals(t2_represent)) {
                    return 0;
                }
                return t1_represent.isBefore(t2_represent) ? -1 : 1;
            }
        };
    }
    
    /**
     * Get the displayed index when the tasks is categorized by 
     * {@link #categorizedByBuildInCategory(ObservableList)}.
     * 
     * @param index
     * @param tasks
     * @return
     * @throws TaskNotFoundException
     */
    public static ReadOnlyTask getDisplayedIndexInUI(int index, 
            ObservableList<ReadOnlyTask> tasks) throws TaskNotFoundException {
        Map<BuildInCategory, List<ReadOnlyTask>> results = categorizedByBuildInCategory(tasks);
        int i = 1;
        for(BuildInCategory c : categorizedBy) {
            if (results.get(c) == null) continue;
            for(ReadOnlyTask t : results.get(c)) {
                if (index == i) {
                    return t;
                }
                i++;
            }
        }
        throw new TaskNotFoundException();
    }

}
