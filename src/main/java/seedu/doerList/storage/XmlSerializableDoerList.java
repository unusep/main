package seedu.doerList.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.ReadOnlyDoerList;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.model.task.UniqueTaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable DoerList that is serializable to XML format
 */
@XmlRootElement(name = "doerList")
public class XmlSerializableDoerList implements ReadOnlyDoerList {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<Category> categories;

    {
        tasks = new ArrayList<>();
        categories = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableDoerList() {}

    /**
     * Conversion
     */
    public XmlSerializableDoerList(ReadOnlyDoerList src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
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
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
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
