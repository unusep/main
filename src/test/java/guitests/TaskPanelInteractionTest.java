//@@author A0147978E
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import seedu.doerList.ui.TaskCard;

public class TaskPanelInteractionTest extends DoerListGuiTest {

    @Test
    public void press_arrowKey_selectionMoved() {
        // press down to `scroll down`
        taskListPanel.clickOnMidOfTaskPanel(10);
        for(int i = 1; i<=8; i++) {
            taskListPanel.useDownArrowKey();
            assertTaskSelected(i);
        }
       
        // the last task is highlighted
        // press down again and there is no effect
        taskListPanel.useDownArrowKey();
        assertTaskSelected(8);
        
        // press up to `scroll up`
        for(int i = 7; i >= 1; i--) {
            taskListPanel.useUpArrowKey();
            assertTaskSelected(i);
        }
        
        // the first task is highlighted
        // press up again and there is no effect
        taskListPanel.useUpArrowKey();
        assertTaskSelected(1);
    }
    
    
    @Test
    public void click_taskCard_taskSelected() {
        // click the first one
        taskListPanel.clickOnMidOfTaskPanel(55);
        assertTaskSelected(1);
    }
    
    /**
     * Validating the displayIndex in selected task in UI is the same as parameter.
     * 
     * @param index
     */
    private void assertTaskSelected(int index) {
        assertNotNull(TaskCard.getSeletedTaskCard());
        assertEquals(TaskCard.getSeletedTaskCard().getDisplayIndex(), index);
    }

}
