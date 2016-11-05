package seedu.doerList.testutil;

import java.time.LocalDateTime;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.commons.util.TimeUtil;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList;

/** Class to provide typical test data for test cases*/
public class TypicalTestTasks {
   
    public TestTask task1, task2, task3, task4, task5, task6, task7, task8, task9, task10, task11, task12;

    //@@author A0147978E
    public TypicalTestTasks() {
        try {
            task1 = new TaskBuilder().withTitle("Lecture Quiz 1").withDescription("Do your homework 4")
                    .withStartTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(10).minusDays(3))
                    .withEndTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12).minusDays(3))
                    .withBuildInCategories(BuildInCategoryList.COMPLETE)
                    .build(); // task due several days complete
            task2 = new TaskBuilder().withTitle("Guide CA2").withDescription("Do your homework 2 Math")
                    .withEndTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12).minusDays(1)) // task due yesterdays
                    .withCategories("CS2101", "CS2103").build();
            task3 =  new TaskBuilder().withTitle("CA1 Guide").withDescription("Do your homework 1")
                    .withStartTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(8))
                    .withCategories("CS2101").withRecurring("daily").build(); // task today recurring daily
            task4 = new TaskBuilder().withTitle("Lecture Quiz 2").withDescription("Do your homework 5")
                    .withStartTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(8))
                    .withEndTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12)).withCategories("MA1101R")
                    .withBuildInCategories(BuildInCategoryList.COMPLETE).build(); // task today complete
            task5 = new TaskBuilder().withTitle("Guide Tutorial")
                    .withStartTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(8).plusDays(1))
                    .withEndTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12).plusDays(1))
                    .withBuildInCategories(BuildInCategoryList.COMPLETE).build(); // task tomorrow complete
            task6 = new TaskBuilder().withTitle("Guide Math").withDescription("Do your homework 3")
                    .withEndTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12).plusDays(3)).build(); // task next 7 days
            task7 = new TaskBuilder().withTitle("Math Test").withDescription("Hello").build(); // inbox
            task8 = new TaskBuilder().withTitle("Math Test 2")
                    .withBuildInCategories(BuildInCategoryList.COMPLETE)
                    .build(); // inbox complete
            
            //Manually added
            task9 = new TaskBuilder().withTitle("Lecture Quiz 0").withDescription("pre-course reflection")
                    .withStartTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(8).minusDays(2))
                    .withEndTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(12).minusDays(2)).withCategories("Urgent")
                    .build(); // task due yesterday
            task10 = new TaskBuilder().withTitle("Do quiz 1").withDescription("quiz 1")
                    .withEndTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(15))
                    .withCategories("CS2103").build(); // task today
            task11 = new TaskBuilder().withTitle("Do quiz 2").withDescription("quiz 2")
                    .withStartTime(TimeUtil.getStartOfDay(LocalDateTime.now()).plusHours(16).plusDays(1)).build(); // task tomorrow
            task12 = new TaskBuilder().withTitle("Hai Long's Birthday").withDescription("Bring Gift")
                    .withCategories("Life")
                    .build(); // inbox
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public void loadDoerListWithSampleData(DoerList ab) {
        try {
            ab.addTask(new Task(task1));
            ab.addTask(new Task(task2));
            ab.addTask(new Task(task3));
            ab.addTask(new Task(task4));
            ab.addTask(new Task(task5));
            ab.addTask(new Task(task6));
            ab.addTask(new Task(task7));
            ab.addTask(new Task(task8));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public DoerList getTypicalDoerList(){
        DoerList ab = new DoerList();
        loadDoerListWithSampleData(ab);
        return ab;
    }
}
