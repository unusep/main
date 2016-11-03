//@@author A0147978E
package seedu.doerList.commons.events.ui;

import seedu.doerList.commons.events.BaseEvent;

/**
 * Represents arrow key pressed in the keyboard
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
