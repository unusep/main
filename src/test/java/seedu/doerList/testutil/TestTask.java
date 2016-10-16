package seedu.doerList.testutil;

import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Description description;
    private TodoTime startTime;
    private TodoTime endTime;
    private UniqueCategoryList categories;

    public TestTask() {
        categories = new UniqueCategoryList();
    }
    
    // copy constructor
    public TestTask(ReadOnlyTask source) {
        this.title = source.getTitle();
        this.description = source.getDescription();
        this.startTime = source.getStartTime();
        this.endTime = source.getEndTime();
        this.categories = source.getCategories();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartTime(TodoTime startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(TodoTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Description getDescription() {
        return description;
    }
    
    @Override
    public TodoTime getStartTime() {
        return this.startTime;
    }

    @Override
    public TodoTime getEndTime() {
        return this.endTime;
    }


    @Override
    public UniqueCategoryList getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");
        cmd.append("/t ").append(this.getTitle()).append(" ");
        
        if (this.hasDescription()) {
            cmd.append("/d ").append(this.getDescription()).append(" ");
        }
       
        if (!this.isFloatingTask()) {
            cmd.append("{");
            if (this.hasStartTime()) {
                cmd.append(this.getStartTime());
            }
            cmd.append("->");
            if (this.hasEndTime()) {
                cmd.append(this.getEndTime());
            }
            cmd.append("}");
        }
        
        UniqueCategoryList categories = this.getCategories();
        if (!categories.getInternalList().isEmpty()) {
            cmd.append(" /c ");
            for(Category c: categories){
                cmd.append(c.categoryName);
            }
        }
        
        return cmd.toString();
    }

}
