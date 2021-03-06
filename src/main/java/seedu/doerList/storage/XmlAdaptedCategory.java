package seedu.doerList.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.category.Category;

/**
 * JAXB-friendly adapted version of the Category.
 */
public class XmlAdaptedCategory {

    @XmlValue
    public String categoryName;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedCategory() {}

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedCategory(Category source) {
        categoryName = source.categoryName;
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Category toModelType() throws IllegalValueException {
        return new Category(categoryName);
    }

}
