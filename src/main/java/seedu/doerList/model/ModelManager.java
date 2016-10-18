package seedu.doerList.model;

import javafx.collections.transformation.FilteredList;
import seedu.doerList.commons.core.ComponentManager;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.events.model.DoerListChangedEvent;
import seedu.doerList.commons.util.StringUtil;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.TodoTime;
import seedu.doerList.model.task.UniqueTaskList;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
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
    }

    public ModelManager() {
        this(new DoerList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyDoerList initialData, UserPrefs userPrefs) {
        doerList = new DoerList(initialData);
        filteredTasks = new FilteredList<>(doerList.getTasks());
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
    public synchronized void replaceTask(int i, Task task) throws UniqueTaskList.DuplicateTaskException, TaskNotFoundException {
        doerList.replaceTask(i, task);
        updateFilteredListToShowAll();
        indicateDoerListChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<Category> getBuildInCategoryList() {
        return new UnmodifiableObservableList<>(doerList.getBuildInCategories());
    }
    
    @Override
    public UnmodifiableObservableList<Category> getCategoryList() {
        return new UnmodifiableObservableList<>(doerList.getCategories());
    }
    

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new TitleDescriptionQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskList(TodoTime deadline) {
        updateFilteredTaskList(new PredicateExpression(new TaskdueQualifier(deadline)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
        filteredTasks.setPredicate(predicate);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class TitleDescriptionQualifier implements Qualifier {
        private Set<String> titleDescriptionKeyWords;

        TitleDescriptionQualifier(Set<String> titleKeyWords) {
            this.titleDescriptionKeyWords = titleKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return titleDescriptionKeyWords.stream()
                    .filter(keyword -> {
                        return StringUtil.containsIgnoreCase(task.getTitle().fullTitle, keyword) ||
                                (task.hasDescription() && StringUtil.containsIgnoreCase(task.getDescription().value, keyword));
                        })
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "title||description=" + String.join(", ", titleDescriptionKeyWords);
        }
    }

    private class TaskdueQualifier implements Qualifier {
        private TodoTime deadline;

        TaskdueQualifier(TodoTime deadline) {
            this.deadline = deadline;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getEndTime().isBefore(deadline);
        }

        @Override
        public String toString() {
            return "";
        }
    }

}
