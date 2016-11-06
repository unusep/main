//@@author A0139168W
package guitests;

import guitests.guihandles.HelpWindowHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelpWindowTest extends DoerListGuiTest {

    @Test
    public void openHelpWindow() {

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        assertHelpWindowOpen(commandBox.runHelpCommand());

    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
