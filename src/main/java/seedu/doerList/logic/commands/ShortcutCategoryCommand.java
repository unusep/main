package seedu.doerList.logic.commands;

import java.util.Optional;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.ui.JumpToCategoryEvent;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;

/**
 * Allows the user to find and lists all tasks within pre-set categories by typing the names of the
 * categories out
 */
public class ShortcutCategoryCommand extends Command {

    public static final String COMMAND_WORD_1 = "All";
    public static final String COMMAND_WORD_2 = "Today";
    public static final String COMMAND_WORD_3 = "Next";
    public static final String COMMAND_WORD_4 = "Inbox";
    public static final String COMMAND_WORD_5 = "Complete";

    public static final String MESSAGE_SUCCESS = "Listed all tasks from category: %1$s";
    public static final String MESSAGE_CATEGORY_NOT_EXISTS = "The category name does not exist";


    public String defaultCategories;

    public ShortcutCategoryCommand() {}

    public ShortcutCategoryCommand(String shortcut) {
        defaultCategories = shortcut;
    }

    /**
     * Try find category name that equals to keyword
     * 
     * @param keyword
     * @return Optional<Category> indicates whether find it or not 
     */
    public Optional<Category> findNameInCategory(String keyword) {
        if (keyword == null) {
            return Optional.of(BuildInCategoryList.ALL);
        }
        for(Category c : model.getBuildInCategoryList()) {
            if (c.categoryName.equals(keyword)) {
                return Optional.of(c);
            }
        }
        for(Category c : model.getCategoryList()) {
            if (c.categoryName.equals(keyword)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @Override
    public CommandResult execute() {
        Category toSelectCategory;
        BuildInCategoryList.resetBuildInCategoryPredicate();
        Optional<Category> fromCategory = findNameInCategory(defaultCategories);
        if (fromCategory.isPresent()) {
            toSelectCategory = fromCategory.get();
        } else {
            return new CommandResult(MESSAGE_CATEGORY_NOT_EXISTS);
        }
        model.updateFilteredListToShowAll();
        model.updateFilteredTaskList(toSelectCategory.getPredicate());
        EventsCenter.getInstance().post(new JumpToCategoryEvent(toSelectCategory));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSelectCategory.categoryName));
    }
}
