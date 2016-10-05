package seedu.doerList.commons.events.model;

import seedu.doerList.commons.events.BaseEvent;
import seedu.doerList.model.ReadOnlyDoerList;

/** Indicates the DoerList in the model has changed*/
public class DoerListChangedEvent extends BaseEvent {

    public final ReadOnlyDoerList data;

    public DoerListChangedEvent(ReadOnlyDoerList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of categories " + data.getCategoryList().size();
    }
}
