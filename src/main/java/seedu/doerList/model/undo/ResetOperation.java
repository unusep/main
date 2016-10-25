//@@author A0147978E
package seedu.doerList.model.undo;

import seedu.doerList.model.DoerList;
import seedu.doerList.model.ReadOnlyDoerList;

/**
 * Represents a reset operation to the doreList
 */
public class ResetOperation extends Operation {

    private DoerList replaceWith;
    private DoerList toReplace;
    
    public ResetOperation(ReadOnlyDoerList toReplace, DoerList replaceWith) {
        this.toReplace = new DoerList(toReplace);
        this.replaceWith = new DoerList(replaceWith);
    }
    
    @Override
    public void execute() throws Exception {
        this.doerList.resetData(replaceWith);
    }

    /**
     * The inverse of reset operation is reset back
     */
    @Override
    public Operation getInverseOperation() {
        return new ResetOperation(replaceWith, toReplace);
    }

}
