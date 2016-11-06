//@@author A0147978E
package seedu.doerList.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.task.Recurring;

/**
 * JAXB-friendly adapted version of the Recurring.
 */
public class XmlAdaptedRecurring {

    @XmlElement(required = true)
    private long day;
    @XmlElement(required = true)
    private long month;
    @XmlElement(required = true)
    private long year;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedRecurring() {}

    /**
     * Converts a given Recurring into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedRecurring(Recurring source) {
        day = source.getDays();
        month = source.getMonths();
        year = source.getYears();
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Recurring object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted TimeInterval
     */
    public Recurring toModelType() throws IllegalValueException {
        return new Recurring(day, month, year);
    }

}
