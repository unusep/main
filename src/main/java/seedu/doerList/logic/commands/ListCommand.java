package seedu.doerList.logic.commands;

import java.util.Optional;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.ui.JumpToCategoryEvent;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;

/**
 * Lists all persons in the doerList to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed tasks from category: %1$s";
    public static final String MESSAGE_CATEGORY_NOT_EXISTS = "The category name does not exist";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks in the Do-erlist with or without category. "
            + "Parameters: [CATEGORY] \n"
            + "Example: " + COMMAND_WORD
            + " CS2102";


    public String toSelectCategoryName;
    
    public ListCommand() {}
    
    public ListCommand(String categoryName) {
        toSelectCategoryName = categoryName;
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
        Optional<Category> fromCategory = findNameInCategory(toSelectCategoryName);
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
