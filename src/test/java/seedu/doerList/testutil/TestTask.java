package seedu.doerList.testutil;

import java.time.format.DateTimeFormatter;

import org.joda.time.format.DateTimeFormat;

import seedu.doerList.model.category.UniqueCategoryList;
import seedu.doerList.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Description description;
    private TimeInterval interval;
    private UniqueCategoryList categories;

    public TestTask() {
        categories = new UniqueCategoryList();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setTimeInterval(TimeInterval interval) {
        this.interval = interval;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public TimeInterval getTimeInterval() {
        return interval;
    }

    @Override
    public UniqueCategoryList getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add -t " + this.getTitle().fullTitle + " ");
        if (this.hasDescription()) {
            sb.append("-d " + this.getDescription().value + " ");
        }
        if (this.hasTimeInterval()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
            sb.append("{" + this.getTimeInterval().getStartTime().toString(DateTimeFormat.forPattern(TimeInterval.TIME_STANDARD_FORMAT))
                    + "->" + this.getTimeInterval().getEndTime().toString(DateTimeFormat.forPattern(TimeInterval.TIME_STANDARD_FORMAT))
                    + "} ");
        }
        if (!this.getCategories().getInternalList().isEmpty()) {
            sb.append("-c ");
            this.getCategories().getInternalList().stream().forEach(s -> sb.append("t/" + s.categoryName + " "));
        }
        return sb.toString();
    }
}