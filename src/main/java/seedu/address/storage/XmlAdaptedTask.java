package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;
import seedu.address.model.category.UniqueCategoryList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private DateTime startTime;
    @XmlElement(required = true)
    private DateTime endTime;


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
        description = source.getDescription().value;
        startTime = source.getTimeInterval().startTime;
        endTime = source.getTimeInterval().endTime;
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
        final Description description = new Description(this.description);
        final TimeInterval timeInterval = new TimeInterval(this.startTime.toString(), this.endTime.toString());
        final UniqueCategoryList categories = new UniqueCategoryList(taskCategories);
        return new Task(title, description, timeInterval, categories);
    }
}
