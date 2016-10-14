package seedu.doerList.ui;

import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.model.category.Category;

public class CategoryListCard extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "CategoryListCard.fxml";
    private HBox root;
    Category category;
    
    @FXML
    private HBox cardPane;
    
    @FXML
    private Label categoryName;
    
    @FXML
    private Label categoryCount;
    
    public CategoryListCard() {
        super();
    }
    
    public static CategoryListCard load(Category category) {
        CategoryListCard card = new CategoryListCard();
        card.category = category;
        return UiPartLoader.loadUiPart(card);
    }
    
    @FXML
    public void initialize() {
        categoryName.setText(category.categoryName);
        categoryCount.setText(category.getTasks().size() + "");
    }
       
    @Override
    public void setNode(Node node) {
        root = (HBox) node;
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    
    public HBox getLayout() {
        return cardPane;
    }
    
    static class CategoryListViewCell extends ListCell<Category> {

        public CategoryListViewCell() {
        }

        @Override
        protected void updateItem(Category category, boolean empty) {
            super.updateItem(category, empty);

            if (empty || category == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(CategoryListCard.load(category).getLayout());
            }
        }
    }
    
}
