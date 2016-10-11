package seedu.doerList.commons.events.ui;

import com.google.common.eventbus.Subscribe;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.BaseEvent;
import seedu.doerList.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Event List Panel
 */
public class TaskPanelArrowKeyPressEvent extends BaseEvent {

    private Direction direction;
    
    public static enum Direction {
        UP,
        DOWN
    };

    public TaskPanelArrowKeyPressEvent(Direction direction){
        this.direction = direction;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Direction getDirection() {
        return direction;
    }
    
}
