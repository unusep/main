package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * Represents a selection change in the Event List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyEvent newSelection;

    public EventPanelSelectionChangedEvent(ReadOnlyEvent newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyEvent getNewSelection() {
        return newSelection;
    }
}
