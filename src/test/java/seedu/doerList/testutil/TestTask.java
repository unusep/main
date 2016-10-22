package seedu.doerList.testutil;

import seedu.doerList.logic.commands.AddCommand;
import seedu.doerList.logic.commands.EditCommand;
import seedu.doerList.model.category.BuildInCategory;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.Description;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.Title;
import seedu.doerList.model.task.TodoTime;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Description description;
    private TodoTime startTime;
    private TodoTime endTime;
    private UniqueCategoryList categories = new UniqueCategoryList();
    private BuildInCategoryList buildInCategories = new BuildInCategoryList();

    public TestTask() {
        
    }
    
    // copy constructor
    public TestTask(ReadOnlyTask source) {
        this.title = source.getTitle();
        this.description = source.getDescription();
        this.startTime = source.getStartTime();
        this.endTime = source.getEndTime();
        this.categories = source.getCategories();
        this.getBuildInCategories().replaceWith(source.getBuildInCategories());
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
    
    public void setCategories(UniqueCategoryList categories) {
        this.categories = categories;
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
    public BuildInCategoryList getBuildInCategories() {
        return buildInCategories;
    }
    
    //@@author A0147978E
    @Override
    public void addBuildInCategory(BuildInCategory category) {
        buildInCategories.add(category);
    }

    //@@author A0147978E
    @Override
    public void removeBuildInCategory(BuildInCategory category) {
        buildInCategories.remove(category);      
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0147978E
    public String getAddCommand() {
        StringBuffer cmd = new StringBuffer();

        cmd.append(AddCommand.COMMAND_WORD);
        cmd.append(" /t ").append(this.getTitle()).append(" ");

        if (this.hasDescription()) {
            cmd.append("/d ").append(this.getDescription()).append(" ");
        }

        if (!this.isFloatingTask()) {
            if (this.hasStartTime()) {
                cmd.append("/s ");
                cmd.append(this.getStartTime()).append(" ");
            }
            if (this.hasEndTime()) {
                cmd.append("/e ");
                cmd.append(this.getEndTime()).append(" ");
            }
        }

        UniqueCategoryList categories = this.getCategories();
        if (!categories.getInternalList().isEmpty()) {
            for(Category c: categories){
                cmd.append("/c " + c.categoryName + " ");
            }
        }

        return cmd.toString();
    }
    
    //@@author A0147978E
    public String getEditCommand(int index) {
        return this.getAddCommand().replace(AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD + " " + index + " ");
    }


}
