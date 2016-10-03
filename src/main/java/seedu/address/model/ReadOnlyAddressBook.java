package seedu.address.model;


import seedu.address.model.category.Category;
import seedu.address.model.category.UniqueCategoryList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.UniqueEventList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    UniqueCategoryList getUniqueCategoryList();

    UniqueEventList getUniqueEventList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyEvent> getEventList();

    /**
     * Returns an unmodifiable view of categories list
     */
    List<Category> getCategoryList();

}
