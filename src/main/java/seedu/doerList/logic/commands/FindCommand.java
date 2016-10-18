package seedu.doerList.logic.commands;

import java.util.Set;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.ui.JumpToCategoryEvent;
import seedu.doerList.commons.util.StringUtil;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;

/**
 * Finds and lists all tasks in doerList whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
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
        BuildInCategoryList.resetBuildInCategoryPredicate();
        BuildInCategoryList.ALL.updatePredicate((ReadOnlyTask task) -> {
            return this.keywords.stream()
            .filter(keyword -> {
                return StringUtil.containsIgnoreCase(task.getTitle().fullTitle, keyword) ||
                        (task.hasDescription() && StringUtil.containsIgnoreCase(task.getDescription().value, keyword));
                })
            .findAny()
            .isPresent();
        });
        EventsCenter.getInstance().post(new JumpToCategoryEvent(BuildInCategoryList.ALL));
        model.updateFilteredTaskList(BuildInCategoryList.ALL.getPredicate());
        return new CommandResult(getMessageForTaskListShownSummary(BuildInCategoryList.ALL.getTasks().size()));
    }

}
