package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.category.Category;
import seedu.address.model.category.UniqueCategoryList;
import seedu.address.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueTaskList tasks;
    private final UniqueCategoryList categories;

    {
        tasks = new UniqueTaskList();
        categories = new UniqueCategoryList();
    }

    public AddressBook() {}

    /**
     * Tasks and Categories are copied into this addressbook
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueCategoryList());
    }

    /**
     * Tasks and Categories are copied into this addressbook
     */
    public AddressBook(UniqueTaskList tasks, UniqueCategoryList categories) {
        resetData(tasks.getInternalList(), categories.getInternalList());
    }

    public static ReadOnlyAddressBook getEmptyAddressBook() {
        return new AddressBook();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setCategories(Collection<Category> categories) {
        this.categories.getInternalList().setAll(categories);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Category> newCategories) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setCategories(newCategories);
    }

    public void resetData(ReadOnlyAddressBook newData) {
        resetData(newData.getTaskList(), newData.getCategoryList());
    }

//// task-level operations

    /**
     * Adds a task to the address book.
     * Also checks the new task's categories and updates {@link #categories} with any new categories found,
     * and updates the Category objects in the task to point to those in {@link #categories}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncCategoriesWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Ensures that every category in this task:
     *  - exists in the master list {@link #categories}
     *  - points to a Category object in the master list
     */
    private void syncCategoriesWithMasterList(Task task) {
        final UniqueCategoryList taskCategories = task.getCategories();
        categories.mergeFrom(taskCategories);

        // Create map with values = category object references in the master list
        final Map<Category, Category> masterCategoryObjects = new HashMap<>();
        for (Category category : categories) {
            masterCategoryObjects.put(category, category);
        }

        // Rebuild the list of task categories using references from the master list
        final Set<Category> commonCategoryReferences = new HashSet<>();
        for (Category category : taskCategories) {
            commonCategoryReferences.add(masterCategoryObjects.get(category));
        }
        task.setCategories(new UniqueCategoryList(commonCategoryReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// category-level operations

    public void addCategory(Category t) throws DuplicateCategoryException {
        categories.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + categories.getInternalList().size() +  " categories";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Category> getCategoryList() {
        return Collections.unmodifiableList(categories.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueCategoryList getUniqueCategoryList() {
        return this.categories;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.tasks.equals(((AddressBook) other).tasks)
                && this.categories.equals(((AddressBook) other).categories));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, categories);
    }
}
