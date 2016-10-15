package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.doerList.model.category.Category;
import seedu.doerList.testutil.TestCategory;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class CategoryCardHandle extends GuiHandle {
    private static final String CATEGORY_NAME_FIELD_ID = "categoryName";
    private static final String CATEGORY_COUNT_FIELD_ID = "categoryCount";

    private Node node;

    public CategoryCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
    
    protected String getContentFromText(String fieldId) { 
        return getTextFromText(fieldId, node);
    }

    public String getCategoryName() {
        return getTextFromLabel("#" + CATEGORY_NAME_FIELD_ID);
    }

    public String getCategoryCount() {
        return getTextFromLabel("#" + CATEGORY_COUNT_FIELD_ID);
    }
    
    public boolean isSameTestCategory(TestCategory category){
        return getCategoryName().equals(category.categoryName)
                && getCategoryCount().equals(String.valueOf(category.expectedNumTasks));
    }
    
    public boolean isSameCategory(Category category){
        return getCategoryName().equals(category.categoryName)
                && getCategoryCount().equals(String.valueOf(category.getTasks().size()));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CategoryCardHandle) {
            CategoryCardHandle handle = (CategoryCardHandle) obj;
            return getCategoryName().equals(handle.getCategoryName())
                    && getCategoryCount().equals(handle.getCategoryCount());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "[" + getCategoryName() + "]" + "(" + getCategoryCount() + ")";
                
    }
}
