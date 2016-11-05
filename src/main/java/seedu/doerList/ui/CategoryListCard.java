//@@author A0147978E
package seedu.doerList.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import seedu.doerList.model.category.Category;

public class CategoryListCard extends UiPart {
    private static final String FXML = "CategoryListCard.fxml";
    private Category category;
    
    @FXML
    private HBox categoryCardPane;
    
    @FXML
    private Label categoryName;
    
    @FXML
    private Label categoryCount;
    
    @FXML
    private StackPane categoryAlertContainer;
    
    @FXML
    private HBox categoryCardSideBar;
    
    @FXML
    private Label categoryAlertCount;
    
    
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
        displayAlertCount();
    }
    
    /**
     * Display the alert count (red) based on number of tasks due
     */
    private void displayAlertCount() {
        if (category.getOverdueTasks().size() > 0) {
            categoryAlertCount.setText(category.getOverdueTasks().size() + "");
        } else {
            categoryCardSideBar.getChildren().remove(categoryAlertContainer);
        }
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    public HBox getLayout() {
        return categoryCardPane;
    }
    
    /** An Cell represents a category in the listView */
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

    @Override
    public void setNode(Node node) {
        // no need
    }
    
}
