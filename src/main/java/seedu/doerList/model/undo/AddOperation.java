//@@author A0147978E
package seedu.doerList.model.undo;

import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Represents a add operation to the doreList
 */
public class AddOperation extends Operation {
    private Task toAdd;
    
    public AddOperation(Task toAdd) {
        this.toAdd = toAdd;
    }
    
    @Override
    public void execute() throws DuplicateTaskException {
        this.doerList.addTask(toAdd);
    }

    /**
     * The inverse of add operation is deletion.
     */
    @Override
    public Operation getInverseOperation() {
        return new DeleteOperation(toAdd);
    }

}
