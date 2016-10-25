//@@author A0147978E
package seedu.doerList.model.undo;

import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.doerList.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents a delete operation to the doreList
 */
public class EditOperation extends Operation {

    private Task toReplace;
    private Task replaceWith;
    
    public EditOperation(Task toReplace, Task replaceWith) {
        this.toReplace = toReplace;
        this.replaceWith = replaceWith;
    }
    
    @Override
    public void execute() throws DuplicateTaskException, TaskNotFoundException {
        this.doerList.replaceTask(toReplace, replaceWith);
    }

    /**
     * The inverse of edition is to edit back
     */
    @Override
    public Operation getInverseOperation() {
        return new EditOperation(replaceWith, toReplace);
    }
    

}
