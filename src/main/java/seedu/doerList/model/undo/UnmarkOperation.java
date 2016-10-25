//@@author A0147978E
package seedu.doerList.model.undo;

import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents a unmark operation to the doreList
 */
public class UnmarkOperation extends Operation {
    private ReadOnlyTask toUnmark;
    
    public UnmarkOperation(ReadOnlyTask toUnmark) {
        this.toUnmark = toUnmark;
    }
    
    @Override
    public void execute() throws DuplicateTaskException, TaskNotFoundException {
        this.doerList.unmarkTask(toUnmark);
    }

    /**
     * The inverse of unmark operation is mark.
     */
    @Override
    public Operation getInverseOperation() {
        return new MarkOperation(toUnmark);
    }

}
