//@@author A0147978E
package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.doerList.TestApp;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the person list.
 */
public class TaskListPanelHandle extends GuiHandle {
    /** Some fields id in the UI. These IDs can be find in {@code /src/main/resources/view/*.fxml} */
    public static final String SECTION_PANE_ID = "sectionPanel";
    public static final String TASK_PANE_ID = "tasksScrollPane";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }   
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnMidOfTaskPanel(int height) {
        Point2D point= TestUtil.getScreenTopMidPoint(guiRobot.lookup("#" + TASK_PANE_ID).query(), height);
        guiRobot.clickOn(point.getX(), point.getY());
        guiRobot.sleep(200);
    }
    
    /**
     * Press the {@code KeyCode.UP} key in the keyboard.
     */
    public void useUpArrowKey() {
        guiRobot.type(KeyCode.UP).sleep(500);
    }
    
    /**
     * Press the {@code KeyCode.DOWN} key in the keyboard.
     */
    public void useDownArrowKey() {
        guiRobot.type(KeyCode.DOWN).sleep(500);
    }
    
    /**
     * Get a sectionPanelHandle that represents the given {@code targetCategory}.
     * If there is not such {@code targetCategory} in current GUI, {@code null} will be returned.
     * 
     * @param targetCategory
     * @return SectionPanelHandle
     */
    public SectionPanelHandle getSectionPanelHandle(TestCategory targetCategory) {
        Set<Node> nodes = getAllSectionPanel();
        Stream<Node> sectionPanelStream = nodes.stream()
                .filter(n -> new SectionPanelHandle(guiRobot, primaryStage, n).isSameCategory(targetCategory));
        Optional<Node> sectionPanelNode = sectionPanelStream.findFirst();
        if (sectionPanelNode.isPresent()) {
            return new SectionPanelHandle(guiRobot, primaryStage, sectionPanelNode.get());
        } else {
            return null;
        }
    }
    
    protected Set<Node> getAllSectionPanel() {
        return guiRobot.lookup("#" + SECTION_PANE_ID).queryAll();
    }
       
    public int getNumberOfSectionPanel() {
        return getAllSectionPanel().size();
    }

    /**
     * Check whether the tasks, which are categorized by BuildInCategory, 
     * display in the same order as {@code expectedCategorized}.
     * 
     * @param expectedCategorized
     * @return boolean
     */
    public boolean isListMatching(List<TestCategory> expectedCategorized) {
        int indexStart = 1;
        for(TestCategory c : expectedCategorized) {
            SectionPanelHandle panel = getSectionPanelHandle(c);
            if (panel == null) {
                return false;
            } else {
                panel.isListMatching(c.getPreDefinedTasks(), indexStart);
            }
            indexStart += c.getPreDefinedTasks().size();
        }
        return true;
    }

}
