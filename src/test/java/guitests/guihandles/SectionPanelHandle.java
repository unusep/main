//@@author A0147978E
package guitests.guihandles;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.ui.TaskListPanel;

/**
 * Provides a handle to section in the tasklist.
 */
public class SectionPanelHandle extends GuiHandle {
    /** Some fields id in the UI. These IDs can be find in {@code /src/main/resources/view/*.fxml} */
    public static final String SECTION_HEADER_ID = "titleField";
    public static final String CARD_PANE_ID = "taskPanel";  
    public static final String TASK_LIST_SCROLLPANE = "tasksScrollPane";
    
    private Node node;

    public SectionPanelHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getHeaderTitle() {
        return getTextFromLabel("#" + SECTION_HEADER_ID);
    }   

    /**
     * Determine whether this handle represents the same category as the parameter.
     * 
     * @param c the expected category
     * @return boolean
     */
    public boolean isSameCategory(Category c){
        return c.categoryName.equals(this.getHeaderTitle());
    }
    
    protected int getNumTaskNode() {
        return getAllCardNodes().size();
    }
    
    protected Set<Node> getAllCardNodes() {
        return guiRobot.from(node).lookup("#" + CARD_PANE_ID).queryAll();
    }
    
    protected Optional<Node> getTaskNodeByTitle(String title) {
        return getAllCardNodes().stream().filter((n) -> {
            TaskCardHandle tempHandle =  new TaskCardHandle(guiRobot, primaryStage, n);
            return tempHandle.getFullTitle().equals(title);
        }).findAny();
    }

    /**
     * Get the taskCardHandle which represents the parameter {@code task}.
     * If the there is no such task in the current UI, the method will return {@code null}.
     * 
     * @param task
     * @return TaskCardHandle
     */
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
    

    /**
     * Return true if the tasks displayed in UI is in the same order as the parameter {@code tasks}.
     * {@code indexStart} indicates that the tasks should start from the index.
     * 
     * @param tasks
     * @param indexStart
     * @return boolean
     * @throws IllegalArgumentException
     */
    public boolean isListMatching(List<TestTask> tasks, int indexStart) throws IllegalArgumentException {
        if (tasks.size() != getNumTaskNode()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + getNumTaskNode() + " tasks");
        }
        for (int i = 0; i < tasks.size(); i++) {
            // navigate to the task
            Optional<Node> nodeOp = getTaskNodeByTitle(tasks.get(i).getTitle().fullTitle);
            if (!nodeOp.isPresent()) {
                return false;
            }
            final int scrollTo = i + indexStart;
            guiRobot.interact(() -> getTaskListPanelController().scrollTo(scrollTo));
            guiRobot.sleep(200);
            // get the tasks panel inside the section if any
            TaskCardHandle tch = this.getTaskCardHandle(tasks.get(i));
            if (tch == null) {
                // if cannot find, the information is not correct
                return false;
            } else {
                // validate the index to make sure it is in correct order
                if (!tch.getDisplayIndex().equals(String.valueOf(i + indexStart))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SectionPanelHandle) {
            SectionPanelHandle handle = (SectionPanelHandle) obj;
            return getHeaderTitle().equals(handle.getHeaderTitle());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "__" + getHeaderTitle() + "__";
                
    }
    
    /**
     * Get the controller of the TaskListPanel in the current UI.
     * This is implemented by using {@code getUserData()} of a {@code node} in the UI.
     * 
     * @return TaskListPanel
     */
    public TaskListPanel getTaskListPanelController() {
        TaskListPanel taskPaneController = (TaskListPanel)guiRobot.lookup("#" + TASK_LIST_SCROLLPANE).query().getUserData();
        return taskPaneController;
    }
}
