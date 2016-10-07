package seedu.doerList.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A ui for the header of a section.
 */
public class TaskListHeader extends UiPart {

    private static final String FXML = "TaskListHeader.fxml";

    private AnchorPane placeHolder;

    private VBox mainPane;
    
    @FXML
    private Label titleField;

    public static TaskListHeader load(Stage primaryStage, AnchorPane placeHolder, String title) {
        TaskListHeader header = UiPartLoader.loadUiPart(primaryStage, placeHolder, new TaskListHeader());
        header.configure(title);
        return header;
    }

    public void configure(String title) {
        titleField.setText(title);
        placeHolder.getChildren().add(mainPane);
    }

    @Override
    public void setNode(Node node) {
        mainPane = (VBox) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

}
