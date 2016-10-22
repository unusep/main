package guitests;

import guitests.guihandles.TaskCardHandle;
import javafx.scene.input.KeyCode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertNull;
import static seedu.doerList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import seedu.doerList.logic.commands.ViewCommand;
import seedu.doerList.model.category.BuildInCategoryList;
import seedu.doerList.testutil.TestCategory;
import seedu.doerList.testutil.TestTask;
import seedu.doerList.commons.core.Messages;
import seedu.doerList.ui.TaskCard;

public class TaskPanelInteractionTest extends DoerListGuiTest {

    @Test
    public void arrowKeyPressed() {
        // press down
        taskListPanel.clickOnMidOfTaskPanel(10);
        for(int i = 1; i<=8; i++) {
            taskListPanel.useDownArrowKey();
            assertTaskSelected(i);
        }
       
        // press down again no effect
        taskListPanel.useDownArrowKey();
        assertTaskSelected(8);
        
        // press up
        for(int i = 7; i >= 1; i--) {
            taskListPanel.useUpArrowKey();
            assertTaskSelected(i);
        }
        
        // press up again no effect
        taskListPanel.useUpArrowKey();
        assertTaskSelected(1);
    }
    
    
    @Test
    public void clickTaskCard() {
        // click the first one
        taskListPanel.clickOnMidOfTaskPanel(55);
        assertTaskSelected(1);
    }
    
    private void assertTaskSelected(int index) {
        assertNotNull(TaskCard.getSeletedTaskCard());
        assertEquals(TaskCard.getSeletedTaskCard().getDisplayIndex(), index);
    }

}
