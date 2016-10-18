package seedu.doerList.commons.events.ui;

import seedu.doerList.commons.events.BaseEvent;
import seedu.doerList.model.category.Category;

/**
 * Indicates a request to jump to the category
 */
public class JumpToCategoryEvent extends BaseEvent {

    public final Category target;

    public JumpToCategoryEvent(Category target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
