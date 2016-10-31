//@@author A0139168W
package seedu.doerList.logic.commands;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.ModelManager;
import seedu.doerList.model.task.*;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.ui.TaskListPanel;

public class MarkCommand extends Command {
    //private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": To mark this task as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "marked task: %1$s";
    public static final String MESSAGE_MARK_RECUR_TASK_SUCCESS = "marked and updated recurring task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "The recurring marked task already exists in the Do-erlist";

    private int targetIndex;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    //@@author A0139401N
    public CommandResult execute() {       
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        try {
            ReadOnlyTask target = TaskListPanel.getDisplayedIndexWhenCategorizedByBuildInCategory(targetIndex, lastShownList);
            if (target.hasRecurring()){ 
                Task newTask = new Task(target);
                newTask = updateRecurringTask(newTask);
                model.replaceTask(target, newTask, true);
                return new CommandResult(String.format(MESSAGE_MARK_RECUR_TASK_SUCCESS, target));  
            } else {
                model.markTask(target);
                return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, target));  
            }
        } catch (TaskNotFoundException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
    
    
    //@@author A0139401N
    /**
     * Update a new task's start and end time based on their recurring values
     *
     * @param original Task (Task before update)
     * @return newTask with updated information
     */
    private Task updateRecurringTask(Task original) {
        LocalDateTime withRecurStartTime = original.getStartTime().getTime();
        LocalDateTime withRecurEndTime = original.getEndTime().getTime();
        
        TodoTime updatedStart = new TodoTime(addingOnDate(withRecurStartTime, original));
        TodoTime updatedEnd = new TodoTime(addingOnDate(withRecurEndTime, original));

        Task newTask = new Task(
                original.getTitle(),
                original.getDescription(),
                updatedStart,
                updatedEnd,
                original.getRecurring(),
                original.getCategories()
                );
        newTask.setBuildInCategories(original.getBuildInCategories());
        return newTask;
    }
    
    //@@author A0139401N
    /**
     * Adds on the original date time with its recurring Interval
     *
     * @param dateTime LocalDateTime (to be updated) recurringInterval Task (values to update)
     * @return AddedDate with updated information
     */
    public LocalDateTime addingOnDate(LocalDateTime dateTime, Task recurringInterval){
        long days = recurringInterval.getRecurring().getValue().getDayOfYear();
        System.out.println("Recurring Days: " + days);
        long months = recurringInterval.getRecurring().getValue().getMonthValue();
        System.out.println("Recurring Months: " + months);
        long years = recurringInterval.getRecurring().getValue().getYear() - recurringInterval.getRecurring().YEAR_PARSER;
        System.out.println("Recurring Years: " + years);
        
        LocalDateTime AddedDate;
        AddedDate = dateTime.plusDays(days);
        System.out.println("AddedDate after adding on the DAYS: " + AddedDate.toString());
        AddedDate = dateTime.plusMonths(months);       
        System.out.println("AddedDate after adding on the MONTHS: " + AddedDate.toString());
        AddedDate = dateTime.plusYears(years);
        System.out.println("AddedDate after adding on the YEARS: " + AddedDate.toString());
        
        return AddedDate;
    }

}
