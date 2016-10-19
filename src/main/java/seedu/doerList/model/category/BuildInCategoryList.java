package seedu.doerList.model.category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.util.CollectionUtil;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.*;

import org.joda.time.DateTime;

/**
 * A list of categories that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Category#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class BuildInCategoryList implements Iterable<Category> {
    
    public static final BuildInCategory ALL;
    public static final BuildInCategory TODAY;
    public static final BuildInCategory NEXT;
    public static final BuildInCategory INBOX;
    public static final BuildInCategory COMPLETE;
    public static final BuildInCategory DUE;
       
    static {
        try {
            ALL = new BuildInCategory("All", (task) -> {return true;});     
            INBOX = new BuildInCategory("Inbox", (task) -> {
                return task.isFloatingTask();
            });
            COMPLETE = new BuildInCategory("Complete", (task) -> {
                return task.getBuildInCategories().contains(BuildInCategoryList.COMPLETE);
            });
            TODAY = new BuildInCategory("Today", (task) -> {                
                DateTime todayBegin = new DateTime().withTimeAtStartOfDay();
                DateTime todayEnd = todayBegin.plusDays(1);  
                if (!INBOX.getPredicate().test(task)) {
                    if (task.hasStartTime() && !task.hasEndTime()) {
                        return task.getStartTime().value.isBefore(todayEnd);
                    } else if (task.hasEndTime() && !task.hasStartTime()) {
                        return task.getEndTime().value.isAfter(todayBegin) &&
                                task.getEndTime().value.isBefore(todayEnd);
                    } else {
                        // interval match
                        if (task.getEndTime().value.isAfter(todayBegin) 
                                && task.getStartTime().value.isBefore(todayEnd)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            });
            NEXT = new BuildInCategory("Next", (task) -> {
                DateTime todayEnd = new DateTime().withTimeAtStartOfDay().plusDays(1);     
                if (task.hasStartTime()) {
                    return task.getStartTime().value.isAfter(todayEnd);
                } else if (task.hasEndTime()) {
                    return task.getEndTime().value.isAfter(todayEnd);
                } else {
                    return false;
                }
            });
            DUE = new BuildInCategory("Overdue", (task) -> {
                DateTime todayBegin = new DateTime().withTimeAtStartOfDay();     
                return !task.getBuildInCategories().contains(BuildInCategoryList.COMPLETE) &&
                        task.hasEndTime() && task.getEndTime().value.isBefore(todayBegin);
            });
        } catch (Exception e) {
            e.printStackTrace();
            // impossible
            throw new RuntimeException("Could not init class.", e);
        }
    }
    
    public static void resetBuildInCategoryPredicate() {
        ALL.setToDeafultPredicate();
        TODAY.setToDeafultPredicate();
        NEXT.setToDeafultPredicate();
        INBOX.setToDeafultPredicate();
        COMPLETE.setToDeafultPredicate();
        DUE.setToDeafultPredicate();
    }
    
    public static void setTasksSource(ObservableList<Task> observableList) {
        ALL.setFilteredTaskList(observableList);
        TODAY.setFilteredTaskList(observableList);
        NEXT.setFilteredTaskList(observableList);
        INBOX.setFilteredTaskList(observableList);
        COMPLETE.setFilteredTaskList(observableList);
        DUE.setFilteredTaskList(observableList);
    }



    /**
     * Categorized tasks based on buildIn Category
     * 
     * @param tasks not modifiable
     * @return Map contain buildInCategory to List of tasks
     */
    public static Map<BuildInCategory, List<ReadOnlyTask>> categorizedByBuildInCategory(
        ObservableList<ReadOnlyTask> tasks, BuildInCategory ... categories) {
        HashMap<BuildInCategory, List<ReadOnlyTask>> results = new HashMap<BuildInCategory, List<ReadOnlyTask>>();
        for(BuildInCategory c : categories) {
            List<ReadOnlyTask> filteredTasks = new ArrayList<ReadOnlyTask>(tasks.filtered(c.getPredicate()));
            if (filteredTasks.size() > 0) {
                // sort the list before put in
                filteredTasks.sort((t1, t2) -> {
                    if (t1.isFloatingTask() && t2.isFloatingTask()) {
                        return t1.getTitle().fullTitle.compareTo(t2.getTitle().fullTitle);
                    } else {
                        DateTime t1_represent = t1.hasStartTime() ? t1.getStartTime().value : new DateTime();
                        DateTime t2_represent = t2.hasStartTime() ? t2.getStartTime().value : new DateTime();
                        t1_represent = t1.hasEndTime() ? t1.getEndTime().value : t1_represent;
                        t2_represent = t2.hasEndTime() ? t2.getEndTime().value : t2_represent;
                        return t1_represent.isBefore(t2_represent) ? -1 : 1;
                    }
                });
                results.put(c, filteredTasks);
            }
        }
        return results;
    }
    
    public static ReadOnlyTask getTaskWhenCategorizedByBuildInCategory(int index, 
            ObservableList<ReadOnlyTask> tasks, 
            BuildInCategory ... categories) throws TaskNotFoundException {
        Map<BuildInCategory, List<ReadOnlyTask>> results = categorizedByBuildInCategory(tasks, categories);
        int i = 1;
        for(BuildInCategory c : categories) {
            if (results.get(c) == null) continue;
            for(ReadOnlyTask t : results.get(c)) {
                if (index == i) {
                    return t;
                }
                i++;
            }
        }
        throw new TaskNotFoundException();
    }
    
    private final ObservableList<Category> internalList = FXCollections.observableArrayList();

    public void addAllBuildInCategories() {
        internalList.addAll(ALL, TODAY, NEXT, INBOX, COMPLETE);
    }
       
    /**
     * Constructs empty CategoryList.
     */
    public BuildInCategoryList() {}
    

    
    public BuildInCategoryList(Collection<Category> stroedList) {
        BuildInCategory[] buildInCategories = {ALL, TODAY, NEXT, INBOX, COMPLETE};
        for(Category c : stroedList) {
            for(BuildInCategory bc : buildInCategories) {
                if (c.categoryName.equals(bc.categoryName)) {
                    internalList.add(bc);
                }
            }
        }
    }
    
    /**
     * Add a BuildInCategory form list
     * Restrict that can only BuildInCategory can be added
     * 
     * @param category
     */
    public void add(BuildInCategory category) {
        if (!contains(category)) {
            internalList.add(category);
        }
    }
    
    /**
     * Remove a BuildInCategory form list
     * 
     * @param category
     */
    public void remove(BuildInCategory category) {
        if (contains(category)) {
            internalList.remove(category);
        }
    }

    /**
     * Returns true if the list contains an equivalent Category as the given argument.
     */
    public boolean contains(BuildInCategory toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof BuildInCategoryList) {
            BuildInCategoryList other = (BuildInCategoryList) o;
            return this.internalList.equals(other.getInternalList());
        } else {
            return super.equals(o);
        }
    }

    public UnmodifiableObservableList<Category> getInternalList() {
        return new UnmodifiableObservableList<Category>(internalList);
    }

    public void replaceWith(BuildInCategoryList buildInCategories) {
        this.internalList.clear();
        this.internalList.addAll(buildInCategories.internalList);
    }
}