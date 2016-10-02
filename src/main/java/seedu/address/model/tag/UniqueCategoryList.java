package seedu.address.model.tag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of categories that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Category#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueCategoryList implements Iterable<Category> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateCategoryException extends DuplicateDataException {
        protected DuplicateCategoryException() {
            super("Operation would result in duplicate categories");
        }
    }

    /**
     * Signals that an operation targeting a specified category in the list would fail because
     * there is no such matching category in the list.
     */
    public static class CategoryNotFoundException extends Exception {}

    private final ObservableList<Category> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty CategoryList.
     */
    public UniqueCategoryList() {}

    /**
     * Returns true if the list contains an equivalent category as the given argument.
     */
    public boolean contains(Category toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a category to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Category toAdd) throws DuplicateCategoryException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateCategoryException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes category from the list.
     *
     * @throws CategoryNotFoundException if no such category could be found in the list.
     */
    public boolean remove(Category toRemove) throws CategoryNotFoundException {
        assert toRemove != null;
        final boolean categoryFoundAndDeleted = internalList.remove(toRemove);
        if (!categoryFoundAndDeleted) {
            throw new CategoryNotFoundException();
        }
        return categoryFoundAndDeleted;
    }

    public ObservableList<Category> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCategoryList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueCategoryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
