package seedu.doerList.model.category;

import java.util.function.Predicate;

import org.joda.time.DateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.doerList.commons.core.UnmodifiableObservableList;
import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.task.ReadOnlyTask;

public class BuildInCategory extends Category {
    
    Predicate<ReadOnlyTask> predicate;
    
    public BuildInCategory(String name, Predicate<ReadOnlyTask> thePredicate) throws IllegalValueException {
        super(name);
        predicate = thePredicate;
    }
    
    /**
     * Return predicate to help filter tasks
     * @return predicate(lambda) expression to help filter tasks 
     */
    public Predicate<ReadOnlyTask> getPredicate() {
        if (predicate == null) {
            return super.getPredicate();
        } else {
            return predicate;
        }
    }
    
    
    
    @Override
    public boolean isBuildIn() {
        return true;
    } 
    
    
}
