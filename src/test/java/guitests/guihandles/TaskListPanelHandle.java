package guitests.guihandles;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.doerList.TestApp;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;
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
    public static final String SECTION_PANE_ID = "#sectionPanel";
    public static final String TASK_PANE_ID = "#tasksScrollPane";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }   
    
//    /**
//     * Clicks on the ListView.
//     */
//    public void clickOnTaskPanel() {
//        Point2D point= TestUtil.getScreenMidPoint(guiRobot.lookup(TASK_PANE_ID).query());
//        guiRobot.clickOn(point.getX(), point.getY());
//    }

    
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
        return guiRobot.lookup(SECTION_PANE_ID).queryAll();
    }
       
    public int getNumberOfSectionPanel() {
        return getAllSectionPanel().size();
    }

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
