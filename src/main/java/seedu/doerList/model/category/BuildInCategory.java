//@@author A0147978E
package seedu.doerList.model.category;

import java.util.function.Predicate;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.task.ReadOnlyTask;

public class BuildInCategory extends Category {
    
    public static String CATEGORY_FILTERED_BADGE = " (filtered)";
    
    Predicate<ReadOnlyTask> defaultPredicate;
    String defaultCategoryName;
    
    Predicate<ReadOnlyTask> currentPredicate;
    
    public BuildInCategory(String name, Predicate<ReadOnlyTask> thePredicate) throws IllegalValueException {
        super(name);
        defaultPredicate = thePredicate;
        currentPredicate = thePredicate;
        defaultCategoryName = name;
    }
    
    /**
     * Return predicate to filter tasks.
     * 
     * @return predicate(lambda) expression to help filter tasks 
     */
    public Predicate<ReadOnlyTask> getPredicate() {
        if (currentPredicate == null) {
            return super.getPredicate();
        } else {
            return currentPredicate;
        }
    }
    
    /**
     * Reset the buildInCategory to default predicate.
     */
    public void setToDefaultPredicate() {
        this.currentPredicate = this.defaultPredicate;
        this.categoryName = this.defaultCategoryName;
    }
    
    /**
     * Update the predicate of the buildInCategory with given {@code thePredicate}.
     * 
     * @param thePredicate
     */
    public void updatePredicate(Predicate<ReadOnlyTask> thePredicate) {
        this.currentPredicate = thePredicate;
        if (!this.categoryName.contains(CATEGORY_FILTERED_BADGE)) {
            this.categoryName = this.categoryName + CATEGORY_FILTERED_BADGE;
        }
    }
    
    @Override
    public boolean isBuildIn() {
        return true;
    } 
    
    
}
