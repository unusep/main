package seedu.address.testutil;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.AddressBook;
import seedu.doerList.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withTitle("CA1 Guide").withDescription("Do your homework 1")
                    .withTimeInterval("2016-10-03 13:00", "2016-10-04 12:00")
                    .withTags("CS2101").build();
            benson = new TaskBuilder().withTitle("Guide CA2").withDescription("Do your homework 2")
                    .withTimeInterval("2016-10-03 13:00", "2016-10-04 12:00")
                    .withTags("CS2103", "CS2101").build();
            carl = new TaskBuilder().withTitle("Guide Tutorial")
                    .withTimeInterval("2016-10-03 13:00", "2016-10-04 12:00").build();
            daniel = new TaskBuilder().withTitle("Guide Math").withDescription("Do your homework 3")
                    .withTimeInterval("2016-10-03 13:00", "2016-10-04 12:00").build();
            elle = new TaskBuilder().withTitle("Math Test")
                    .withTimeInterval("2016-10-03 13:00", "2016-10-04 12:00").build();
            fiona = new TaskBuilder().withTitle("Lecture Quiz").withDescription("Do your homework 4")
                    .withTimeInterval("2016-10-03 13:00", "2016-10-04 12:00").build();
            george = new TaskBuilder().withTitle("Quiz 1").build();

            //Manually added
            hoon = new TaskBuilder().withTitle("Do quiz 1").withDescription("quiz 1").build();
            ida = new TaskBuilder().withTitle("Do quiz 2").withDescription("quiz 2").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTask() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
