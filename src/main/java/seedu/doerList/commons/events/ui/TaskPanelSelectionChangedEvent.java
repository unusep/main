package seedu.doerList.commons.events.ui;

import seedu.doerList.commons.events.BaseEvent;
import seedu.doerList.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Event List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
