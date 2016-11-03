//@@author A0147978E
package seedu.doerList.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.doerList.MainApp;
import seedu.doerList.model.task.Recurring;

/** Card represents a specific task recurring bar */
public class TaskCardRecurringBar extends UiPart {
    public static final String LOOP_ICON_NAME = "/images/loop_icon.png";
    public static final String INTERVAL_FIELD_ID = "recurringInterval";
    private static final String FXML = "TaskCardRecurringBar.fxml";
    
    private HBox rootPanel;
    private AnchorPane placeHolderPane;
    
    @FXML
    private Label recurringInterval;
    @FXML
    private ImageView recurringIcon;

    private Recurring recurring;

    public TaskCardRecurringBar(){

    }

    public static TaskCardRecurringBar load(Stage primaryStage, AnchorPane recurringBarPlaceholder, Recurring recurring){
        TaskCardRecurringBar recurringBar =
                UiPartLoader.loadUiPart(primaryStage, recurringBarPlaceholder, new TaskCardRecurringBar());
        recurringBar.recurring = recurring;
        recurringBar.configure();
        return recurringBar;
    }

    public void configure() {
        displayIconAndLabel();
        addToPlaceholder();
    }

    private void displayIconAndLabel() {
        recurringIcon.setImage(getImage(LOOP_ICON_NAME));
        recurringInterval.setText(this.recurring.toHumanReadable());
    }

    private void addToPlaceholder() {       
        placeHolderPane.getChildren().add(rootPanel);
    }

    @Override
    public void setNode(Node node) {
        rootPanel = (HBox)node;
    }
    
    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        placeHolderPane = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }
      
}
