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

public class CategoryListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "CategoryListPanel.fxml";
    private AnchorPane placeHolderPane;
    private VBox root;
    
    @FXML
    private ListView<Category> categoryListView;
    
    public CategoryListPanel() {
        super();
    }
    
    public static CategoryListPanel load(Stage primaryStage, AnchorPane placeholder, ObservableList<Category> categories) {
        CategoryListPanel buildInCategoryListPanel = UiPartLoader.loadUiPart(primaryStage, placeholder, new CategoryListPanel());
        buildInCategoryListPanel.configure(categories);
        return buildInCategoryListPanel;
    }
    
    private void configure(ObservableList<Category> categories) {
        setConnections(categories);
        addToPlaceholder();
    }
    
    private void setConnections(ObservableList<Category> categoryList) {
        categoryListView.setItems(categoryList);
        categoryListView.setCellFactory(listView -> new CategoryListCard.CategoryListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }
    
    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(root);
    }
    
    private void setEventHandlerForSelectionChangeEvent() {
        categoryListView.getSelectionModel().selectedItemProperty()
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
