package seedu.doerList.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.doerList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doerList.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.doerList.commons.core.Config;
import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.events.model.DoerListChangedEvent;
import seedu.doerList.commons.events.ui.JumpToListRequestEvent;
import seedu.doerList.commons.events.ui.ShowHelpRequestEvent;
import seedu.doerList.commons.util.ConfigUtil;
import seedu.doerList.commons.util.TimeUtil;
import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.logic.commands.CommandResult;
import seedu.doerList.logic.commands.DeleteCommand;
import seedu.doerList.logic.commands.EditCommand;
import seedu.doerList.logic.commands.FindCommand;
import seedu.doerList.logic.commands.HelpCommand;
import seedu.doerList.logic.commands.ListCommand;
import seedu.doerList.logic.commands.MarkCommand;
import seedu.doerList.logic.commands.RedoCommand;
import seedu.doerList.logic.commands.SaveCommand;
import seedu.doerList.logic.commands.TaskdueCommand;
import seedu.doerList.logic.commands.UndoCommand;
import seedu.doerList.logic.commands.UnmarkCommand;
import seedu.doerList.logic.commands.ViewCommand;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.Model;
import seedu.doerList.model.ModelManager;
import seedu.doerList.model.ReadOnlyDoerList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.Description;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Recurring;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.Title;
import seedu.doerList.model.task.TodoTime;
import seedu.doerList.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.doerList.storage.StorageManager;
import seedu.doerList.storage.XmlFileStorage;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyDoerList latestSavedDoerList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(DoerListChangedEvent abce) {
        latestSavedDoerList = new DoerList(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempDoerListFile = saveFolder.getRoot().getPath() + "TempDoerList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        String tempConfig = saveFolder.getRoot().getPath() + "TempConfig.json";
        logic = new LogicManager(model, new StorageManager(tempDoerListFile, tempPreferencesFile, tempConfig));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedDoerList = new DoerList(model.getDoerList()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

    //@@author A0139401N
    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'doerList' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyDoerList, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new DoerList(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal doerList data are same as those in the {@code expectedDoerList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedDoerList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyDoerList expectedDoerList,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedDoerList, model.getDoerList());
        assertEquals(expectedDoerList, latestSavedDoerList);
    }


    //@@author A0139401N 
    @Test
    public void execute_unknownCommandWord() throws Exception { 
        String unknownCommand = "uicfhmowqewca"; 
        assertCommandBehavior(unknownCommand, Messages.MESSAGE_UNKNOWN_COMMAND);  
    } 

    //@@author A0140905M
    @Test
    public void execute_help_noArgs() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }
    //@@author A0140905M


    //@@author A0139401N
    @Test
    public void execute_help_invalidArgs() throws Exception {
        assertCommandBehavior("help sdfdsf", HelpCommand.INVALID_HELP_MESSAGE);
        assertCommandBehavior("help 123", HelpCommand.INVALID_HELP_MESSAGE);
        assertCommandBehavior("help hmmm hahaha", HelpCommand.INVALID_HELP_MESSAGE);
    }
    //@@author


    //@@author A0140905M
    @Test
    public void execute_help_correctArgs() throws Exception {
        assertCommandBehavior("help add", AddCommand.MESSAGE_USAGE);
        assertCommandBehavior("help edit", EditCommand.MESSAGE_USAGE);
        assertCommandBehavior("help mark", MarkCommand.MESSAGE_USAGE);
        assertCommandBehavior("help unmark", UnmarkCommand.MESSAGE_USAGE);
        assertCommandBehavior("help list", ListCommand.MESSAGE_USAGE);
        assertCommandBehavior("help find", FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("help view", ViewCommand.MESSAGE_USAGE);
        assertCommandBehavior("help delete", DeleteCommand.MESSAGE_USAGE);
        assertCommandBehavior("help redo", RedoCommand.MESSAGE_USAGE);
        assertCommandBehavior("help undo", UndoCommand.MESSAGE_USAGE);
        assertCommandBehavior("help taskdue", TaskdueCommand.MESSAGE_USAGE);
        assertCommandBehavior("help saveto", SaveCommand.MESSAGE_USAGE);
    }
    //@@author

    @Test
    public void execute_exit() throws Exception {
        //assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
    public void execute_clear() throws Exception {
        //TestDataHelper helper = new TestDataHelper();
        //model.addPerson(helper.generateTask(1));
        //model.addPerson(helper.generateTask(2));
        //model.addPerson(helper.generateTask(3));

        //assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new DoerList(), Collections.emptyList());
    }
    

    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add", expectedMessage);
        assertCommandBehavior(
                "add /t", expectedMessage);
        assertCommandBehavior(
                "add /t     ", expectedMessage);

    }

    //@@author A0147978E
    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add /t valid title /d valid description /s invalid format /e 2011-10-12 13:00 /c valid_category", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
        assertCommandBehavior(
                "add /t valid title /d valid description /s 2011-10-12 12:00 /e invalid format /c valid_category", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
        assertCommandBehavior(
                "add /t valid title /d valid description /s 2011-10-12 12:00 /e 2011-10-11 12:00 /c valid_category", TodoTime.TIME_INTERVAL_CONSTRAIN);
    }


    //@@author
    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task[] inputs = {
                helper.taskWithAttribute(true, true, true, false, true),
                helper.taskWithAttribute(true, true, true, false, false),
                helper.taskWithAttribute(true, true, false, false, false),
                helper.taskWithAttribute(true, false, false, false, true),
                helper.taskWithAttribute(true, false, false, false, false),
                helper.taskWithAttribute(false, true, false, false, false),
                helper.taskWithAttribute(false, false, true, false, false),
                helper.taskWithAttribute(false, false, false, false, true),
                helper.taskWithAttribute(false, true, true, false, false),
                helper.taskWithAttribute(false, false, false, false, false)
        };
        for(Task toBeAdded : inputs) {
            DoerList expectedAB = new DoerList();
            expectedAB.addTask(toBeAdded);
            // execute command and verify result
            model.resetData(new DoerList());
            assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                    String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                    expectedAB,
                    expectedAB.getTaskList());
        }
    }
    
    @Test
    public void execute_addRecurring_fail() throws DuplicateTaskException, Exception {
        TestDataHelper helper = new TestDataHelper();
        Task toAdded = helper.taskWithAttribute(false, false, false, true, false);
        assertCommandBehavior(helper.generateAddCommand(toAdded), Recurring.MESSAGE_RECURRING_STARTEND_CONSTRAINTS);
    }
    
    //@@author A0139401N
    @Test
    public void execute_addRecurring_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task[] inputs = {
                helper.taskWithAttribute(true, true, true, true, true),
                helper.taskWithAttribute(true, true, true, true, false),
                helper.taskWithAttribute(false, true, true, true, false),
                helper.taskWithAttribute(false, true, true, true, true)
        };
        for(Task toBeAdded : inputs) {
            DoerList expectedAB = new DoerList();
            expectedAB.addTask(toBeAdded);
            // executes the command and verifies the result
            model.resetData(new DoerList());
            assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                    String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                    expectedAB,
                    expectedAB.getTaskList());
        }
    }
    
    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateTask(1);
        DoerList expectedAB = new DoerList();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // person already in internal doerList
 
        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }

    //@@author A0147978E
    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        DoerList expectedDL = helper.generateDoerList(2);
        List<? extends ReadOnlyTask> expectedList = expectedDL.getTaskList();

        // prepare doerList state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                String.format(ListCommand.MESSAGE_SUCCESS, "All"),
                expectedDL,
                expectedList);
    }

    //@@author A0147978E
    @Test
    public void execute_list_buildInCategory() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task Today1 = helper.generateTaskWithTime(1, TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(8).toString(), 
                TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12).toString(), null); // today
        Task Next1 = helper.generateTaskWithTime(2, TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(8).plusDays(1).toString(), 
                TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12).plusDays(1).toString(), null); // tomorrow
        Task Next2 = helper.generateTaskWithTime(3, TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(8).plusDays(5).toString(), 
                TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12).plusDays(5).toString(), null); // next 5 days
        Task Next3 = helper.generateTaskWithTime(3, TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(8).plusDays(7).toString(), 
                TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12).plusDays(7).toString(), null); // next 7 days
        Task Inbox1 = helper.generateTaskWithTime(4, null, null, null); // inbox
        Task Complete1 = helper.generateTaskWithCategory(5); // complete
        Complete1.addBuildInCategory(BuildInCategoryList.COMPLETE);

        // prepare doerList state
        helper.addToModel(model, Arrays.asList(Today1, Next1, Next2, Next3, Inbox1, Complete1));

        // Test ALL
        assertCategoryListed(Arrays.asList(Today1, Next1, Next2, Next3, Inbox1, Complete1),
                BuildInCategoryList.ALL, BuildInCategoryList.ALL.categoryName);
        // Test Next
        assertCategoryListed(Arrays.asList(Next1, Next2, Next3),
                BuildInCategoryList.NEXT, BuildInCategoryList.NEXT.categoryName);
        // Test Inbox
        assertCategoryListed(Arrays.asList(Inbox1),
                BuildInCategoryList.INBOX, BuildInCategoryList.INBOX.categoryName);
        // Test complete
        assertCategoryListed(Arrays.asList(Complete1),
                BuildInCategoryList.COMPLETE, BuildInCategoryList.COMPLETE.categoryName);
    }

    //@@author A0147978E
    @Test
    public void execute_list_category() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithCategory(1, new Category("CA1"), new Category("CA2"));
        Task task2 = helper.generateTaskWithCategory(2, new Category("CA1"));
        Task task3 = helper.generateTaskWithCategory(3, new Category("CA2"));
        Task task4 = helper.generateTaskWithCategory(4);

        // prepare doerList state
        helper.addToModel(model, Arrays.asList(task1, task2, task3, task4));

        // list unknown category
        CommandResult result = logic.execute("list CA3");
        assertEquals(Messages.MESSAGE_CATEGORY_NOT_EXISTS, result.feedbackToUser);

        // list All
        //Execute the command
        result = logic.execute("list");
        assertEquals(Arrays.asList(task1, task2, task3, task4), logic.getFilteredTaskList());
        // List CA1
        assertCategoryListed(Arrays.asList(task1, task2), new Category("CA1"), "CA1");
        // list CA2
        assertCategoryListed(Arrays.asList(task1, task3), new Category("CA2"), "CA2");
    }

    //@@author A0147978E
    @Test
    public void execute_list_notCaseSensitive() throws Exception {
     // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithCategory(1, new Category("UPpERloWer"), new Category("lower"));
        task1.addBuildInCategory(BuildInCategoryList.COMPLETE);
        Task task2 = helper.generateTaskWithCategory(2, new Category("UPPER"), new Category("lower"));
        Task task3 = helper.generateTaskWithCategory(3, new Category("UPpERloWer"));

        // prepare doerList state
        helper.addToModel(model, Arrays.asList(task1, task2, task3));
        //Execute the command
        // not case sensitive
        assertCategoryListed(Arrays.asList(task1, task3), new Category("UPpERloWer"), "upperlower");
        assertCategoryListed(Arrays.asList(task1, task2), new Category("lower"), "LoWer");
        assertCategoryListed(Arrays.asList(task2), new Category("UPPER"), "upper");
        assertCategoryListed(Arrays.asList(task1), BuildInCategoryList.COMPLETE, "CompLeTE");
    }

    //@@author A0147978E
    /**
     * Execute the command and validate the correct tasks {@code expected} under {@code category} are listed
     *
     * @param expected
     * @param category
     * @param commandArg
     */
    private void assertCategoryListed(List<? extends ReadOnlyTask> expected, Category category, String commandArg) {
        //Execute the command
        CommandResult result = logic.execute("list " + commandArg);
        //Confirm the UI display elements, should contain the right data
        assertEquals(String.format(ListCommand.MESSAGE_SUCCESS, category.categoryName), result.feedbackToUser);
        assertEquals(expected, logic.getFilteredTaskList());
    }

    //@@author
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 persons
        model.resetData(new DoerList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getDoerList(), taskList);
    }

    //@@author
    @Test
    public void execute_viewInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("view", expectedMessage);
    }

    //@@author
    @Test
    public void execute_viewIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("view");
    }

    //@@author
    @Test
    public void execute_view_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        DoerList expectedDL = helper.generateDoerList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("view 2",
                String.format(ViewCommand.MESSAGE_VIEW_TASK_SUCCESS, threeTasks.get(1)),
                expectedDL,
                expectedDL.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

    //@@author A0147978E
    @Test
    public void execute_edit_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "edit 1 /t valid title /d valid description /s invalid format /e 2011-10-12 13:00 /c valid_category", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
        assertCommandBehavior(
                "edit 1 /t valid title /d valid description /s 2011-10-12 12:00 /e invalid format /c valid_category", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);

        // dummy date for test
        TestDataHelper helper = new TestDataHelper();
        DoerList expectedDL = helper.generateDoerList(3);
        helper.addToModel(model, expectedDL.getTasks());
        assertCommandBehavior("edit 2 /t valid title /d valid description /s 2011-10-12 12:00 /e 2011-10-11 12:00 /c valid_category",
                TodoTime.TIME_INTERVAL_CONSTRAIN,
                expectedDL,
                expectedDL.getTaskList());
    }

    //@@author
    @Test
    public void execute_editInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("edit ", expectedMessage);
        assertIncorrectIndexFormatBehaviorForCommand("edit Drinks With David", expectedMessage);
        assertIncorrectIndexFormatBehaviorForCommand("edit /t     ", expectedMessage);
    }

    @Test
    public void execute_editTaskNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("edit 999");
    }

    @Test
    public void execute_edit_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        threeTasks.get(0).addBuildInCategory(BuildInCategoryList.COMPLETE); // mark the task as complete
        threeTasks.get(2).addBuildInCategory(BuildInCategoryList.COMPLETE); // mark the task as complete
        helper.addToModel(model, threeTasks);

        DoerList expectedAB = helper.generateDoerList(threeTasks);
        ReadOnlyTask taskToEdit = expectedAB.getTaskList().get(2);
        Task editedTask = helper.generateTask(4);
        editedTask.addBuildInCategory(BuildInCategoryList.COMPLETE);
        expectedAB.removeTask(taskToEdit);
        expectedAB.addTask(editedTask);

        assertCommandBehavior(helper.generateAddCommand(editedTask).replace("add", "edit 3"),
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, editedTask),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_editRecurring_fail() throws DuplicateTaskException, Exception {
        TestDataHelper helper = new TestDataHelper();
        Task toAdded = helper.taskWithAttribute(true, false, false, false, false);
        model.addTask(toAdded);
        DoerList expectedDL = new DoerList();
        expectedDL.addTask(toAdded);
        assertCommandBehavior("edit 1 /r daily",
                Recurring.MESSAGE_RECURRING_STARTEND_CONSTRAINTS,
                expectedDL,
                expectedDL.getTaskList());
    }
    
    @Test
    public void execute_editRecurring_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        helper.addToModel(model, threeTasks);
        
        DoerList expectedDL = helper.generateDoerList(threeTasks);
        ReadOnlyTask taskToEdit = expectedDL.getTaskList().get(2);
        Task editedTask = helper.generateRecurringTask(4);
        expectedDL.removeTask(taskToEdit);
        expectedDL.addTask(editedTask);
        
        assertCommandBehavior(helper.generateAddCommand(editedTask).replace("add", "edit 3"),
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, editedTask),
                expectedDL,
                expectedDL.getTaskList());
    }

    //@author A0147978E
    @Test
    public void execute_editTask_categoryWithZeroTask_removed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithCategory(1, new Category("A"));
        Task task2_before = helper.generateTaskWithCategory(2, new Category("A"), new Category("B"));
        Task task2_after = helper.generateTaskWithCategory(2, new Category("A"));

        // prepare test data
        DoerList expectedAB = helper.generateDoerList(Arrays.asList(task1, task2_after));
        helper.addToModel(model, Arrays.asList(task1, task2_before));

        assertCommandBehavior("edit 2 /c A",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, task2_before, task2_after),
                expectedAB,
                expectedAB.getTaskList());
    }

    //@@author
    @Test
    public void execute_editResultInDuplicate_notAllowed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskTitleAndDescription("Task 1", "D 1");
        Task task2 = helper.generateTaskTitleAndDescription("Task 1", "D 2");
        helper.addToModel(model, Arrays.asList(task1, task2));

        DoerList expectedAB = helper.generateDoerList(Arrays.asList(task1, task2));
        assertCommandBehavior("edit 1 /d D 2",
                String.format(EditCommand.MESSAGE_DUPLICATE_TASK),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    //@@author A0139168W
    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
        assertIncorrectIndexFormatBehaviorForCommand("delete a", expectedMessage);
    }
    
    //@@author A0139168W
    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    //@@author A0139168W
    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        DoerList expectedAB = helper.generateDoerList(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

    //@author A0147978E
    @Test
    public void execute_delete_removesTask_categoryWithZeroTask_removed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithCategory(1, new Category("A"));
        Task task2 = helper.generateTaskWithCategory(2, new Category("A"), new Category("B"));

        // prepare test data
        DoerList expectedAB = helper.generateDoerList(Arrays.asList(task1, task2));
        expectedAB.removeTask(task2);
        helper.addToModel(model, Arrays.asList(task1, task2));
        //@@author

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, task2),
                expectedAB,
                expectedAB.getTaskList());
    }

    //@author
    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWords() throws Exception {
        // keywords in title or description
        TestDataHelper helper = new TestDataHelper();
        Task cTarget1 = helper.generateTaskTitleAndDescription("bla bla KEY bla", "dummy");
        Task cTarget2 = helper.generateTaskTitleAndDescription("dummy", "bla KEY bla bceofeia");
        Task cTarget3 = helper.generateTaskTitleAndDescription("KEY bla", "bla KEY bla bceofeia");
        Task c1 = helper.generateTaskTitleAndDescription("KE Y", "dummy");
        Task c2 = helper.generateTaskTitleAndDescription("KEYKEYKEY sduauo", "dummy");

        List<Task> fiveTasks = helper.generateTaskList(c1, cTarget1, c2, cTarget2, cTarget3);
        DoerList expectedAB = helper.generateDoerList(fiveTasks);
        List<Task> expectedList = helper.generateTaskList(cTarget1, cTarget2, cTarget3);
        helper.addToModel(model, fiveTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskTitleAndDescription("bla bla KEY bla", "dummy");
        Task p2 = helper.generateTaskTitleAndDescription("bla KEY bla bceofeia", "dummy");
        Task p3 = helper.generateTaskTitleAndDescription("key key", "dummy");
        Task p4 = helper.generateTaskTitleAndDescription("KEy sduauo", "dummy");

        List<Task> fourPersons = helper.generateTaskList(p3, p1, p4, p2);
        DoerList expectedAB = helper.generateDoerList(fourPersons);
        List<Task> expectedList = fourPersons;
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task cTarget1 = helper.generateTaskTitleAndDescription("bla bla KEY bla", "dummy");
        Task cTarget2 = helper.generateTaskTitleAndDescription("bla rAnDoM bla bceofeia", "dummy");
        Task cTarget3 = helper.generateTaskTitleAndDescription("key key", "dummy");
        Task c1 = helper.generateTaskTitleAndDescription("sduauo", "dummy");

        List<Task> fourPersons = helper.generateTaskList(cTarget1, c1, cTarget2, cTarget3);
        DoerList expectedAB = helper.generateDoerList(fourPersons);
        List<Task> expectedList = helper.generateTaskList(cTarget1, cTarget2, cTarget3);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    
    //@@author A0139168W
    @Test
    public void execute_unmark_unmarkInvalidIndex() throws Exception {
        assertIncorrectIndexFormatBehaviorForCommand("unmark ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }

    
    //@@author A0139168W
    @Test
    public void execute_unmarkRecurringTask_getUpdated() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task recurringTask = helper.generateTaskWithTime(5, "tomorrow 5pm", "tomorrow 6pm", "daily");
        Task updatedRecurringTask = helper.generateTaskWithTime(5, "today 5pm", "today 6pm", "daily");     
        helper.addToModel(model, Arrays.asList(recurringTask));
        List<Task> expectedList = helper.generateTaskList(updatedRecurringTask);
        DoerList expectedDL = helper.generateDoerList(expectedList);
        
        assertCommandBehavior("unmark 1", 
                String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, recurringTask), 
                expectedDL, 
                expectedList);
    }
    
    @Test
    public void execute_unmark_unmarkTaskAsUndone_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper(); 
        Task incomplete = helper.generateTask(5); // not complete
        Task complete = helper.generateTask(5);
        complete.addBuildInCategory(BuildInCategoryList.COMPLETE);
        List<Task> expectedList = helper.generateTaskList(incomplete);
        DoerList expectedDL = helper.generateDoerList(expectedList);
        
        helper.addToModel(model, Arrays.asList(incomplete));

        assertCommandBehavior("unmark 1", 
                String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, incomplete), 
                expectedDL, 
                expectedList);

        // marking twice should be ok
        assertCommandBehavior("unmark 1", 
                String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, incomplete), 
                expectedDL, 
                expectedList);       
    }

    
    //@@author A0139168W
    @Test
    public void exectue_mark_invalidIndex() throws Exception {
        assertIncorrectIndexFormatBehaviorForCommand("mark ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    
    //@@author A0139168W
    @Test
    public void execute_markRecurringTask_getUpdated() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task recurringTask = helper.generateTaskWithTime(5, "today 5pm", "today 6pm", "daily");
        Task updatedRecurringTask = helper.generateTaskWithTime(5, "tomorrow 5pm", "tomorrow 6pm", "daily");
        helper.addToModel(model, Arrays.asList(recurringTask));
        List<Task> expectedList = helper.generateTaskList(updatedRecurringTask);
        DoerList expectedDL = helper.generateDoerList(expectedList);
        
        assertCommandBehavior("mark 1", 
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, recurringTask), 
                expectedDL, 
                expectedList);
    }
    
    @Test
    public void execute_mark_markTaskAsDone_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task incomplete = helper.generateTask(5); // not complete
        Task complete = helper.generateTask(5);
        complete.addBuildInCategory(BuildInCategoryList.COMPLETE);
        List<Task> expectedList = helper.generateTaskList(complete);
        DoerList expectedDL = helper.generateDoerList(expectedList);
        
        helper.addToModel(model, Arrays.asList(incomplete));

        assertCommandBehavior("mark 1", 
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, complete), 
                expectedDL, 
                expectedList);

        // marking twice should be ok
        assertCommandBehavior("mark 1", 
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, complete), 
                expectedDL, 
                expectedList);
    }
    //@@author

    @Test
    public void execute_taskdue_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> fourTasks = helper.generateTaskList(4);
        helper.addToModel(model, fourTasks);
        DoerList expectedAB = helper.generateDoerList(fourTasks);

        List<Task> expectedList = fourTasks.subList(0, 3);

        assertCommandBehavior("taskdue 2016-10-06 23:59",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    //@@author A0140905M
    @Test
    public void execute_taskdue_invalidArgsFormat() throws Exception {
        assertCommandBehavior(
                "taskdue", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
        assertCommandBehavior(
                "taskdue ok ", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
        assertCommandBehavior(
                "taskdue hmmm    ", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
    }
    //@@author

    //@@author A0140905M
    @Test
    public void execute_undo_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "undo 13123 ", expectedMessage);
        assertCommandBehavior(
                "undo 1231023213    ", expectedMessage);
    }
    //@@author

    @Test
    public void execute_undo_redo_add_operation_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTask(1);
        Task task2 = helper.generateTask(2);
        DoerList expectedDL = new DoerList(); // going to undo the add command

        // execute command
        logic.execute(helper.generateAddCommand(task1));
        logic.execute("undo");
        logic.execute(helper.generateAddCommand(task2));
        // cannot redo after operation
        assertEquals(RedoCommand.MESSAGE_REDO_FAILURE, logic.execute("redo").feedbackToUser);
        // validate the results
        assertCommandBehavior("undo",
                UndoCommand.MESSAGE_UNDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());

        // execute redo should give back 1 tasks
        helper.addToDoerList(expectedDL, Arrays.asList(task2));
        assertCommandBehavior("redo",
                RedoCommand.MESSAGE_REDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());
    }

    @Test
    public void execute_undo_redo_delete_operation_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTask(1);
        Task task2 = helper.generateTask(2);
        model.addTask(task1);
        model.addTask(task2);

        // execute command
        logic.execute("delete 1");
        logic.execute("undo");
        logic.execute("delete 1");
        // cannot redo after operation
        assertEquals(RedoCommand.MESSAGE_REDO_FAILURE, logic.execute("redo").feedbackToUser);
        
        // execute undo command  and verify
        DoerList expectedDL = new DoerList();
        helper.addToDoerList(expectedDL, Arrays.asList(task1, task2)); // the order does matter
        assertCommandBehavior("undo",
                UndoCommand.MESSAGE_UNDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());

        // execute redo should give back task
        expectedDL = new DoerList();
        helper.addToDoerList(expectedDL, Arrays.asList(task1));
        assertCommandBehavior("redo",
                RedoCommand.MESSAGE_REDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());
    }

    @Test
    public void execute_undo_redo_edit_operation_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithCategory(1, new Category("CA1"), new Category("CA2"));
        Task task2_before = helper.generateTaskWithCategory(2, new Category("CA1"));
        Task task2_after = helper.generateTaskWithCategory(2, new Category("CA2"));
        DoerList expectedDL = new DoerList(); // going to undo the add command
        model.addTask(task1);
        model.addTask(task2_before);
        helper.addToDoerList(expectedDL, Arrays.asList(task1, task2_before));

        // execute command
        logic.execute("edit 2 /c CA3");
        logic.execute("undo");
        logic.execute("edit 2 /c CA2");
        // cannot redo after operation
        assertEquals(RedoCommand.MESSAGE_REDO_FAILURE, logic.execute("redo").feedbackToUser);
        
        // execute undo command 1 time and verify
        assertCommandBehavior("undo",
                UndoCommand.MESSAGE_UNDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());

        // execute redo should give back the original tasks
        expectedDL.replaceTask(task2_before, task2_after);
        assertCommandBehavior("redo",
                RedoCommand.MESSAGE_REDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());
    }
    
    //@@author A0147978E
    @Test
    public void execute_undo_redo_mark_unmark_operation_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTask(1);
        Task task2 = helper.generateTask(2);
        Task task2_mark = helper.generateTask(2); task2_mark.addBuildInCategory(BuildInCategoryList.COMPLETE);
        DoerList expectedDL = new DoerList(); // going to undo the add command
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        helper.addToDoerList(expectedDL, Arrays.asList(task1, task2));

        // execute command
        logic.execute("mark 1");
        logic.execute("undo");
        logic.execute("mark 2");
        // cannot redo after operation
        assertEquals(RedoCommand.MESSAGE_REDO_FAILURE, logic.execute("redo").feedbackToUser);

        // execute undo command 1 time and verify
        assertCommandBehavior("undo",
                UndoCommand.MESSAGE_UNDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());
        logic.execute("redo");
        
        logic.execute("delete 2");
        logic.execute("undo");
        logic.execute("unmark 2");
        // cannot redo after operation
        assertEquals(RedoCommand.MESSAGE_REDO_FAILURE, logic.execute("redo").feedbackToUser);
        // execute undo should give back the original tasks
        DoerList expectedDL_mark = new DoerList();
        helper.addToDoerList(expectedDL_mark, Arrays.asList(task1, task2_mark));
        assertCommandBehavior("undo",
                UndoCommand.MESSAGE_UNDO_SUCCESS,
                expectedDL_mark,
                expectedDL_mark.getTaskList());
        
        assertCommandBehavior("redo",
                RedoCommand.MESSAGE_REDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());
    }
       
    //@@author
    @Test
    public void execute_undo_redo_clear_operation_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithCategory(1, new Category("CA1"), new Category("CA2"));
        Task task2 = helper.generateTaskWithCategory(2, new Category("CA1"));
        DoerList expectedDL = new DoerList(); // going to undo the add command
        model.addTask(task1);
        model.addTask(task2);
        helper.addToDoerList(expectedDL, Arrays.asList(task2, task1));

        // execute command
        logic.execute("delete 1");
        logic.execute("undo");
        logic.execute("clear");
        // cannot redo after operation
        assertEquals(RedoCommand.MESSAGE_REDO_FAILURE, logic.execute("redo").feedbackToUser);
        
        // execute undo command 1 time and verify
        assertCommandBehavior("undo",
                UndoCommand.MESSAGE_UNDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());

        // execute redo should give back the original tasks
        expectedDL = new DoerList();
        assertCommandBehavior("redo",
                RedoCommand.MESSAGE_REDO_SUCCESS,
                expectedDL,
                expectedDL.getTaskList());
    }


    @Test
    public void execute_EmptyList_undo_redo_unsuccessful_no_undoable_command() throws Exception {
        DoerList expectedAB = new DoerList();
        logic.execute("wrong command");

        assertCommandBehavior("undo",
                UndoCommand.MESSAGE_UNDO_FAILURE,
                expectedAB,
                expectedAB.getTaskList());

        assertCommandBehavior("redo",
                RedoCommand.MESSAGE_REDO_FAILURE,
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_undo_redo_invalidArgsFormat() throws Exception {
        assertCommandBehavior(
                "redo 13123 ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        assertCommandBehavior(
                "redo 1231023213    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        assertCommandBehavior(
                "undo 334    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        assertCommandBehavior(
                "undo 1234    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }
    
    
    //@@author A0139168W
    @Test
    public void execute_saveLocation_successful_moreFilePath() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        DoerList expectedDL = helper.generateDoerList(10);
        model.resetData(expectedDL);
        String tempConfig = saveFolder.getRoot().getPath() + "TempConfig.json";
        //create temp config file
        ConfigUtil.saveConfig(new Config(), tempConfig);
        
        String validSavePath = "data/test2.xml";
        
        //Execute the command
        CommandResult result = logic.execute("saveto " + validSavePath);

        //Confirm the ui display elements should contain the right data
        assertEquals(String.format(SaveCommand.MESSAGE_SUCCESS, validSavePath), 
                result.feedbackToUser);
        
        // validate doer list content
        ReadOnlyDoerList retrieve = XmlFileStorage.loadDataFromSaveFile(new File(validSavePath));
        assertEquals(retrieve.getUniqueTaskList(), expectedDL.getUniqueTaskList());
        assertEquals(retrieve.getUniqueCategoryList(), expectedDL.getUniqueCategoryList());
        
        // validate storage path
        Optional<Config> configOptional = ConfigUtil.readConfig(tempConfig);
        assertEquals(configOptional.get().getDoerListFilePath(), validSavePath);
    }
    
    
    //@@author A0139168W
    @Test
    public void execute_saveLocation_invalidArgsFormat() throws Exception {
        assertCommandBehavior("saveto",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_INVALID_SAVE_LOCATION));
        assertCommandBehavior("saveto     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_INVALID_SAVE_LOCATION));
        assertCommandBehavior("saveto /n   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_INVALID_SAVE_LOCATION));
    }




    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        //@@author A0147978E
        /**
         * Generate a task with/without attribute
         *
         * @param hasDescription indicate whether to has the attribute
         * @param hasTimeInterval indicate whether to has the attribute
         * @param hasRecurring indicate whether to has the attribute
         * @param hasCategory indicate whether to has the attribute
         * @return Task generated task
         * @throws Exception
         */
        public Task taskWithAttribute(boolean hasDescription, boolean hasStartTime, boolean hasEndTime, boolean hasRecurring, boolean hasCategory) throws Exception {
            Title title = new Title("My Task");
            Description description = null;
            TodoTime startTime = null;
            TodoTime endTime = null;
            Recurring recurring = null;
            UniqueCategoryList categories = new UniqueCategoryList();
            if (hasDescription) {
                description = new Description("Do my homework");
            }
            if (hasStartTime) {
                startTime = new TodoTime("2016-10-03 14:00");
            }
            if (hasEndTime) {
                endTime = new TodoTime("2016-10-04 15:00");
            }
            if (hasRecurring){
                recurring = new Recurring("daily");
            }
            if (hasCategory) {
                Category category1 = new Category("CS2101");
                Category category2 = new Category("CS2103T");
                categories = new UniqueCategoryList(category1, category2);
            }

            return new Task(title, description, startTime, endTime, recurring, categories);
        }
        


        //@@author A0139401N
        /**
         * Generates a valid recurring task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        public Task generateRecurringTask(int seed) throws Exception {
            LocalDateTime sampleDate = LocalDateTime.parse("2016-10-03 10:15", 
                    DateTimeFormatter.ofPattern(TodoTime.TIME_STANDARD_FORMAT));
            return new Task(
                    new Title("Task " + seed),
                    new Description("" + Math.abs(seed)),
                    new TodoTime(sampleDate),
                    new TodoTime(sampleDate.plusDays(seed)),
                    new Recurring("daily"),
                    new UniqueCategoryList(new Category("CS" + Math.abs(seed)), new Category("CS" + Math.abs(seed + 1)))
            );
        }
        
        //@@author A0147978E
        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        public Task generateTask(int seed) throws Exception {
            LocalDateTime sampleDate = LocalDateTime.parse("2016-10-03 10:15",
                    DateTimeFormatter.ofPattern(TodoTime.TIME_STANDARD_FORMAT));
            return new Task(
                    new Title("Task " + seed),
                    new Description("" + Math.abs(seed)),
                    new TodoTime(sampleDate),
                    new TodoTime(sampleDate.plusDays(seed)),
                    null,
                    new UniqueCategoryList(new Category("CS" + Math.abs(seed)), new Category("CS" + Math.abs(seed + 1)))
            );
        }
                
        //@@author A0147978E
        /**
         * Generate Task with given seed, startTime, endTime and recurring
         * 
         * @param seed
         * @param startTime
         * @param endTime
         * @return
         */
        public Task generateTaskWithTime(int seed, String startTime, String endTime, String recurring) {
            try {
                return new Task(
                        new Title("Task " + seed),
                        new Description("" + Math.abs(seed)),
                        startTime != null ? new TodoTime(startTime) : null,
                        endTime != null ? new TodoTime(endTime) : null,
                        recurring != null ? new Recurring(recurring) : null,
                        new UniqueCategoryList(new Category("CS" + Math.abs(seed)), new Category("CS" + Math.abs(seed + 1)))
                );
            } catch (Exception e) {
                // impossible
            }
            return null;
        }

        //@@author A0147978E
        /**
         * Generate task based on given seed and category
         *
         * @param seed
         * @param Category... c
         * @return task with given seed and category
         */
        public Task generateTaskWithCategory(int seed, Category... c) {
            try {
                LocalDateTime sampleDate = LocalDateTime.parse("2016-10-03 10:15",
                        DateTimeFormatter.ofPattern(TodoTime.TIME_STANDARD_FORMAT));
                return new Task(
                        new Title("Task " + seed),
                        new Description("" + Math.abs(seed)),
                        new TodoTime(sampleDate),
                        new TodoTime(sampleDate.plusDays(seed)),
                        null, 
                        new UniqueCategoryList(Arrays.asList(c))
                );
            } catch (Exception e) {
                // impossible
            }
            return null;
        }

        //@@author A0147978E
        /**
         * Generate task with title and description but without recurring tasks
         * 
         * @param title
         * @param description
         * @return generated Task
         * @throws Exception
         */
        public Task generateTaskTitleAndDescription(String title, String description) throws Exception {
            Category category1 = new Category("CS2101");
            Category category2 = new Category("CS2103T");
            UniqueCategoryList categories = new UniqueCategoryList(category1, category2);
            return new Task(new Title(title),
                    new Description(description),
                    new TodoTime("2016-10-03 14:00"),
                    new TodoTime("2016-10-04 15:00"),
                    null,
                    categories);
        }
        

        //@@author A0147978E
        /** Generates the correct add command based on the task given */
        public String generateAddCommand(Task r) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");
            cmd.append("/t ").append(r.getTitle()).append(" ");

            if (r.hasDescription()) {
                cmd.append("/d ").append(r.getDescription()).append(" ");
            }

            if (!r.isFloatingTask()) {
                if (r.hasStartTime()) {
                    cmd.append("/s ");
                    cmd.append(r.getStartTime() + " ");
                }
                if (r.hasEndTime()) {
                    cmd.append("/e ");
                    cmd.append(r.getEndTime() + " ");
                }
            }
            
            if (r.hasRecurring()){
                cmd.append("/r ").append(r.getRecurring()).append(" ");
            }          
            
            UniqueCategoryList categories = r.getCategories();
            if (!categories.getInternalList().isEmpty()) {
                for(Category c: categories){
                    cmd.append("/c " + c.categoryName + " ");
                }
            }

            return cmd.toString();
        }

        //@@author
        /**
         * Generates an DoerList with auto-generated tasks.
         */
        public DoerList generateDoerList(int numGenerated) throws Exception{
            DoerList doerList = new DoerList();
            addToDoerList(doerList, numGenerated);
            return doerList;
        }

        /**
         * Generates an DoerList based on the list of Tasks given.
         */
        public DoerList generateDoerList(List<Task> tasks) throws Exception{
            DoerList doerList = new DoerList();
            addToDoerList(doerList, tasks);
            return doerList;
        }

        /**
         * Adds auto-generated Person objects to the given DoerList
         * @param doerList The DoerList to which the Persons will be added
         */
        public void addToDoerList(DoerList doerList, int numGenerated) throws Exception{
            addToDoerList(doerList, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given DoerList
         */
        public void addToDoerList(DoerList doerList, List<Task> tasksToAdd) throws Exception{
            for(Task c: tasksToAdd){
                doerList.addTask(c);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Task will be added
         */
        public void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        public void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task t: tasksToAdd){
                model.addTask(t);
            }
        }

        /**
         * Generates a list of Tasks.
         */
        public List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }
        
        public List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

    }
}
