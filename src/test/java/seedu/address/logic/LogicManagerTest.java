package seedu.address.logic;

import com.google.common.eventbus.Subscribe;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.EventsCenter;
import seedu.address.logic.commands.*;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.category.Category;
import seedu.address.model.category.UniqueCategoryList;
import seedu.address.model.task.*;
import seedu.address.storage.StorageManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyAddressBook latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(AddressBookChangedEvent abce) {
        latestSavedAddressBook = new AddressBook(abce.data);
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
        String tempAddressBookFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempAddressBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedAddressBook = new AddressBook(model.getAddressBook()); // last saved assumed to be up to date before.
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
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyAddressBook, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new AddressBook(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyAddressBook expectedAddressBook,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedAddressBook, latestSavedAddressBook);
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        //String unknownCommand = "uicfhmowqewca";
        //assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws Exception {
        //assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        //assertTrue(helpShown);
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

        //assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new AddressBook(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add", expectedMessage);
        assertCommandBehavior(
                "add -t", expectedMessage);
        assertCommandBehavior(
                "add -t     ", expectedMessage);

    }

    @Test
    public void execute_add_invalidPersonData() throws Exception {
//        assertCommandBehavior(
//                "add []\\[;] p/12345 e/valid@e.mail a/valid, address", Title.MESSAGE_TITLE_CONSTRAINTS);
//        assertCommandBehavior(
//                "add Valid Name p/not_numbers e/valid@e.mail a/valid, address", Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
//        assertCommandBehavior(
//                "add Valid Name p/12345 e/notAnEmail a/valid, address", TimeInterval.MESSAGE_TIME_INTERVAL_CONSTRAINTS);
//        assertCommandBehavior(
//                "add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag", Category.MESSAGE_CATEGORY_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
//        // setup expectations
//        TestDataHelper helper = new TestDataHelper();
//        Person toBeAdded = helper.adam();
//        AddressBook expectedAB = new AddressBook();
//        expectedAB.addPerson(toBeAdded);
//
//        // execute command and verify result
//        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
//                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
//                expectedAB,
//                expectedAB.getPersonList());

    }
    
    @Test
    public void execute_add_floatingTask_successful() throws Exception {
      // setup expectations
      TestDataHelper helper = new TestDataHelper();
      Task toBeAdded = helper.taskWithAttribute(false, false, false);
      AddressBook expectedAB = new AddressBook();
      expectedAB.addTask(toBeAdded);

      // execute command and verify result
      assertCommandBehavior(helper.generateAddCommand(toBeAdded),
              String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
              expectedAB,
              expectedAB.getTaskList());
    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
//        // setup expectations
//        TestDataHelper helper = new TestDataHelper();
//        Person toBeAdded = helper.adam();
//        AddressBook expectedAB = new AddressBook();
//        expectedAB.addPerson(toBeAdded);
//
//        // setup starting state
//        model.addPerson(toBeAdded); // person already in internal address book
//
//        // execute command and verify result
//        assertCommandBehavior(
//                helper.generateAddCommand(toBeAdded),
//                AddCommand.MESSAGE_DUPLICATE_PERSON,
//                expectedAB,
//                expectedAB.getPersonList());

    }


    @Test
    public void execute_list_showsAllPersons() throws Exception {
//        // prepare expectations
//        TestDataHelper helper = new TestDataHelper();
//        AddressBook expectedAB = helper.generateAddressBook(2);
//        List<? extends ReadOnlyPerson> expectedList = expectedAB.getPersonList();
//
//        // prepare address book state
//        helper.addToModel(model, 2);
//
//        assertCommandBehavior("list",
//                ListCommand.MESSAGE_SUCCESS,
//                expectedAB,
//                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
//        assertCommandBehavior(commandWord , expectedMessage); //index missing
//        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
//        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
//        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
//        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
//        String expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
//        TestDataHelper helper = new TestDataHelper();
//        List<Person> personList = helper.generatePersonList(2);
//
//        // set AB state to 2 persons
//        model.resetData(new AddressBook());
//        for (Person p : personList) {
//            model.addPerson(p);
//        }
//
//        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getAddressBook(), personList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
//        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
//        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        //assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectPerson() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        List<Person> threePersons = helper.generatePersonList(3);
//
//        AddressBook expectedAB = helper.generateAddressBook(threePersons);
//        helper.addToModel(model, threePersons);
//
//        assertCommandBehavior("select 2",
//                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
//                expectedAB,
//                expectedAB.getPersonList());
//        assertEquals(1, targetedJumpIndex);
//        assertEquals(model.getFilteredPersonList().get(1), threePersons.get(1));
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

        AddressBook expectedAB = helper.generateAddressBook(threeTasks);
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
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task cTarget1 = helper.generateTaskWithTitleAndDescription("bla bla KEY bla", "dummy");
        Task cTarget2 = helper.generateTaskWithTitleAndDescription("bla KEY bla bceofeia", "dummy");
        Task c1 = helper.generateTaskWithTitleAndDescription("KE Y", "dummy");
        Task c2 = helper.generateTaskWithTitleAndDescription("KEYKEYKEY sduauo", "dummy");

        List<Task> fourPersons = helper.generateTaskList(c1, cTarget1, c2, cTarget2);
        AddressBook expectedAB = helper.generateAddressBook(fourPersons);
        List<Task> expectedList = helper.generateTaskList(cTarget1, cTarget2);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
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
        AddressBook expectedAB = helper.generateAddressBook(fourPersons);
        List<Task> expectedList = fourPersons;
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
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
        AddressBook expectedAB = helper.generateAddressBook(fourPersons);
        List<Task> expectedList = helper.generateTaskList(cTarget1, cTarget2, cTarget3);
        helper.addToModel(model, fourPersons);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
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
        Task taskWithAttribute(boolean hasDescription, boolean hasTimeInterval, boolean hasCategory) throws Exception {
            Title title = new Title("My Task");
            Description description = null;
            TimeInterval timeInterval = null;
            UniqueCategoryList categories = new UniqueCategoryList();
            if (hasDescription) {
                description = new Description("Do my homework");
            }
            if (hasTimeInterval) {
                timeInterval = new TimeInterval("2016-10-03 14:00", "2016-10-04 15:00");
            }
            if (hasCategory) {
                Category category1 = new Category("CS2101");
                Category category2 = new Category("CS2103T");
                categories = new UniqueCategoryList(category1, category2);
            }
            
            return new Task(title, description, timeInterval, categories);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            DateTime sampleDate1 = DateTime.parse("2016-10-03 10:15", DateTimeFormat.forPattern(TimeInterval.TIME_STANDARD_FORMAT));
            DateTime sampleDate2 = DateTime.parse("2016-10-03 10:15", DateTimeFormat.forPattern(TimeInterval.TIME_STANDARD_FORMAT));
            return new Task(
                    new Title("Person " + seed),
                    new Description("" + Math.abs(seed)),
                    new TimeInterval(sampleDate1, sampleDate2.plusDays(seed)),
                    new UniqueCategoryList(new Category("CS" + Math.abs(seed)), new Category("CS" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task r) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");
            cmd.append("-t ").append(r.getTitle()).append(" ");
            
            if (r.hasDescription()) {
                cmd.append("-d ").append(r.getDescription()).append(" ");
            }
           
            if (r.hasTimeInterval()) {
                cmd.append("{").append(r.getTimeInterval()).append("} ");
            }
            
            UniqueCategoryList categories = r.getCategories();
            if (!categories.getInternalList().isEmpty()) {
                cmd.append("-c ");
                for(Category c: categories){
                    cmd.append(c.categoryName);
                }
            }
            
            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated tasks.
         */
        AddressBook generateAddressBook(int numGenerated) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, numGenerated);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of Tasks given.
         */
        AddressBook generateAddressBook(List<Task> tasks) throws Exception{
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, tasks);
            return addressBook;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         */
        void addToAddressBook(AddressBook addressBook, int numGenerated) throws Exception{
            addToAddressBook(addressBook, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given AddressBook
         */
        void addToAddressBook(AddressBook addressBook, List<Task> tasksToAdd) throws Exception{
            for(Task c: tasksToAdd){
                addressBook.addTask(c);
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
                    new TimeInterval("2016-10-03 14:00", "2016-10-04 15:00"),
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
