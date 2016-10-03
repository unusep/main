package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.Category;
import seedu.address.model.category.UniqueCategoryList;
import seedu.address.model.event.*;

import javax.xml.bind.annotation.XmlElement;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

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
    public XmlAdaptedEvent() {}


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
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
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final List<Category> eventCategories = new ArrayList<>();
        for (XmlAdaptedCategory category : categorized) {
            eventCategories.add(category.toModelType());
        }
        final Title title = new Title(this.title);
        final Description description = new Description(this.description);
        final TimeInterval timeInterval = new TimeInterval(this.startTime.toString(), this.endTime.toString());
        final UniqueCategoryList categories = new UniqueCategoryList(eventCategories);
        return new Event(title, description, timeInterval, categories);
    }
}
