//@@author A0147978E
package seedu.doerList.model.undo;

import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents a mark operation to the doreList
 */
public class MarkOperation extends Operation {
    private ReadOnlyTask toMark;
    
    public MarkOperation(ReadOnlyTask toMark) {
        this.toMark = toMark;
    }
    
    @Override
    public void execute() throws DuplicateTaskException, TaskNotFoundException {
        this.doerList.markTask(toMark);
    }

    /**
     * The inverse of mark operation is unmark.
     */
    @Override
    public Operation getInverseOperation() {
        return new UnmarkOperation(toMark);
    }

}
