package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.UniqueEventList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given event. */
    void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;

    /** Adds the given event */
    void addEvent(Event event) throws UniqueEventList.DuplicateEventException;

    /** Returns the filtered event list as an {@code UnmodifiableObservableList<ReadOnlyEvent>} */
    UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList();

    /** Updates the filter of the filtered event list to show all events */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered event list to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);

}
