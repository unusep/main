
package seedu.doerList.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.ui.JumpToCategoryEvent;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.*;

/**
 * Adds a task to the to-do List.
 */
public class AddCommand extends Command {


    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the Do-erlist. "
            + "Parameters: /t TASK [/d DESCRIPTION] [/s START] [/e END] [/c [CATEGORY] ... \n"
            + "Example: " + COMMAND_WORD
            + " /t Take lecture /s 2016-10-04 10:00 /e 2016-10-04 12:00 /c CS2102";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Do-erlist";

    private final Task toAdd;


    /**
     * Add a floating task (task with no start time and DeadLine)
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String title, String description, String startTime, String endTime, Set<String> categories)
    		throws IllegalValueException {
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(new Category(categoryName));
        }

        this.toAdd = new Task(
        		new Title(title.trim()),
        		description == null ? null : new Description(description.trim()),
        		startTime == null ? null : new TodoTime(startTime),
        		endTime == null ? null : new TodoTime(endTime),
        		new UniqueCategoryList(categorySet)
        );

    }
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            BuildInCategoryList.resetBuildInCategoryPredicate();
            EventsCenter.getInstance().post(new JumpToCategoryEvent(BuildInCategoryList.ALL));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

}
