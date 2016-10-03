package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.event.ReadOnlyEvent;

public class EventCard extends UiPart{

    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label timeInterval;
    @FXML
    private Label categories;

    private ReadOnlyEvent event;
    private int displayedIndex;

    public EventCard(){

    }

    public static EventCard load(ReadOnlyEvent event, int displayedIndex){
        EventCard card = new EventCard();
        card.event = event;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        title.setText(event.getTitle().fullTitle);
        id.setText(displayedIndex + ". ");
        description.setText(event.getDescription().value);
        timeInterval.setText(event.getTimeInterval().toString());
        categories.setText(event.categoriesString());
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
