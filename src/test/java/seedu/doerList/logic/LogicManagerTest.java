package seedu.doerList.logic;

import com.google.common.eventbus.Subscribe;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.events.model.DoerListChangedEvent;
import seedu.doerList.commons.events.ui.JumpToListRequestEvent;
import seedu.doerList.commons.events.ui.ShowHelpRequestEvent;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.Logic;
import seedu.doerList.logic.LogicManager;
import seedu.doerList.logic.commands.*;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.Model;
import seedu.doerList.model.ModelManager;
import seedu.doerList.model.ReadOnlyDoerList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.doerList.model.task.*;
import seedu.doerList.storage.StorageManager;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.doerList.commons.core.Messages.*;

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
        logic = new LogicManager(model, new StorageManager(tempDoerListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedDoerList = new DoerList(model.getDoerList()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

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


    @Test
    public void execute_unknownCommandWord() throws Exception {
        //String unknownCommand = "uicfhmowqewca";
        //assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help_noArgs() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
    public void execute_help_invalidArgs() throws Exception {
        assertCommandBehavior("help sdfdsf", HelpCommand.INVALID_HELP_MESSAGE);
        assertCommandBehavior("help 123", HelpCommand.INVALID_HELP_MESSAGE);
        assertCommandBehavior("help hmmm hahaha", HelpCommand.INVALID_HELP_MESSAGE);
    }

    @Test
    public void execute_help_correctArgs() throws Exception {
        assertCommandBehavior("help add", AddCommand.MESSAGE_USAGE);
        assertCommandBehavior("help edit", EditCommand.MESSAGE_USAGE);
        //assertCommandBehavior("help mark", MarkCommand.MESSAGE_USAGE);
        //assertCommandBehavior("help unmark", UnMarkCommand.MESSAGE_USAGE);
        assertCommandBehavior("help list", ListCommand.MESSAGE_USAGE);
        assertCommandBehavior("help find", FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("help view", ViewCommand.MESSAGE_USAGE);
        assertCommandBehavior("help delete", DeleteCommand.MESSAGE_USAGE);
        //assertCommandBehavior("help undo", UndoCommand.MESSAGE_USAGE);
        //assertCommandBehavior("help redo", RedoCommand.MESSAGE_USAGE);
        //assertCommandBehavior("help taskdue", TaskdueCommand.MESSAGE_USAGE);
    }

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

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add /t valid title /d valid description /s invalid format /e 2011-10-12 13:00 /c valid_category", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
        assertCommandBehavior(
                "add /t valid title /d valid description /s 2011-10-12 12:00 /e invalid format /c valid_category", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task[] inputs = {
                helper.taskWithAttribute(true, false, false, false),
                helper.taskWithAttribute(false, true, false, false),
                helper.taskWithAttribute(false, false, true, false),
                helper.taskWithAttribute(false, false, false, true),
                helper.taskWithAttribute(false, true, true, false),
                helper.taskWithAttribute(false, false, false, false)
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


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        DoerList expectedAB = helper.generateDoerList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare doerList state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                String.format(ListCommand.MESSAGE_SUCCESS, "All"),
                expectedAB,
                expectedList);
    }
    
    @Test
    public void execute_list_buildInCategory() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task Today1 = helper.generateTaskWithTime(1, new DateTime().withHourOfDay(8).toString(), 
                        new DateTime().withHourOfDay(12).toString()); // today
        Task Next7Days1 = helper.generateTaskWithTime(2, new DateTime().withHourOfDay(8).plusDays(1).toString(), 
                        new DateTime().withHourOfDay(12).plusDays(1).toString()); // tomorrow
        Task Next7Days2 = helper.generateTaskWithTime(3, new DateTime().withHourOfDay(8).plusDays(5).toString(), 
                        new DateTime().withHourOfDay(12).plusDays(5).toString()); // next 5 days
        Task Next7Days3 = helper.generateTaskWithTime(3, new DateTime().withHourOfDay(8).plusDays(7).toString(), 
                        new DateTime().withHourOfDay(12).plusDays(7).toString()); // next 7 days
        Task Inbox1 = helper.generateTaskWithTime(4, null, null); // inbox
        Task Complete1 = helper.generateTaskWithCategory(5); // complete
        Complete1.addBuildInCategory(BuildInCategoryList.COMPLETE);

        // prepare doerList state
        helper.addToModel(model, Arrays.asList(Today1, Next7Days1, Next7Days2, Next7Days3, Inbox1, Complete1));

        // Test ALL
        assertBuildInCategoryListed(Arrays.asList(Today1, Next7Days1, Next7Days2, Next7Days3, Inbox1, Complete1),
                        BuildInCategoryList.ALL
                        );
        // Test Next 7 Days
        assertBuildInCategoryListed(Arrays.asList(Next7Days1, Next7Days2, Next7Days3),
                        BuildInCategoryList.NEXT
                        );
        // Test Inbox
        assertBuildInCategoryListed(Arrays.asList(Inbox1),
                        BuildInCategoryList.INBOX
                        );
        // Test complete
        assertBuildInCategoryListed(Arrays.asList(Complete1),
                        BuildInCategoryList.COMPLETE
                        );
    }
    
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
        assertEquals(ListCommand.MESSAGE_CATEGORY_NOT_EXISTS, result.feedbackToUser);
        
        // list All
        //Execute the command
        result = logic.execute("list");
        assertEquals(Arrays.asList(task1, task2, task3, task4), logic.getFilteredTaskList());
        
        // List CA1
        assertCategoryListed(Arrays.asList(task1, task2),
                        "CA1"
                        );
        // list CA2
        assertCategoryListed(Arrays.asList(task1, task3),
                        "CA2"
                        );
    }
    
    private void assertBuildInCategoryListed(List<? extends ReadOnlyTask> expected, Category category) throws Exception {
        //Execute the command
        CommandResult result = logic.execute("list " + category.categoryName);
        //Confirm the ui display elements should contain the right data
        assertEquals(String.format(ListCommand.MESSAGE_SUCCESS, category.categoryName), result.feedbackToUser);
        assertEquals(expected, category.getTasks());
    }
    
    private void assertCategoryListed(List<? extends ReadOnlyTask> expected, String categoryName) {
        //Execute the command
        CommandResult result = logic.execute("list " + categoryName);
        //Confirm the ui display elements should contain the right data
        assertEquals(String.format(ListCommand.MESSAGE_SUCCESS, categoryName), result.feedbackToUser);
        assertEquals(expected, logic.getFilteredTaskList());
    }


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

    @Test
    public void execute_viewInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("view", expectedMessage);
    }

    @Test
    public void execute_viewIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("view");
    }

    @Test
    public void execute_select_jumpsToCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generateTaskList(3);

        DoerList expectedAB = helper.generateDoerList(threePersons);
        helper.addToModel(model, threePersons);

        assertCommandBehavior("view 2",
                String.format(ViewCommand.MESSAGE_VIEW_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threePersons.get(1));
    }

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
        helper.addToModel(model, threeTasks);

        DoerList expectedAB = helper.generateDoerList(threeTasks);
        ReadOnlyTask taskToEdit = expectedAB.getTaskList().get(2);
        Task editedTask = helper.generateTask(4);
        expectedAB.removeTask(taskToEdit);
        expectedAB.addTask(editedTask);

        assertCommandBehavior(helper.generateAddCommand(editedTask).replace("add", "edit 3"),
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, editedTask),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_editResultInDuplicate_notAllowed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithTitleAndDescription("Task 1", "D 1");
        Task task2 = helper.generateTaskWithTitleAndDescription("Task 1", "D 2");
        helper.addToModel(model, Arrays.asList(task1, task2));

        DoerList expectedAB = helper.generateDoerList(Arrays.asList(task1, task2));
        assertCommandBehavior("edit 1 /d D 2",
                String.format(EditCommand.MESSAGE_DUPLICATE_TASK),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
        assertIncorrectIndexFormatBehaviorForCommand("delete a", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

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


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWords() throws Exception {
        // keywords in title or description
        TestDataHelper helper = new TestDataHelper();
        Task cTarget1 = helper.generateTaskWithTitleAndDescription("bla bla KEY bla", "dummy");
        Task cTarget2 = helper.generateTaskWithTitleAndDescription("dummy", "bla KEY bla bceofeia");
        Task cTarget3 = helper.generateTaskWithTitleAndDescription("KEY bla", "bla KEY bla bceofeia");
        Task c1 = helper.generateTaskWithTitleAndDescription("KE Y", "dummy");
        Task c2 = helper.generateTaskWithTitleAndDescription("KEYKEYKEY sduauo", "dummy");

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
        Task p1 = helper.generateTaskWithTitleAndDescription("bla bla KEY bla", "dummy");
        Task p2 = helper.generateTaskWithTitleAndDescription("bla KEY bla bceofeia", "dummy");
        Task p3 = helper.generateTaskWithTitleAndDescription("key key", "dummy");
        Task p4 = helper.generateTaskWithTitleAndDescription("KEy sduauo", "dummy");

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
        Task cTarget1 = helper.generateTaskWithTitleAndDescription("bla bla KEY bla", "dummy");
        Task cTarget2 = helper.generateTaskWithTitleAndDescription("bla rAnDoM bla bceofeia", "dummy");
        Task cTarget3 = helper.generateTaskWithTitleAndDescription("key key", "dummy");
        Task c1 = helper.generateTaskWithTitleAndDescription("sduauo", "dummy");

        List<Task> fourPersons = helper.generateTaskList(cTarget1, c1, cTarget2, cTarget3);
        DoerList expectedAB = helper.generateDoerList(fourPersons);
        List<Task> expectedList = helper.generateTaskList(cTarget1, cTarget2, cTarget3);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    
    @Test
    public void execute_unmark_unmarkInvalidIndex() throws Exception {
        assertIncorrectIndexFormatBehaviorForCommand("unmark ", 
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void execute_unmark_unmarkTaskAsUndone_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task notComplete = helper.generateTask(5); // not complete
        Task complete = helper.generateTask(5);
        complete.addBuildInCategory(BuildInCategoryList.COMPLETE);
        List<Task> expectedList = helper.generateTaskList(notComplete);
        DoerList expectedAB = helper.generateDoerList(expectedList);
        
        helper.addToModel(model, Arrays.asList(complete));

        assertCommandBehavior("unmark 1", 
                String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, notComplete), 
                expectedAB, 
                expectedList);
        
        // marking twice should be ok
        assertCommandBehavior("unmark 1", 
                String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, notComplete), 
                expectedAB, 
                expectedList);       
    }
    
    @Test
    public void exectue_mark_invalidIndex() throws Exception {
        assertIncorrectIndexFormatBehaviorForCommand("mark ", 
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void execute_mark_markTaskAsDone_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task notComplete = helper.generateTask(5); // not complete
        Task complete = helper.generateTask(5);
        complete.addBuildInCategory(BuildInCategoryList.COMPLETE);
        List<Task> expectedList = helper.generateTaskList(complete);
        DoerList expectedAB = helper.generateDoerList(expectedList);
        
        helper.addToModel(model, Arrays.asList(notComplete));

        assertCommandBehavior("mark 1", 
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, complete), 
                expectedAB, 
                expectedList);
        
        // marking twice should be ok
        assertCommandBehavior("mark 1", 
                String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, complete), 
                expectedAB, 
                expectedList);       
    }

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

    @Test
    public void execute_taskdue_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskdueCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "taskdue", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
        assertCommandBehavior(
                "taskdue ok ", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
        assertCommandBehavior(
                "taskdue hmmm    ", TodoTime.MESSAGE_TODOTIME_CONSTRAINTS);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        /**
         * Generate a task with/without attribute
         *
         * @param hasDescription indicate whether to has the attribute
         * @param hasTimeInterval indicate whether to has the attribute
         * @param hasCategory indicate whether to has the attribute
         * @return Task generated task
         * @throws Exception
         */
        Task taskWithAttribute(boolean hasDescription, boolean hasStartTime, boolean hasEndTime, boolean hasCategory) throws Exception {
            Title title = new Title("My Task");
            Description description = null;
            TodoTime startTime = null;
            TodoTime endTime = null;
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
            if (hasCategory) {
                Category category1 = new Category("CS2101");
                Category category2 = new Category("CS2103T");
                categories = new UniqueCategoryList(category1, category2);
            }

            return new Task(title, description, startTime, endTime, categories);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            DateTime sampleDate = DateTime.parse("2016-10-03 10:15", DateTimeFormat.forPattern(TodoTime.TIME_STANDARD_FORMAT));
            return new Task(
                    new Title("Task " + seed),
                    new Description("" + Math.abs(seed)),
                    new TodoTime(sampleDate),
                    new TodoTime(sampleDate.plusDays(seed)),
                    new UniqueCategoryList(new Category("CS" + Math.abs(seed)), new Category("CS" + Math.abs(seed + 1)))
            );
        }
        
        /**
         * Generate Task with given seed, startTime and endTime
         * 
         * @param seed
         * @param startTime
         * @param endTime
         * @return
         */
        Task generateTaskWithTime(int seed, String startTime, String endTime) {
            try {
                return new Task(
                        new Title("Task " + seed),
                        new Description("" + Math.abs(seed)),
                        startTime != null ? new TodoTime(startTime) : null,
                        endTime != null ? new TodoTime(endTime) : null,
                        new UniqueCategoryList(new Category("CS" + Math.abs(seed)), new Category("CS" + Math.abs(seed + 1)))
                );
            } catch (Exception e) {
                // impossible
            }
            return null;
        }
        
        /**
         * Generate task based on given seed and category
         * 
         * @param seed
         * @param Category... c
         * @return task with given seed and category
         */
        Task generateTaskWithCategory(int seed, Category... c) {
            try {
                DateTime sampleDate = DateTime.parse("2016-10-03 10:15", DateTimeFormat.forPattern(TodoTime.TIME_STANDARD_FORMAT));
                return new Task(
                        new Title("Task " + seed),
                        new Description("" + Math.abs(seed)),
                        new TodoTime(sampleDate),
                        new TodoTime(sampleDate.plusDays(seed)),
                        new UniqueCategoryList(Arrays.asList(c))
                );
            } catch (Exception e) {
                // impossible
            }
            return null;
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task r) {
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

            UniqueCategoryList categories = r.getCategories();
            if (!categories.getInternalList().isEmpty()) {
                for(Category c: categories){
                    cmd.append("/c " + c.categoryName + " ");
                }
            }

            return cmd.toString();
        }

        /**
         * Generates an DoerList with auto-generated tasks.
         */
        DoerList generateDoerList(int numGenerated) throws Exception{
            DoerList doerList = new DoerList();
            addToDoerList(doerList, numGenerated);
            return doerList;
        }

        /**
         * Generates an DoerList based on the list of Tasks given.
         */
        DoerList generateDoerList(List<Task> tasks) throws Exception{
            DoerList doerList = new DoerList();
            addToDoerList(doerList, tasks);
            return doerList;
        }

        /**
         * Adds auto-generated Person objects to the given DoerList
         * @param doerList The DoerList to which the Persons will be added
         */
        void addToDoerList(DoerList doerList, int numGenerated) throws Exception{
            addToDoerList(doerList, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given DoerList
         */
        void addToDoerList(DoerList doerList, List<Task> tasksToAdd) throws Exception{
            for(Task c: tasksToAdd){
                doerList.addTask(c);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Task will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task t: tasksToAdd){
                model.addTask(t);
            }
        }

        Task generateTaskWithTitleAndDescription(String title, String description) throws Exception {
            Category category1 = new Category("CS2101");
            Category category2 = new Category("CS2103T");
            UniqueCategoryList categories = new UniqueCategoryList(category1, category2);
            return new Task(new Title(title),
                    new Description(description),
                    new TodoTime("2016-10-03 14:00"),
                    new TodoTime("2016-10-04 15:00"),
                    categories);
        }

        /**
         * Generates a list of Tasks.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }



    }
}
