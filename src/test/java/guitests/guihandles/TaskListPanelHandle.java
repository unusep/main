package guitests.guihandles;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.doerList.TestApp;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.testutil.TestUtil;
import seedu.doerList.ui.TaskCard;
import seedu.doerList.ui.TaskListPanel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the person list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#taskPanel";

    private static final String TASK_LIST_PANEL_ID = "#tasksScrollPane";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

//    public List<ReadOnlyTask> getSelectedTask() {
//        ListView<ReadOnlyTask> personList = getListView();
//        return personList.getSelectionModel().getSelectedItems();
//    }

    public TaskListPanel getTaskListPanel() {
        return (TaskListPanel)(getNode(TASK_LIST_PANEL_ID).getUserData());
    }
    
    public List<TaskCard> getTaskCards() {
        return getAllCardNodes().stream().map((Node node) -> {
            Object data = node.getUserData();
            assert data instanceof TaskCard;
            return (TaskCard) data;
        }).collect(Collectors.toList());
    }
    
    public List<ReadOnlyTask> getAllTasksInTaskCards() {
        return getTaskCards().stream().map((TaskCard tc) -> {
            return tc.getTask();
        }).collect(Collectors.toList());
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param tasks A list of person in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, tasks);
    }
    
//    /**
//     * Clicks on the ListView.
//     */
//    public void clickOnListView() {
//        Point2D point= TestUtil.getScreenMidPoint(getListView());
//        guiRobot.clickOn(point.getX(), point.getY());
//    }

    /**
     * Returns true if the {@code persons} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> taskInList = getAllTasksInTaskCards();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > taskInList.size()){
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!taskInList.get(startPosition + i).getTitle().fullTitle.equals(tasks[i].getTitle().fullTitle)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getNumberOfTask()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + getNumberOfTask() + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition + 1;
            guiRobot.interact(() -> getTaskListPanel().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getTaskCardHandle(i + startPosition), tasks[i])) {
                return false;
            }
        }
        return true;
    }


    public TaskCardHandle navigateToTask(String title) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<TaskCard> taskcard = getTaskCards()
                .stream().filter((TaskCard tc) -> {
                    return tc.getTask().getTitle().fullTitle.equals(title);
                }).findAny();
        if (!taskcard.isPresent()) {
            throw new IllegalStateException("Name not found: " + title);
        }

        return navigateToTask(taskcard.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(TaskCard taskcard) {
        guiRobot.interact(() -> {
            getTaskListPanel().scrollTo(taskcard.getDisplayIndex());
            guiRobot.sleep(150);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(taskcard.getTask());
    }


    /**
     * Gets a task from the list by index
     */
    public ReadOnlyTask getTask(int index) {
        return getAllTasksInTaskCards().get(index);
    }

    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle(new Task(getAllTasksInTaskCards().get(index)));
    }

    public TaskCardHandle getTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Stream<Node> taskCardStream = nodes.stream()
                .filter(n -> new TaskCardHandle(guiRobot, primaryStage, n).isSameTask(task));
        Optional<Node> taskCardNode = taskCardStream.findFirst();
        if (taskCardNode.isPresent()) {
            return new TaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTask() {
        return getAllCardNodes().size();
    }
}
