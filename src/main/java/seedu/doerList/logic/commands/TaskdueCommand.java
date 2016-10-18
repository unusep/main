package seedu.doerList.logic.commands;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.ui.JumpToCategoryEvent;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.logic.parser.TimeParser;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.TodoTime;

/**
 * Finds and lists all tasks that have end time before or on the time specified
 * by the argument
 */
public class TaskdueCommand extends Command {

    public static final String COMMAND_WORD = "taskdue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all tasks that have deadline before or on the time specified by the end time.\n"
            + "Parameters: END_TIME \n" + "Example: " + COMMAND_WORD
            + " tomorrow";

    private final String endTime;

    public TaskdueCommand(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public CommandResult execute() {
        try {
            String time = new TimeParser().parse(endTime);
            DateTime deadline = DateTime.parse(time, DateTimeFormat.forPattern(TodoTime.TIME_STANDARD_FORMAT));
            
            model.updateFilteredListToShowAll();
            model.updateFilteredTaskList((ReadOnlyTask task) -> {
                return task.hasEndTime() && task.getEndTime().value.isBefore(deadline);
            });
                 
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        }
    }
}
