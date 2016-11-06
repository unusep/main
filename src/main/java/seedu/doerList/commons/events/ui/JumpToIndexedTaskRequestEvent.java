//@@author A0147978E
package seedu.doerList.commons.events.ui;

import seedu.doerList.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the a task indicated by {@code targetIndex}
 */
public class JumpToIndexedTaskRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToIndexedTaskRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
