package seedu.doerList.model;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList;
import seedu.doerList.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the doerList level
 * Duplicates are not allowed (by .equals comparison)
 */
public class DoerList implements ReadOnlyDoerList {

    private final UniqueTaskList tasks;
    private final UniqueCategoryList categories;
    private final BuildInCategoryList buildInCategories;

    {
        tasks = new UniqueTaskList();
        categories = new UniqueCategoryList();
        buildInCategories = new BuildInCategoryList();
        addListenerToCategoryList();
        buildInCategories.addAllBuildInCategories();
    }
    
    //@@author A0147978E
    /** 
     * Add listener to categoryList so that every time the category list get added,
     * the {@code tasks} is added into the category.
     */
    private void addListenerToCategoryList() {
        ListChangeListener<? super Category> listener = (ListChangeListener.Change<? extends Category> c) -> {
            while (c.next() && c.wasAdded()) {
                addTaskListToCategory(c.getAddedSubList());
            }
        };
        categories.getInternalList().addListener(listener);
    }

    private void addTaskListToCategory(List<? extends Category> category) {
        for(Category addedCategory : category) {
            addedCategory.setFilteredTaskList(getTasks());
        }
    }
    
    //@@author
    public DoerList() {}

    /**
     * Tasks and Categories are copied into this doerList
     */
    public DoerList(ReadOnlyDoerList toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueCategoryList());
    }

    /**
     * Tasks and Categories are copied into this doerList
     */
    public DoerList(UniqueTaskList tasks, UniqueCategoryList categories) {
        resetData(tasks.getInternalList(), categories.getInternalList());
    }

    public static ReadOnlyDoerList getEmptyDoerList() {
        return new DoerList();
    }

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }
    
    public ObservableList<Category> getCategories() {
        return categories.getInternalList();
    }
    
    //@@author A0147978E
    public ObservableList<Category> getBuildInCategories() {
        return buildInCategories.getInternalList();
    }

    //@@author
    public void setTasks(List<Task> tasks) {
        for(Task t : tasks) {
            syncCategoriesWithMasterList(t);
        }
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setCategories(Collection<Category> categories) {
        this.categories.getInternalList().setAll(categories);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Category> newCategories) {
        setCategories(newCategories.stream().map(Category::new).collect(Collectors.toList())); // the order matter
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList())); // set tasks must follow the set setCategories
    }

    public void resetData(ReadOnlyDoerList newData) {
        resetData(newData.getTaskList(), newData.getCategoryList());
    }

//// task-level operations

    /**
     * Adds a task to the doerList.
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
    
    //@@author A0147978E
    /**
     * Ensure that once the task {@code toRemove} is removed, categories of the task that have
     * no task will be deleted.
     * 
     * @param toRemove
     */
    private void syncCategroiesMaterListAfterRemove(ReadOnlyTask toRemove) {
        for(Category c : toRemove.getCategories()) {
            if (c.getTasks().size() == 0) {
                categories.getInternalList().remove(c);
            } 
        }
    }

    //@@author
    public boolean removeTask(ReadOnlyTask toRemove) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(toRemove)) {
            syncCategroiesMaterListAfterRemove(toRemove);
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    
    public void replaceTask(ReadOnlyTask prevTask, Task t) throws DuplicateTaskException, TaskNotFoundException {
        tasks.replace(prevTask, t);
        syncCategoriesWithMasterList(t); // if there is exception, this statement will not be executed
        syncCategroiesMaterListAfterRemove(prevTask);
    }


    //@@author A0139168W
    public void unmarkTask(ReadOnlyTask task) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.contains(task)) {
            task.removeBuildInCategory(BuildInCategoryList.COMPLETE);
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    public void markTask(ReadOnlyTask task) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.contains(task)) {
            task.addBuildInCategory(BuildInCategoryList.COMPLETE);
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
                || (other instanceof DoerList // instanceof handles nulls
                && this.tasks.equals(((DoerList) other).tasks)
                && this.categories.equals(((DoerList) other).categories));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, categories);
    }
}
