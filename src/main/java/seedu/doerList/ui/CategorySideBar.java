//@@author A0147978E
package seedu.doerList.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.model.category.Category;

public class CategorySideBar extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(CategorySideBar.class);
    
    private static final String FXML = "CategorySideBar.fxml";
    private AnchorPane placeHolderPane;
    private VBox root;
    
    private CategoryListPanel buildInCategoryList;
    private CategoryListPanel categoryList;
    
    @FXML
    private AnchorPane buildInCategoryListPanelPlaceholder;
    
    @FXML
    private AnchorPane categoryListPanelPlaceholder;
    
    public CategorySideBar() {
        super();
    }
    
    public static CategorySideBar load(Stage primaryStage, AnchorPane placeholder, 
            ObservableList<Category> buildInCategories, ObservableList<Category> categories) {
        CategorySideBar buildInCategoryListPanel = UiPartLoader.loadUiPart(primaryStage, placeholder, new CategorySideBar());
        buildInCategoryListPanel.configure(buildInCategories, categories);
        return buildInCategoryListPanel;
    }
    
    private void configure(ObservableList<Category> buildInCategories, ObservableList<Category> categories) {
        buildInCategoryList = CategoryListPanel.load(primaryStage, getBuildInCategoryListPlaceholder(), buildInCategories);
        categoryList = CategoryListPanel.load(primaryStage, getCategoryListPanelPlaceholder(), categories);
        addToPlaceholder();
    }
    
    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(root);
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
    
    public AnchorPane getBuildInCategoryListPlaceholder() {
        return buildInCategoryListPanelPlaceholder;
    }
    
    public AnchorPane getCategoryListPanelPlaceholder() {
        return categoryListPanelPlaceholder;
    }
    
    /**
     * Scroll to the category specific by {@code target}.
     * Both BuildInCategoryList and CategoryList be asked to scroll but the target
     * only exist in one list
     * 
     * @param target
     */
    public void categoryScrollTo(Category target) {
        categoryList.scrollTo(target);
        buildInCategoryList.scrollTo(target);  
    }
    
    /**
     * Set the {@code selection} as active in the list and clear other selection.
     * 
     * @param selection
     */
    public void clearOtherSelectionExcept(Category selection) {
        if (selection.isBuildIn()) {
            categoryList.clearSelection();
        } else {
            buildInCategoryList.clearSelection();
        }
    }

    /**
     * Refresh the displaying of the categories.
     * This is used when the ObservableList has been updated.
     */
    public void refreshCategories() {
        logger.info("CategorySideBarController : Refreshing the categories UI");
        categoryList.redrawListView();
        buildInCategoryList.redrawListView();
    }
    
}
