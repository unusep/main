package seedu.doerList.model;

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

import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the doerList data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DoerList doerList;
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
        BuildInCategoryList.setTasksSource(doerList.getTasks());
    }

    public ModelManager() {
        this(new DoerList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyDoerList initialData, UserPrefs userPrefs) {
        doerList = new DoerList(initialData);
        filteredTasks = new FilteredList<>(doerList.getTasks());
        BuildInCategoryList.setTasksSource(doerList.getTasks());
    }

    @Override
    public void resetData(ReadOnlyDoerList newData) {
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
        indicateDoerListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        doerList.addTask(task);
        updateFilteredListToShowAll();
        indicateDoerListChanged();
    }

    @Override
    public synchronized void replaceTask(ReadOnlyTask prevTask, Task task) throws UniqueTaskList.DuplicateTaskException, TaskNotFoundException {
        doerList.replaceTask(prevTask, task);
        indicateDoerListChanged();
    }

    //@@author A0139401N
    @Override
    public synchronized void recurTask(ReadOnlyTask task) throws TaskNotFoundException {
        doerList.recurTask(task);
        indicateDoerListChanged();
    }
    
    @Override
    public synchronized void markTask(ReadOnlyTask task) throws TaskNotFoundException {
        doerList.markTask(task);
        indicateDoerListChanged();
    }
    
    @Override
    public synchronized void unmarkTask(ReadOnlyTask task) throws TaskNotFoundException {
        doerList.unmarkTask(task);
        indicateDoerListChanged();
    }
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

}
