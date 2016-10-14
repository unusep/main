package seedu.doerList.model.category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.doerList.commons.exceptions.DuplicateDataException;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.commons.util.CollectionUtil;

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

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateCategoryException extends DuplicateDataException {
        protected DuplicateCategoryException() {
            super("Operation would result in duplicate categories");
        }
    }
      
    public static enum ListOfCategory {
        ALL,
        TODAY,
        NEXT7DAYS,
        INBOX,
        COMPLETE
    }

    private final ObservableList<Category> buildInList = FXCollections.observableArrayList();

    public void addBuildInCategories() {
        try {
            // add `All` category
            buildInList.add(new BuildInCategory("All", (task) -> {return true;}));
            // add `Tomorrow` category
            buildInList.add(new BuildInCategory("Today", (task) -> {
                DateTime todayBegin = new DateTime().withTimeAtStartOfDay();
                DateTime todayEnd = todayBegin.plusDays(1);        
                return (!task.hasStartTime() || task.getStartTime().value.isAfter(todayBegin)) &&
                        task.hasEndTime() && task.getEndTime().value.isBefore(todayEnd);
            }));
            // add `Next 7 Days` category
            buildInList.add(new BuildInCategory("Next 7 Days", (task) -> {
                DateTime todayEnd = new DateTime().withTimeAtStartOfDay().plusDays(1);
                DateTime sevenDaysEnd = todayEnd.plusDays(7);        
                return (!task.hasStartTime() || task.getStartTime().value.isAfter(todayEnd)) &&
                        task.hasEndTime() && task.getEndTime().value.isBefore(sevenDaysEnd);
            }));
            // add `Inbox` category
            buildInList.add(new BuildInCategory("Inbox", (task) -> {
                        return !task.hasEndTime() && !task.hasEndTime();
                    }));
            // add `Complete` category
            buildInList.add(new BuildInCategory("Complete", null));
        } catch (IllegalValueException e) {
            // impossible
            e.printStackTrace();
        }
    }
    
    
    /**
     * Constructs empty CategoryList.
     */
    public BuildInCategoryList() {}

    public Category getCategory(ListOfCategory index) {
        return buildInList.get(index.ordinal());
    }

    /**
     * Returns true if the list contains an equivalent Category as the given argument.
     */
    public boolean contains(Category toCheck) {
        assert toCheck != null;
        return buildInList.contains(toCheck);
    }

    @Override
    public Iterator<Category> iterator() {
        return buildInList.iterator();
    }

    public ObservableList<Category> getInternalList() {
        return buildInList;
    }
}