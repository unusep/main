package seedu.doerList.storage;

//import org.joda.time.DateTime;
import java.time.LocalDateTime;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.task.TodoTime;

/**
 * JAXB-friendly adapted version of the Todo Time.
 */
public class XmlAdaptedTodoTime {

    private LocalDateTime value;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTodoTime() {}

    /**
     * Converts a given TodoTime into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTodoTime(TodoTime source) {
        value = source.getTime();
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's TimeInterval object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted TimeInterval
     */
    public TodoTime toModelType() throws IllegalValueException {
        return new TodoTime(value);
    }

}
