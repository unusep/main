//@@author A0139168W
package seedu.doerList.logic.commands;

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
                updateRecurringTask(newTask);
                model.replaceTask(target, newTask);
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
     * @return Task with updated information
     */
    private Task updateRecurringTask(Task original) {

        int updateDay = original.getRecurring().getValue().getDayOfYear();
        int updateMonth = original.getRecurring().getValue().getMonthValue();
        int updateYear = original.getRecurring().getValue().getYear() - 2000;
        TodoTime withRecurEndTime = original.getEndTime();
        TodoTime withRecurStartTime = original.getStartTime();

        // Updating Day
        withRecurEndTime.getTime().plusDays((long)updateDay);
        withRecurStartTime.getTime().plusDays((long)updateDay);

        // Updating Month
        withRecurEndTime.getTime().plusMonths((long)updateMonth);
        withRecurStartTime.getTime().plusMonths((long)updateMonth);

        // Updating Year
        withRecurEndTime.getTime().plusYears((long)updateYear);
        withRecurStartTime.getTime().plusYears((long)updateYear);

        Task newTask = new Task(
                original.getTitle(),
                original.getDescription(),
                withRecurStartTime,
                withRecurEndTime,
                original.getRecurring(),
                original.getCategories()
                );
        newTask.setBuildInCategories(original.getBuildInCategories());
        return newTask;
    }

}
