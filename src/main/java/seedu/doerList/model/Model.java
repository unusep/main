package seedu.doerList.model;

import java.util.Set;
import java.util.function.Predicate;

import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList;
import seedu.doerList.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyDoerList newData);

    /** Returns the DoerList */
    ReadOnlyDoerList getDoerList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the build-in category list as an {@code UnmodifiableObservableList<Category>} */
    UnmodifiableObservableList<Category> getBuildInCategoryList();
    
    /** Returns the user-created category list as an {@code UnmodifiableObservableList<Category>} **/
    UnmodifiableObservableList<Category> getCategoryList();
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given predicate*/
    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);

    void replaceTask(int i, Task task) throws UniqueTaskList.DuplicateTaskException, TaskNotFoundException;
    
    void unmarkTask(Task task) throws TaskNotFoundException;

}
