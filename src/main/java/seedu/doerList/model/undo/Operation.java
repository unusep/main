//@@author A0147978E
package seedu.doerList.model.undo;

import seedu.doerList.model.DoerList;

/**
 * Represents a operation with hidden internal logic and the ability to be executed.
 */
public abstract class Operation {
    protected DoerList doerList;

    /**
     * Executes the operation against the doerList
     */
    public abstract void execute() throws Exception;

    /**
     * Get the inverse operation of current operation
     */
    public abstract Operation getInverseOperation();
    
    
    /**
     * Provides any needed dependencies to the operation.
     * Operations making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(DoerList doerList) {
        this.doerList = doerList;
    }
}
