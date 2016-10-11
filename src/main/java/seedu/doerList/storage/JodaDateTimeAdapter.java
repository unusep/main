package seedu.doerList.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import seedu.doerList.model.task.TodoTime;

public class JodaDateTimeAdapter extends XmlAdapter<String, DateTime> {

    @Override
    public DateTime unmarshal(String v) throws Exception {
        return DateTime.parse(v, DateTimeFormat.forPattern(TodoTime.TIME_STANDARD_FORMAT));
    }

    @Override
    public String marshal(DateTime v) throws Exception {
        return v.toString(DateTimeFormat.forPattern(TodoTime.TIME_STANDARD_FORMAT));
    }

}
