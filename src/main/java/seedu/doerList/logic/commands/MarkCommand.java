//@@author A0139168W
package seedu.doerList.logic.commands;

import java.time.LocalDateTime;

import seedu.doerList.commons.core.Messages;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.task.*;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.ui.TaskListPanel;

public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": To mark this task as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "marked task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "The recurring marked task already exists in the Do-erlist";

    private int targetIndex;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    

    //@@author A0139401N
    public CommandResult execute() {       
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        try {
            ReadOnlyTask target = TaskListPanel.getDisplayedIndexInUI(targetIndex, lastShownList);
            if (target.hasRecurring()){ 
                Task newTask = new Task(target);
                newTask = updateRecurringTask(newTask);
                model.replaceTask(target, newTask);
            } else {
                model.markTask(target);
            }
            return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, target));
        } catch (TaskNotFoundException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
    
    
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
            updatedStart = addingOnDate(withRecurStartTime, original);
        }
        if (original.hasEndTime()) {
            LocalDateTime withRecurEndTime = original.getEndTime().getTime();
            updatedEnd = addingOnDate(withRecurEndTime, original);
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
    
    
    //@@author A0139401N
    /**
     * Adds on the original date time with its recurring Interval
     *
     * @param dateTime TodoTime (to be updated) recurringInterval Task (values to update)
     * @return updatedTime with added on time
     */
    public TodoTime addingOnDate(LocalDateTime dateTime, Task recurringInterval){
        TodoTime updatedTime;
        long days = recurringInterval.getRecurring().getDays();
        long months = recurringInterval.getRecurring().getMonths();
        long years = recurringInterval.getRecurring().getYears();
        
        updatedTime = new TodoTime(dateTime.plusDays(days));
        updatedTime = new TodoTime(updatedTime.getTime().plusMonths(months));     
        updatedTime = new TodoTime(updatedTime.getTime().plusYears(years));  
        
        return updatedTime;
    }
  //@@author 

}
