package seedu.doerList.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.commons.events.ui.CategorySelectionChangedEvent;
import seedu.doerList.model.category.Category;

public class BuildInCategoryListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "BuildInCategoryListPanel.fxml";
    private AnchorPane placeHolderPane;
    private VBox root;
    
    @FXML
    private ListView<Category> buildInCategoryListView;
    
    public BuildInCategoryListPanel() {
        super();
    }
    
    public static BuildInCategoryListPanel load(Stage primaryStage, AnchorPane placeholder, ObservableList<Category> buildInCategories) {
        BuildInCategoryListPanel buildInCategoryListPanel = UiPartLoader.loadUiPart(primaryStage, placeholder, new BuildInCategoryListPanel());
        buildInCategoryListPanel.configure(buildInCategories);
        return buildInCategoryListPanel;
    }
    
    private void configure(ObservableList<Category> buildInCategories) {
        setConnections(buildInCategories);
        addToPlaceholder();
    }
    
    private void setConnections(ObservableList<Category> categoryList) {
        buildInCategoryListView.setItems(categoryList);
        buildInCategoryListView.setCellFactory(listView -> new CategoryListCard.CategoryListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }
    
    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(root);
    }
    
    private void setEventHandlerForSelectionChangeEvent() {
        buildInCategoryListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection for category changed to : '" + newValue + "'");
                        raise(new CategorySelectionChangedEvent(newValue));
                    }
                });
    }
    
    @Override
    public void setNode(Node node) {
        root = (VBox) node;
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
}
