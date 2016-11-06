package seedu.doerList.model;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.doerList.commons.core.ComponentManager;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.events.model.DoerListChangedEvent;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.doerList.model.undo.Operation;
import seedu.doerList.model.undo.UndoManager;

/**
 * Represents the in-memory model of the doerList data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DoerList doerList;
    private final UndoManager undoManager;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given DoerList
     * DoerList and its variables should not be null
     */
    public ModelManager(DoerList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with doerList: " + src + " and user prefs " + userPrefs);

        doerList = new DoerList(src);
        filteredTasks = new FilteredList<>(doerList.getTasks());
        undoManager = new UndoManager();
        BuildInCategoryList.setTasksSource(doerList.getTasks());
    }

    public ModelManager() {
        this(new DoerList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyDoerList initialData, UserPrefs userPrefs) {
        doerList = new DoerList(initialData);
        filteredTasks = new FilteredList<>(doerList.getTasks());
        undoManager = new UndoManager();
        BuildInCategoryList.setTasksSource(doerList.getTasks());
    }

    @Override
    public void resetData(ReadOnlyDoerList newData) {
        undoManager.recordReset(this.doerList, newData);
        undoManager.resetRedoStack();
        doerList.resetData(newData);
        indicateDoerListChanged();
    }

    @Override
    public ReadOnlyDoerList getDoerList() {
        return doerList;
    }

    /** Raises an task to indicate the model has changed */
    private void indicateDoerListChanged() {
        raise(new DoerListChangedEvent(doerList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        doerList.removeTask(target);
        undoManager.recordDelete(target);
        undoManager.resetRedoStack();
        indicateDoerListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        doerList.addTask(task);
        undoManager.recordAdd(task);
        undoManager.resetRedoStack();
        updateFilteredListToShowAll();
        indicateDoerListChanged();
    }

    //@@author A0140905M
    @Override
    public synchronized void replaceTask(ReadOnlyTask prevTask, Task task) throws UniqueTaskList.DuplicateTaskException, TaskNotFoundException {
        doerList.replaceTask(prevTask, task);
        undoManager.recordEdit(prevTask, task);
        undoManager.resetRedoStack();
        indicateDoerListChanged();
    }
    //@@author

    //@@author A0139168W
    @Override
    public synchronized void markTask(ReadOnlyTask task) throws TaskNotFoundException {
        doerList.markTask(task);
        undoManager.recordMark(task);
        undoManager.resetRedoStack();
        indicateDoerListChanged();
    }

    //@@author A0139168W
    @Override
    public synchronized void unmarkTask(ReadOnlyTask task) throws TaskNotFoundException {
        doerList.unmarkTask(task);
        undoManager.recordUnmark(task);
        undoManager.resetRedoStack();
        indicateDoerListChanged();
    }
    //@@author
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0147978E
    @Override
    public UnmodifiableObservableList<Category> getBuildInCategoryList() {
        return new UnmodifiableObservableList<>(doerList.getBuildInCategories());
    }

     //@@author
    @Override
    public UnmodifiableObservableList<Category> getCategoryList() {
        return new UnmodifiableObservableList<>(doerList.getCategories());
    }


    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    //@@author A0147978E
    /**
     * Update the predicate of the {@code filteredTasks}
     */
    @Override
    public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
        filteredTasks.setPredicate(predicate);
    }

  //=========== Undo redo operation ===============================================================

    @Override
    public void undo() throws UndoManager.OperationFailException {
        try {
            Operation op = undoManager.pullUndoStack();
            op.setData(this.doerList);
            op.execute();
            indicateDoerListChanged();
        } catch (Exception e) {
            throw new UndoManager.OperationFailException(e.getMessage());
        }
    }

    @Override
    public void redo() throws UndoManager.OperationFailException {
        Operation op;
        try {
            op = undoManager.pullRedoStack();
            op.setData(this.doerList);
            op.execute();
            indicateDoerListChanged();
        } catch (Exception e) {
            throw new UndoManager.OperationFailException(e.getMessage());
        }
    }

}
