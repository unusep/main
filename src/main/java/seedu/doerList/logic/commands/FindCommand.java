//@@author A0147978E
package seedu.doerList.logic.commands;

import java.util.Set;
import java.util.function.Predicate;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.ui.JumpToCategoryEvent;
import seedu.doerList.commons.util.StringUtil;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;

/**
 * Finds and lists all tasks in doerList whose name or description contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names or description contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " study";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        updateCategoryAllPredicate();
        model.updateFilteredTaskList(BuildInCategoryList.ALL.getPredicate());
        EventsCenter.getInstance().post(new JumpToCategoryEvent(BuildInCategoryList.ALL));
        return new CommandResult(getMessageForTaskListShownSummary(BuildInCategoryList.ALL.getTasks().size()));
    }

    private void updateCategoryAllPredicate() {
        BuildInCategoryList.ALL.setToDefaultPredicate();
        BuildInCategoryList.ALL.updatePredicate(generateTaskPredicate(this.keywords));
    }

    /**
     * Generate a predicate to check whether a task's title or description
     * contain the given set of keywords
     * 
     * @param keywords A set of keywords
     * @return
     */
    private Predicate<ReadOnlyTask> generateTaskPredicate(Set<String> keywords) {
        return (ReadOnlyTask task) -> {
            return keywords.stream()
                    .filter(generateStringFilter(task))
                    .findAny().isPresent();
        };
    }
    
    /**
     * Generate a String predicate to check whether a string is contained
     * in the task's title and description
     * 
     * @param task Given task
     * @return
     */
    private Predicate<? super String> generateStringFilter(ReadOnlyTask task) {
        Predicate<? super String> predicate = (keyword -> {
                return StringUtil.containsIgnoreCase(task.getTitle().fullTitle, keyword) ||
                    (task.hasDescription() && StringUtil.containsIgnoreCase(task.getDescription().value, keyword));
            });
        return predicate;
    }

}
