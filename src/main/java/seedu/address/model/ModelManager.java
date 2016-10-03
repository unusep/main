package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(AddressBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new AddressBook(src);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyAddressBook initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an task to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new TitleQualifier(keywords)));
        //TODO: add description filter
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
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

    private class TitleQualifier implements Qualifier {
        private Set<String> titleKeyWords;

        TitleQualifier(Set<String> titleKeyWords) {
            this.titleKeyWords = titleKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return titleKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getTitle().fullTitle, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "title=" + String.join(", ", titleKeyWords);
        }
    }
    
    private class DescriptionQualifier implements Qualifier {
        private Set<String> descriptionKeyWords;

        DescriptionQualifier(Set<String> descriptionKeyWords) {
            this.descriptionKeyWords = descriptionKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.hasDescription() && descriptionKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDescription().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "description=" + String.join(", ", descriptionKeyWords);
        }
    }

}
