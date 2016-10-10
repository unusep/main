package seedu.doerList.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = false)
    private String description;
    @XmlElement(required = false)
    private XmlAdaptedTodoTime startTime;
    @XmlElement(required = false)
    private XmlAdaptedTodoTime endTime;


    @XmlElement
    private List<XmlAdaptedCategory> categorized = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        title = source.getTitle().fullTitle;
        if (source.hasDescription()) {
            description = source.getDescription().value;
        }
        if (source.hasStartTime()) {
            startTime = new XmlAdaptedTodoTime(source.getStartTime());
        }
        if (source.hasEndTime()) {
            endTime = new XmlAdaptedTodoTime(source.getEndTime());
        }
        categorized = new ArrayList<>();
        for (Category category : source.getCategories()) {
            categorized.add(new XmlAdaptedCategory(category));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Category> taskCategories = new ArrayList<>();
        for (XmlAdaptedCategory category : categorized) {
            taskCategories.add(category.toModelType());
        }
        final Title title = new Title(this.title);
        Description description = null;
        TodoTime startTime = null;
        TodoTime endTime = null;
        if (this.description != null) {
            description = new Description(this.description);
        }
        if (this.startTime != null) {
            startTime = this.startTime.toModelType();
        }
        if (this.endTime != null) {
            endTime = this.endTime.toModelType();
        }
        final UniqueCategoryList categories = new UniqueCategoryList(taskCategories);
        return new Task(title, description, startTime, endTime, categories);
    }
}
