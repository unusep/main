//@@author A0147978E
package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.doerList.model.category.Category;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestUtil;

/**
 * Provides a handle to all two category in the system.
 */
public class CategorySideBarHandle extends GuiHandle {
    /** Some fields id in the UI. These IDs can be find in {@code /src/main/resources/view/*.fxml} */
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

    /**
     * Get current selection in the category, including BuildInCategory Listview and Category Listview.
     * 
     * @param categoryName
     * @return CategoryCardHandle represent the selected category card
     */
    public CategoryCardHandle getSelection(String categoryName) {
        List<Category> buildInCategoryList = getBuidInCategoryListView().getSelectionModel().getSelectedItems();
        List<Category> categoryList = getCategoryListView().getSelectionModel().getSelectedItems();
        if (buildInCategoryList.size() == 1 && categoryList.size() == 0) {
            return getCategoryCardHandleByName(getBuidInCategoryListView(), buildInCategoryList.get(0));
        } else if (buildInCategoryList.size() == 0 && categoryList.size() == 1){
            return getCategoryCardHandleByName(getCategoryListView(), categoryList.get(0));
        } else if (buildInCategoryList.size() != 0 && categoryList.size() != 0) {
            throw new IllegalArgumentException("Two selections exist in the categorySideBar");
        } else {
            return null;
        }
    }

    public boolean isBuildInCategoryListMatching(List<TestCategory> expectedBuildInCategoryList) {
        return this.isListMatching(getBuidInCategoryListView(), 0, expectedBuildInCategoryList);
    }

    public boolean categoryListMatching(List<TestCategory> expectedCateogires) {
        return this.isListMatching(getCategoryListView(), 0, expectedCateogires);
    }
    
    /**
     * Returns true if the list is showing the category details correctly and in correct order.
     * 
     * @param startPosition The starting position of the sub list.
     * @param persons A list of person in the correct order.
     * @param boolean
     */
    public boolean isListMatching(ListView<Category> listView, int startPosition, List<TestCategory> categories) throws IllegalArgumentException {
        if (categories.size() + startPosition != listView.getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (listView.getItems().size() - 1) + " persons");
        }
        // verify binding data contains in order
        assertTrue(this.containsInOrder(listView, startPosition, categories));
        // verify displaying data
        for (int i = 0; i < categories.size(); i++) {
            final int scrollTo = i;
            guiRobot.interact(() -> {
                listView.scrollTo(scrollTo); // if the scrollbar is hidden, this will be error in log, but it doesn't matter
            });
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTestCategory(getCategoryCardHandleByName(listView, categories.get(i)), categories.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    public CategoryCardHandle getCategoryCardHandleByName(ListView<Category> listView, Category category) {
        Set<Node> nodes = getAllCardNodesFrom(listView);
        Optional<Node> personCardNode = nodes.stream()
                .filter(n -> new CategoryCardHandle(guiRobot, primaryStage, n).isSameCategoryName(category))
                .findFirst();
        if (personCardNode.isPresent()) {
            return new CategoryCardHandle(guiRobot, primaryStage, personCardNode.get());
        } else {
            return null;
        }
    }
    
    protected Set<Node> getAllCardNodesFrom(ListView<Category> listview) {
        return guiRobot.from(listview).lookup("#" + CATEGORY_CARD_PANE).queryAll();
    }
    
    /**
     * Check whether the provided categories is in the same order 
     * as the one in the listView from the startPosition.
     * 
     * @param listView
     * @param startPosition
     * @param categories
     * @return boolean
     */
    public boolean containsInOrder(ListView<Category> listView, int startPosition, List<TestCategory> categories) {
        List<Category> categoriesInList = listView.getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + categories.size() > categoriesInList.size()){
            return false;
        }

        // Return false if any of the category doesn't match
        for (int i = 0; i < categories.size(); i++) {
            if (!categoriesInList.get(startPosition + i).categoryName.equals(categories.get(i).categoryName)) {
                return false;
            }
        }

        return true;
    }
}
