//@@author A0147978E
package seedu.doerList.model.undo;

import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents a delete operation to the doreList
 */
public class DeleteOperation extends Operation {

    Task toDelete;
    
    public DeleteOperation(Task toDelete) {
        this.toDelete = toDelete;
    }
    
    @Override
    public void execute() throws TaskNotFoundException {
        this.doerList.removeTask(toDelete);
    }

    /**
     * The inverse of delete operation is addition
     */
    @Override
    public Operation getInverseOperation() {
        return new AddOperation(toDelete);
    }

}
