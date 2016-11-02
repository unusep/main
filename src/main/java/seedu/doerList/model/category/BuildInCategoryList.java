//@@author A0147978E
package seedu.doerList.model.category;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.util.TimeUtil;
import seedu.doerList.model.task.Task;

/**
 * A list of buildinCategories.
 * 
 * @see BuildInCategory#equals(Object)
 */
public class BuildInCategoryList implements Iterable<Category> {  
    public static final BuildInCategory ALL;
    public static final BuildInCategory TODAY;
    public static final BuildInCategory NEXT;
    public static final BuildInCategory INBOX;
    public static final BuildInCategory COMPLETE;
    public static final BuildInCategory DUE;
       
    // predefined category
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
                LocalDateTime todayBegin = TimeUtil.getStartOfDay(LocalDateTime.now());
                LocalDateTime todayEnd = TimeUtil.getEndOfDay(LocalDateTime.now());    
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
                LocalDateTime todayEnd = TimeUtil.getEndOfDay(LocalDateTime.now());  
                if (task.hasStartTime()) {
                    return task.getStartTime().value.isAfter(todayEnd);
                } else if (task.hasEndTime()) {
                    return task.getEndTime().value.isAfter(todayEnd);
                } else {
                    return false;
                }
            });         
            DUE = new BuildInCategory("Overdue", (task) -> {
                LocalDateTime todayBegin = TimeUtil.getStartOfDay(LocalDateTime.now());    
                return !task.getBuildInCategories().contains(BuildInCategoryList.COMPLETE) && 
                        
                        task.hasEndTime() && task.getEndTime().value.isBefore(todayBegin);
            });
        } catch (Exception e) {
            e.printStackTrace();
            // impossible
            throw new RuntimeException("Could not init class.", e);
        }
    }
    
    /**
     * Reset all predefined buildInCategories' predicates.
     */
    public static void resetBuildInCategoryPredicate() {
        ALL.setToDefaultPredicate();
        TODAY.setToDefaultPredicate();
        NEXT.setToDefaultPredicate();
        INBOX.setToDefaultPredicate();
        COMPLETE.setToDefaultPredicate();
        DUE.setToDefaultPredicate();
    }
    
    /**
     * Set the tasks source to the buildInCategories so that it can filter task.
     * 
     * @param observableList
     */
    public static void setTasksSource(ObservableList<Task> observableList) {
        ALL.setFilteredTaskList(observableList);
        TODAY.setFilteredTaskList(observableList);
        NEXT.setFilteredTaskList(observableList);
        INBOX.setFilteredTaskList(observableList);
        COMPLETE.setFilteredTaskList(observableList);
        DUE.setFilteredTaskList(observableList);
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
     * Replace the data in current buildInCategories with the data in another buildInCategories.
     * 
     * @param buildInCategories
     */
    public void replaceWith(BuildInCategoryList buildInCategories) {
        this.internalList.clear();
        this.internalList.addAll(buildInCategories.internalList);
    }
    
    /**
     * Add a BuildInCategory to the list.
     * Restriction: only BuildInCategory can be added.
     * 
     * @param category
     */
    public void add(BuildInCategory category) {
        if (!contains(category)) {
            internalList.add(category);
        }
    }
    
    /**
     * Remove a BuildInCategory form list.
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

    /**
     * Returns {@code UnmodifiableObservableList} so that no one can modify.
     * 
     * @return
     */
    public UnmodifiableObservableList<Category> getInternalList() {
        return new UnmodifiableObservableList<Category>(internalList);
    }
}