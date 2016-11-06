package seedu.doerList.model;

import java.util.function.Predicate;

import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.model.undo.UndoManager;

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

    //@@author A0147978E
    /** Returns the build-in category list as an {@code UnmodifiableObservableList<Category>} */
    UnmodifiableObservableList<Category> getBuildInCategoryList();

    //@@author
    /** Returns the user-created category list as an {@code UnmodifiableObservableList<Category>} **/
    UnmodifiableObservableList<Category> getCategoryList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    //@@author A0147978E
    /** Updates the filter of the filtered task list to filter by the given predicate*/
    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);

    //@@author A0140905M
    /** Replace a task with given task*/
    void replaceTask(ReadOnlyTask toReplace, Task task) throws UniqueTaskList.DuplicateTaskException, TaskNotFoundException;

    //@@author A0139168W
    /** Marks the given task as undone */
    void unmarkTask(ReadOnlyTask task) throws TaskNotFoundException;

    //@@author A0139168W
    /** Marks the given task as done */
    void markTask(ReadOnlyTask task) throws TaskNotFoundException;

    //@@author A0147978E
    /** Undo the recent addition/edition/deletion of task */
    void undo() throws UndoManager.OperationFailException;

    //@@author A0147978E
    /** Redo the recent addition/edition/deletion of task */
    void redo() throws UndoManager.OperationFailException;
}
