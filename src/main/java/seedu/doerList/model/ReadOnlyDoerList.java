package seedu.doerList.model;


import java.util.List;

import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an doerList
 */
public interface ReadOnlyDoerList {

    UniqueCategoryList getUniqueCategoryList();

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of categories list
     */
    List<Category> getCategoryList();

}
