package seedu.doerList.commons.events.storage;

import seedu.doerList.commons.events.BaseEvent;
import seedu.doerList.model.ReadOnlyDoerList;

public class StoragePathChangedEvent extends BaseEvent {
    private String filePath;
    private ReadOnlyDoerList data;
    
    public StoragePathChangedEvent(String filePath, ReadOnlyDoerList data) {
        this.filePath = filePath;
        this.data = data;
    }
    
    public String getFilePath() {
        return this.filePath;
    }
    
    public ReadOnlyDoerList getData() {
        return this.data;
    }
     
    // For logging purposes
    @Override
    public String toString() {
        return "Save Location: " + filePath;
    }
}
