package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.doerList.model.category.Category;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestUtil;
import javafx.scene.control.ListView;


import static org.junit.Assert.assertTrue;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class CategorySideBarHandle extends GuiHandle {
    private static final String BUILD_IN_CATEGORY_PLACEHOLDER_ID = "buildInCategoryListPanelPlaceholder";
    private static final String CATEGORY_PLACEHOLDER_ID = "categoryListPanelPlaceholder";
    private static final String CATEGORY_LIST_VIEW = "categoryListView";
    private static final String CATEGORY_CARD_PANE = "categoryCardPane";


    public CategorySideBarHandle(GuiRobot guiRobot, Stage primaryStage){
        super(guiRobot, primaryStage, null);
    }

    public ListView<Category> getBuidInCategoryListView() {
        return (ListView<Category>) guiRobot.from(getNode("#" + BUILD_IN_CATEGORY_PLACEHOLDER_ID))
                .lookup("#" + CATEGORY_LIST_VIEW).query();
    }
    
    public ListView<Category> getCategoryListView() {
        return (ListView<Category>) guiRobot.from(getNode("#" + CATEGORY_PLACEHOLDER_ID))
                .lookup("#" + CATEGORY_LIST_VIEW).query();
    }

    public CategoryCardHandle getSelection(String categoryName) {
        List<Category> buildInCategoryList = getBuidInCategoryListView().getSelectionModel().getSelectedItems();
        List<Category> categoryList = getCategoryListView().getSelectionModel().getSelectedItems();
        if (buildInCategoryList.size() == 1 && categoryList.size() == 0) {
            return getCategoryCardHandle(buildInCategoryList.get(0));
        } else if (buildInCategoryList.size() == 0 && categoryList.size() == 1){
            return getCategoryCardHandle(buildInCategoryList.get(0));
        } else if (buildInCategoryList.size() != 0 && categoryList.size() != 0) {
            throw new IllegalArgumentException("Two selections exist in the categorySideBar");
        } else {
            return null;
        }
    }

    public boolean isBuildInCategoryListMatching(TestCategory[] expectedCateogires) {
        return this.isListMatching(getBuidInCategoryListView(), 0, expectedCateogires);
    }

    public boolean categoryListMatching(TestCategory[] expectedCateogires) {
        return this.isListMatching(getCategoryListView(), 0, expectedCateogires);
    }
    
    /**
     * Returns true if the list is showing the category details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(ListView<Category> listView, int startPosition, TestCategory... categories) throws IllegalArgumentException {
        if (categories.length + startPosition != listView.getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (listView.getItems().size() - 1) + " persons");
        }
        assertTrue(this.containsInOrder(listView, startPosition, categories));
        for (int i = 0; i < categories.length; i++) {
            if (!TestUtil.compareCardAndTestCategory(getCategoryCardHandle(listView, startPosition + i), categories[i])) {
                return false;
            }
        }
        return true;
    }
    
    public CategoryCardHandle getCategoryCardHandle(ListView<Category> listview, int index) {
        return getCategoryCardHandle(listview.getItems().get(index));
    }
    
    public CategoryCardHandle getCategoryCardHandle(Category category) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> personCardNode = nodes.stream()
                .filter(n -> new CategoryCardHandle(guiRobot, primaryStage, n).isSameCategory(category))
                .findFirst();
        if (personCardNode.isPresent()) {
            return new CategoryCardHandle(guiRobot, primaryStage, personCardNode.get());
        } else {
            return null;
        }
    }
    
    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup("#" + CATEGORY_CARD_PANE).queryAll();
    }
    
    /**
     * Returns true if the {@code categories} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(ListView<Category> listView, int startPosition, TestCategory... categories) {
        List<Category> categoriesInList = listView.getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + categories.length > categoriesInList.size()){
            return false;
        }

        // Return false if any of the category doesn't match
        for (int i = 0; i < categories.length; i++) {
            if (!categoriesInList.get(startPosition + i).categoryName.equals(categories[i].categoryName)) {
                return false;
            }
        }

        return true;
    }
}
