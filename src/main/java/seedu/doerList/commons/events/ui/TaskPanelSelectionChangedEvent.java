package seedu.doerList.commons.events.ui;

import com.google.common.eventbus.Subscribe;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.BaseEvent;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.ui.TaskCard;

/**
 * Represents a selection change in the Event List Panel
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
