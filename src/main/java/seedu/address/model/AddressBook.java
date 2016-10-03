package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.category.Category;
import seedu.address.model.category.UniqueCategoryList;
import seedu.address.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.UniqueEventList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEventList events;
    private final UniqueCategoryList categories;

    {
        events = new UniqueEventList();
        categories = new UniqueCategoryList();
    }

    public AddressBook() {}

    /**
     * Events and Categories are copied into this addressbook
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this(toBeCopied.getUniqueEventList(), toBeCopied.getUniqueCategoryList());
    }

    /**
     * Events and Categories are copied into this addressbook
     */
    public AddressBook(UniqueEventList events, UniqueCategoryList categories) {
        resetData(events.getInternalList(), categories.getInternalList());
    }

    public static ReadOnlyAddressBook getEmptyAddressBook() {
        return new AddressBook();
    }

//// list overwrite operations

    public ObservableList<Event> getEvents() {
        return events.getInternalList();
    }

    public void setEvents(List<Event> events) {
        this.events.getInternalList().setAll(events);
    }

    public void setCategories(Collection<Category> categories) {
        this.categories.getInternalList().setAll(categories);
    }

    public void resetData(Collection<? extends ReadOnlyEvent> newEvents, Collection<Category> newCategories) {
        setEvents(newEvents.stream().map(Event::new).collect(Collectors.toList()));
        setCategories(newCategories);
    }

    public void resetData(ReadOnlyAddressBook newData) {
        resetData(newData.getEventList(), newData.getCategoryList());
    }

//// event-level operations

    /**
     * Adds a event to the address book.
     * Also checks the new event's categories and updates {@link #categories} with any new categories found,
     * and updates the Category objects in the event to point to those in {@link #categories}.
     *
     * @throws UniqueEventList.DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(Event p) throws UniqueEventList.DuplicateEventException {
        syncCategoriesWithMasterList(p);
        events.add(p);
    }

    /**
     * Ensures that every category in this event:
     *  - exists in the master list {@link #categories}
     *  - points to a Category object in the master list
     */
    private void syncCategoriesWithMasterList(Event event) {
        final UniqueCategoryList eventCategories = event.getCategories();
        categories.mergeFrom(eventCategories);

        // Create map with values = category object references in the master list
        final Map<Category, Category> masterCategoryObjects = new HashMap<>();
        for (Category category : categories) {
            masterCategoryObjects.put(category, category);
        }

        // Rebuild the list of event categories using references from the master list
        final Set<Category> commonCategoryReferences = new HashSet<>();
        for (Category category : eventCategories) {
            commonCategoryReferences.add(masterCategoryObjects.get(category));
        }
        event.setCategories(new UniqueCategoryList(commonCategoryReferences));
    }

    public boolean removeEvent(ReadOnlyEvent key) throws UniqueEventList.EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new UniqueEventList.EventNotFoundException();
        }
    }

//// category-level operations

    public void addCategory(Category t) throws DuplicateCategoryException {
        categories.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return events.getInternalList().size() + " events, " + categories.getInternalList().size() +  " categories";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyEvent> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
    }

    @Override
    public List<Category> getCategoryList() {
        return Collections.unmodifiableList(categories.getInternalList());
    }

    @Override
    public UniqueEventList getUniqueEventList() {
        return this.events;
    }

    @Override
    public UniqueCategoryList getUniqueCategoryList() {
        return this.categories;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.events.equals(((AddressBook) other).events)
                && this.categories.equals(((AddressBook) other).categories));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(events, categories);
    }
}
