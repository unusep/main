//@@author A0147978E
package seedu.doerList.model.undo;

import java.util.Stack;

import seedu.doerList.model.DoerList;
import seedu.doerList.model.ReadOnlyDoerList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Task;

/**
 * Manage the changes to doerList in model component.
 * Keep reverse operation so that can use redo and undo command
 * 
 */
public class UndoManager {
    private Stack<Operation> redoStack = new Stack<Operation>();
    private Stack<Operation> undoStack = new Stack<Operation>();
    
    public static class StackEmptyException extends Exception {
        public StackEmptyException(String s) {
            super(s);
        }
    }
    
    public static class OperationFailException extends Exception {
        public OperationFailException(String s) {
            super(s);
        }
    }
    
    /** ==== Record edition/addition/deletion/reset data to the model(doerList) and generate the reverse operation ====*/
    
    public void recordEdit(ReadOnlyTask toEdit, Task afterEdit) {
        undoStack.push(new EditOperation(afterEdit, new Task(toEdit)));
    }
    
    public void recordAdd(Task toAdd) {
        undoStack.push(new DeleteOperation(toAdd));
    }
    
    public void recordDelete(ReadOnlyTask toDelete) {
        undoStack.push(new AddOperation(new Task(toDelete)));
    }
    
    public void recordReset(DoerList toReplace, ReadOnlyDoerList replaceWith) {
        undoStack.push(new ResetOperation(replaceWith, toReplace));
    }
    
    /**
     * Pull the most recent redo operation from the redo stack
     * 
     * @return Operation
     * @throws StackEmptyException
     */
    public Operation pullRedoStack() throws StackEmptyException {
        if (redoStack.isEmpty()) {
            throw new StackEmptyException("");
        }
        Operation op = redoStack.pop();
        undoStack.push(op.getInverseOperation()); // as redo operation become undo operation
        return op;
    }
    
    /**
     * Pull the most recent undo operation in the undo stack
     * 
     * @return Operation
     * @throws StackEmptyException
     */
    public Operation pullUndoStack() throws StackEmptyException {
        if (undoStack.isEmpty()) {
            throw new StackEmptyException("");
        }
        Operation op = undoStack.pop();
        redoStack.push(op.getInverseOperation()); // as undo operation become redo operation
        return op;
    }
}
