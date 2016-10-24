//@@author A0147978E
package seedu.doerList.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import seedu.doerList.model.task.TodoTime;

public class JavaTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v, DateTimeFormatter.ofPattern(TodoTime.TIME_STANDARD_FORMAT));
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.format(DateTimeFormatter.ofPattern(TodoTime.TIME_STANDARD_FORMAT)).toString();
    }

}
