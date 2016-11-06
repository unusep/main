//@@author A0139168W
package seedu.doerList.logic.commands;

import java.time.LocalDateTime;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.TodoTime;
import seedu.doerList.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.ui.TaskListPanel;

public class UnmarkCommand extends Command {
    
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": marks a 'done' task as 'not done'.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "unmark task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "The recurring marked task already exists in the Do-erlist";

    
    private int targetIndex;
    
    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        try {
            ReadOnlyTask target = TaskListPanel.getDisplayedIndexInUI(targetIndex, lastShownList);
            if (target.hasRecurring()){ 
                Task newTask = new Task(target);
                newTask = updateRecurringTask(newTask);
                model.replaceTask(target, newTask);
            } else {
                model.unmarkTask(target);
            }
            return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, target)); 
        } catch (TaskNotFoundException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (DuplicateTaskException e) {
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
        TodoTime updatedStart = null;
        TodoTime updatedEnd = null;
        if (original.hasStartTime()) {
            LocalDateTime withRecurStartTime = original.getStartTime().getTime();
            updatedStart = minusOnDate(withRecurStartTime, original);
        }
        if (original.hasEndTime()) {
            LocalDateTime withRecurEndTime = original.getEndTime().getTime();
            updatedEnd = minusOnDate(withRecurEndTime, original);
        }
        
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
  //@@author 
    
    
    //@@author A0139401N
    /**
     * Minus on the original date time with its recurring Interval
     *
     * @param dateTime TodoTime (to be updated) recurringInterval Task (values to update)
     * @return updatedTime with added on time
     */
    public TodoTime minusOnDate(LocalDateTime dateTime, Task recurringInterval){
        TodoTime updatedTime;
        long days = recurringInterval.getRecurring().getDays();
        long months = recurringInterval.getRecurring().getMonths();
        long years = recurringInterval.getRecurring().getYears();
        
        updatedTime = new TodoTime(dateTime.minusDays(days));
        updatedTime = new TodoTime(updatedTime.getTime().minusMonths(months));     
        updatedTime = new TodoTime(updatedTime.getTime().minusYears(years));  
        
        return updatedTime;
    }
  //@@author 
}
