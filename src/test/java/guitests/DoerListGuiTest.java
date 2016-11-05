package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.CategoryCardHandle;
import guitests.guihandles.CategorySideBarHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.stage.Stage;
import seedu.doerList.TestApp;
import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestUtil;
import seedu.doerList.testutil.TypicalTestTasks;
import seedu.doerList.ui.TaskCard;

/**
 * A GUI Test class for DoerList.
 */
public abstract class DoerListGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTasks td = new TypicalTestTasks();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TaskListPanelHandle taskListPanel;
    protected CategorySideBarHandle categorySideBar;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    protected StatusBarHandle statusBar;
    private Stage stage;

    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        // reset category name
        BuildInCategoryList.resetBuildInCategoryPredicate();
        // reset selection
        TaskCard.clearSelection();
        
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            mainMenu = mainGui.getMainMenu();
            taskListPanel = mainGui.getTaskListPanel();
            categorySideBar = mainGui.getCategorySideBar();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            statusBar = mainGui.getStatusBar();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
        
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected DoerList getInitialData() {
        DoerList ab = TestUtil.generateEmptyDoerList();
        td.loadDoerListWithSampleData(ab);
        return ab;
    }

    /**
     * Override this in child classes to set the data file location.
     * @return
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyTask person, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, person));
    }
    
    /**
     * Asserts the category shown in the category sidebar is same as the given category
     */
    public void assertMatching(TestCategory category, CategoryCardHandle card) {
        assertTrue(TestUtil.compareCardAndTestCategory(card, category));
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     * @param expected
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
    
    /**
     * To check whether the UI match the given data
     * 
     * @param expectedDisplayTaskPanel
     * @param expectedBuildInCategoryList
     * @param expectedCategoryList
     */
    protected void checkUiMatching(List<TestCategory> expectedDisplayTaskPanel,
            List<TestCategory> expectedBuildInCategoryList, List<TestCategory> expectedCategoryList) {
        // confirm the list now contains accurate buildInCategory and count
        assertTrue(categorySideBar.isBuildInCategoryListMatching(expectedBuildInCategoryList));
        // confirm the list now contains accurate category and count
        assertTrue(categorySideBar.categoryListMatching(expectedCategoryList));
        
        // confirm the list now contains all tasks
        assertTrue(taskListPanel.isListMatching(expectedDisplayTaskPanel));
    }
}
