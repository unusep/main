package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;
import seedu.address.model.category.UniqueCategoryList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
    private XmlAdaptedTimeInterval timeInterval;


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
        if (source.hasTimeInterval()) {
            timeInterval = new XmlAdaptedTimeInterval(source.getTimeInterval());
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
        TimeInterval timeInterval = null;
        if (this.description != null) {
            description = new Description(this.description);
        }
        if (this.timeInterval != null) {
            timeInterval = this.timeInterval.toModelType();
        }
        final UniqueCategoryList categories = new UniqueCategoryList(taskCategories);
        return new Task(title, description, timeInterval, categories);
    }
}
