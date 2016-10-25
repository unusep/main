//@@author A0139401N
package seedu.doerList.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.Description;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.Title;
import seedu.doerList.model.task.TodoTime;
import seedu.doerList.model.task.UniqueTaskList;

/**
 *  Creates a list of reserved dates for people so as to notice if they are busier on that day
 */
public class ReserveCommand extends Command {
    
    public static final String COMMAND_WORD = "reserve";

    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": /t TASK /s START /e END\n"
            + "Parameters: TASK_NAME [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " t/ Exam Period /s 1 Nov 10:00 /e 10 Nov 20:00";

    public static final String MESSAGE_SUCCESS = "Time period reserved: %1$s";
    public static final String MESSAGE_PERIOD_RESERVED = "Time period is already reserved with other exisiting tasks.";

    public static final String RESERVE_DESCRIPTION = "Time period reserved";
    public static final String RESERVE_NAME = "RESERVED";
    //public static final String MESSAGE_ILLEGAL_TIME_EXCEPTION = "End time must be later than Start time.";

    private final Task toReserve;

    /**
     * Reserve a floating task (task with no start time and DeadLine)
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public ReserveCommand(String title, String startTime, String endTime) throws IllegalValueException {
        final Set<Category> categorySet = new HashSet<>();
        categorySet.add(new Category(RESERVE_NAME));

        this.toReserve = new Task(
                new Title(title.trim()),
                new Description(RESERVE_DESCRIPTION.trim()),
                new TodoTime(startTime),
                new TodoTime(endTime),
                new UniqueCategoryList(categorySet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        try {
            model.addTask(toReserve);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toReserve));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_PERIOD_RESERVED);
        }
        
    }

}