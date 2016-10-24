package seedu.doerList.commons.events.storage;

import seedu.doerList.commons.events.BaseEvent;

// Indicates the data path has changed
public class DataPathChangedEvent extends BaseEvent {
    
    private String saveLocation;
    private String fileName;
    
    public DataPathChangedEvent(String saveLocation, String fileName) {
        this.saveLocation = saveLocation;
        this.fileName = fileName;
    }
    
    public String getSaveLocation() {
        return this.saveLocation;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    // For logging purposes
    @Override
    public String toString() {
        return "Save Location: " + saveLocation + "\n File Name: " + fileName;
    }
}
