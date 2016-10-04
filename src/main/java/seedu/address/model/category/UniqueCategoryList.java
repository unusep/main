package seedu.address.model.category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of categories that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
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

    private final ObservableList<Category> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty CategoryList.
     */
    public UniqueCategoryList() {}

    /**
     * Varargs/array constructor, enforces no nulls or duplicates.
     */
    public UniqueCategoryList(Category... categories) throws DuplicateCategoryException {
        assert !CollectionUtil.isAnyNull((Object[]) categories);
        final List<Category> initialCategories = Arrays.asList(categories);
        if (!CollectionUtil.elementsAreUnique(initialCategories)) {
            throw new DuplicateCategoryException();
        }
        internalList.addAll(initialCategories);
    }

    /**
     * java collections constructor, enforces no null or duplicate elements.
     */
    public UniqueCategoryList(Collection<Category> categories) throws DuplicateCategoryException {
        CollectionUtil.assertNoNullElements(categories);
        if (!CollectionUtil.elementsAreUnique(categories)) {
            throw new DuplicateCategoryException();
        }
        internalList.addAll(categories);
    }

    /**
     * java set constructor, enforces no nulls.
     */
    public UniqueCategoryList(Set<Category> categories) {
        CollectionUtil.assertNoNullElements(categories);
        internalList.addAll(categories);
    }

    /**
     * Copy constructor, insulates from changes in source.
     */
    public UniqueCategoryList(UniqueCategoryList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * All categories in this list as a Set. This set is mutable and change-insulated against the internal list.
     */
    public Set<Category> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Categories in this list with those in the argument category list.
     */
    public void setCategories(UniqueCategoryList replacement) {
        this.internalList.clear();
        this.internalList.addAll(replacement.internalList);
    }

    /**
     * Adds every category from the argument list that does not yet exist in this list.
     */
    public void mergeFrom(UniqueCategoryList categories) {
        final Set<Category> alreadyInside = this.toSet();
        for (Category category : categories) {
            if (!alreadyInside.contains(category)) {
                internalList.add(category);
            }
        }
    }

    /**
     * Returns true if the list contains an equivalent Category as the given argument.
     */
    public boolean contains(Category toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Category to the list.
     *
     * @throws DuplicateCategoryException if the Category to add is a duplicate of an existing Category in the list.
     */
    public void add(Category toAdd) throws DuplicateCategoryException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateCategoryException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Category> getInternalList() {
        return internalList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            // short cut
            return true;
        }
        if (other instanceof UniqueCategoryList) {
            // doing set except
            UniqueCategoryList otherObj = (UniqueCategoryList) other;
            ArrayList<Category> compare = new ArrayList<Category>(this.internalList);
            compare.removeAll(otherObj.internalList);
            return compare.isEmpty();
        }
            
        return super.equals(other);
        
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}