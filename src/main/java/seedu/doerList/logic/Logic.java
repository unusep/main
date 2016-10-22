package seedu.doerList.logic;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.doerList.logic.commands.CommandResult;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    void setPredicateForTaskList(Predicate<ReadOnlyTask> predicate);
    
    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the list of category */
    ObservableList<Category> getCategoryList();
    
    //@@author A0147978E
    /** Returns the list of build-in category */
    ObservableList<Category> getBuildInCategoryList();

}
