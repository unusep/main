package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.category.Category;
import seedu.address.model.category.UniqueCategoryList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.UniqueEventList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook implements ReadOnlyAddressBook {

    @XmlElement
    private List<XmlAdaptedEvent> events;
    @XmlElement
    private List<Category> categories;

    {
        events = new ArrayList<>();
        categories = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableAddressBook() {}

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
        categories = src.getCategoryList();
    }

    @Override
    public UniqueCategoryList getUniqueCategoryList() {
        try {
            return new UniqueCategoryList(categories);
        } catch (UniqueCategoryList.DuplicateCategoryException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniqueEventList getUniqueEventList() {
        UniqueEventList lists = new UniqueEventList();
        for (XmlAdaptedEvent p : events) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyEvent> getEventList() {
        return events.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Category> getCategoryList() {
        return Collections.unmodifiableList(categories);
    }

}
