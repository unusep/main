package seedu.address.storage;

import seedu.address.model.task.TimeInterval;
import seedu.address.commons.exceptions.IllegalValueException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

/**
 * JAXB-friendly adapted version of the TimeInterval.
 */
public class XmlAdaptedTimeInterval {

    @XmlJavaTypeAdapter(JodaDateTimeAdapter.class)
    private DateTime startTime;
    @XmlJavaTypeAdapter(JodaDateTimeAdapter.class)
    private DateTime endTime;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTimeInterval() {}

    /**
     * Converts a given TimeInterval into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTimeInterval(TimeInterval source) {
        startTime = source.startTime;
        endTime = source.endTime;
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's TimeInterval object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted TimeInterval
     */
    public TimeInterval toModelType() throws IllegalValueException {
        return new TimeInterval(startTime, endTime);
    }

}
