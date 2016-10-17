package seedu.doerList.testutil;

import org.joda.time.DateTime;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.*;

/**
 *
 */
public class TypicalTestTasks {
   
    public static TestTask task1, task2, task3, task4, task5, task6, task7, task8, task9, task10;

    public TypicalTestTasks() {
        try {
            task1 =  new TaskBuilder().withTitle("CA1 Guide").withDescription("Do your homework 1")
                    .withStartTime(new DateTime().withHourOfDay(8))
                    .withEndTime(new DateTime().withHourOfDay(12))
                    .withCategories("CS2101").build(); // task today
            task2 = new TaskBuilder().withTitle("Lecture Quiz 2").withDescription("Do your homework 5")
                    .withStartTime(new DateTime().withHourOfDay(8))
                    .withEndTime(new DateTime().withHourOfDay(12))
                    .withBuildInCategories(BuildInCategoryList.COMPLETE).build(); // task today complete
            task3 = new TaskBuilder().withTitle("Guide Tutorial")
                    .withStartTime(new DateTime().withHourOfDay(8).plusDays(1))
                    .withEndTime(new DateTime().withHourOfDay(12).plusDays(1)).build(); // task tomorrow
            task4 = new TaskBuilder().withTitle("Guide Math").withDescription("Do your homework 3")
                    .withStartTime(new DateTime().withHourOfDay(8).plusDays(3))
                    .withEndTime(new DateTime().withHourOfDay(12).plusDays(3)).build(); // task next 7 days
            task5 = new TaskBuilder().withTitle("Math Test").build(); // inbox
            task6 = new TaskBuilder().withTitle("Guide CA2").withDescription("Do your homework 2 Math")
                    .withStartTime(new DateTime().withHourOfDay(8).minusDays(1))
                    .withEndTime(new DateTime().withHourOfDay(12).minusDays(1)) // task due yesterdays
                    .withCategories("CS2103", "CS2101").build();
            task7 = new TaskBuilder().withTitle("Lecture Quiz 1").withDescription("Do your homework 4")
                    .withStartTime(new DateTime().withHourOfDay(8).minusDays(3))
                    .withEndTime(new DateTime().withHourOfDay(12).minusDays(3)).build(); // task due several days before
            
            //Manually added
            task8 = new TaskBuilder().withTitle("Do quiz 1").withDescription("quiz 1")
                    .withStartTime(new DateTime().withHourOfDay(14))
                    .withEndTime(new DateTime().withHourOfDay(15))
                    .withCategories("CS2103").build(); // task today
            task9 = new TaskBuilder().withTitle("Do quiz 2").withDescription("quiz 2")
                    .withStartTime(new DateTime().withHourOfDay(16).plusDays(1))
                    .withEndTime(new DateTime().withHourOfDay(17).plusDays(1)).build(); // task tomorrow
            task10 = new TaskBuilder().withTitle("Hai Long Birthday").withDescription("Bring Gift")
                    .withCategories("Life").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadDoerListWithSampleData(DoerList ab) {

        try {
            ab.addTask(new Task(task1));
            ab.addTask(new Task(task2));
            ab.addTask(new Task(task3));
            ab.addTask(new Task(task4));
            ab.addTask(new Task(task5));
            ab.addTask(new Task(task6));
            ab.addTask(new Task(task7));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{task1, task2, task3, task4, task5, task6, task7};
    }
    
    /**
     * The category name is associate with {@link getTypicalTasks()}
     * 
     * @return TestCategory[]
     */
    public TestCategory[] getTypicalTestCategory() {
        try {
            return new TestCategory[]{new TestCategory("CS2101", 2), new TestCategory("CS2103", 1)};
        } catch (IllegalValueException e) {
            // impossible
            return null;
        }
    }

    public DoerList getTypicalDoerList(){
        DoerList ab = new DoerList();
        loadDoerListWithSampleData(ab);
        return ab;
    }
}
