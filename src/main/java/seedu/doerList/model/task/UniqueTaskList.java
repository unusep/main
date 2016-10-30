package seedu.doerList.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.doerList.commons.exceptions.DuplicateDataException;
import seedu.doerList.commons.util.CollectionUtil;
import seedu.doerList.model.category.BuildInCategoryList;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching person in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicatePersonException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    /**
     *
     * @return
     * @throws TaskNotFoundException, DuplicateTaskException
     */
    public void replace(ReadOnlyTask prevTask, Task toReplace) throws DuplicateTaskException, TaskNotFoundException {
        assert toReplace != null && prevTask != null;
        int i = 0;
        // try to find the index of the task
        for(ReadOnlyTask t : internalList) {
            if (t.equals(prevTask)) {
                break;
            }
            i++;
        }
        if (i >= internalList.size()) {
            throw new TaskNotFoundException();
        }
        Task original = internalList.get(i);
        if (contains(toReplace) && 
                !(original.equals(toReplace) && !toReplace.getCategories().equals(original.getCategories()))) {
            // is not just update categories
            throw new DuplicateTaskException();
        }
        internalList.set(i, toReplace);
    }
    
    /**
    *
    * @return
    * @throws TaskNotFoundException, DuplicateTaskException
    */
   public void change(ReadOnlyTask prevTask, Task toReplace) throws TaskNotFoundException {
       assert toReplace != null && prevTask != null;
       int i = 0;
       // try to find the index of the task
       for(ReadOnlyTask t : internalList) {
           if (t.equals(prevTask)) {
               break;
           }
           i++;
       }
       if (i >= internalList.size()) {
           throw new TaskNotFoundException();
       }
       Task original = internalList.get(i);
       internalList.set(i, toReplace);
   }
    
    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
