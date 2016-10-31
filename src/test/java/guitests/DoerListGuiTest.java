package guitests;

import guitests.guihandles.*;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;
import seedu.doerList.TestApp;
import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestUtil;
import seedu.doerList.testutil.TypicalTestTasks;
import seedu.doerList.ui.CategoryListCard;
import seedu.doerList.ui.CategorySideBar;
import seedu.doerList.ui.TaskCard;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        TypicalTestTasks.loadDoerListWithSampleData(ab);
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
     * Asserts the person shown in the card is same as the given person
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
}
