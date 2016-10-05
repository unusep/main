package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import seedu.address.model.task.TimeInterval;

public class JodaDateTimeAdapter extends XmlAdapter<String, DateTime> {

    @Override
    public DateTime unmarshal(String v) throws Exception {
        return DateTime.parse(v, DateTimeFormat.forPattern(TimeInterval.TIME_STANDARD_FORMAT));
    }

    @Override
    public String marshal(DateTime v) throws Exception {
        return v.toString(DateTimeFormat.forPattern(TimeInterval.TIME_STANDARD_FORMAT));
    }

}
