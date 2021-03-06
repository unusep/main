//@@author A0147978E
package seedu.doerList.commons.events.ui;

import seedu.doerList.commons.events.BaseEvent;
import seedu.doerList.model.category.Category;

/**
 * Indicates a event that selection of category has changed
 */
public class CategorySelectionChangedEvent extends BaseEvent {
    private final Category newSelection;

    public CategorySelectionChangedEvent(Category newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Category getNewSelection() {
        return newSelection;
    }
}
