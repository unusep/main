//@@author A0147978E
package seedu.doerList.commons.events.ui;

import seedu.doerList.commons.events.BaseEvent;
import seedu.doerList.ui.TaskCard;

/**
 * Represents selection changed in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {

    private final TaskCard selectedCard;

    public TaskPanelSelectionChangedEvent(TaskCard selectedCard){
        this.selectedCard = selectedCard;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskCard getNewSelectedCard() {
        return selectedCard;
    }
    
}
