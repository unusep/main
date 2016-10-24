package seedu.doerList.model.category;

import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;

/**
 * Represents a Category in the doerList.
 * Guarantees: immutable; name is valid as declared in {@link #isValidCategoryName(String)}
 */
public class Category {

    public static final String MESSAGE_CATEGORY_CONSTRAINTS = "Categorys names can be in any format";
    public static final String CATEGORY_VALIDATION_REGEX = ".+";

    public String categoryName; 
    public Category() {}
    private ObservableList<ReadOnlyTask> filteredList = FXCollections.observableArrayList();
    
    /**
     * Validates given category name.
     *
     * @throws IllegalValueException if the given category name string is invalid.
     */
    public Category(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidCategoryName(name)) {
            throw new IllegalValueException(MESSAGE_CATEGORY_CONSTRAINTS);
        }
        this.categoryName = name;
    }

    /**
     * Returns true if a given string is a valid category name.
     */
    public static boolean isValidCategoryName(String test) {
        return test.matches(CATEGORY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && this.categoryName.equals(((Category) other).categoryName)); // state check
    }
    
    public void setFilteredTaskList(ObservableList<Task> theFilteredList) {
        assert theFilteredList != null;
        filteredList = new UnmodifiableObservableList<>(theFilteredList);
    }
    
    //@@author A0147978E
    public FilteredList<ReadOnlyTask> getTasks() {
        assert filteredList != null;
        return filteredList.filtered(this.getPredicate());
    }
    
    //@@author A0147978E
    public FilteredList<ReadOnlyTask> getOverdueTasks() {
        assert filteredList != null;
        return this.getTasks().filtered(BuildInCategoryList.DUE.getPredicate());
    }

    //@@author
    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + categoryName + ']';
    }
    
    //@@author A0147978E
    /**
     * Return predicate of the category to filter tasks
     * @return predicate(lambda) expression to help filter tasks 
     */
    public Predicate<ReadOnlyTask> getPredicate() {
        return (ReadOnlyTask task) -> {
            return task.getCategories().contains(this);
        };
    }
    
    //@@author A0147978E
    public boolean isBuildIn() {
        return false;
    }
    

}