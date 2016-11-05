package guitests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandBoxTest extends DoerListGuiTest {

    @Test
    public void commandBox_commandSucceeds_textCleared() {
        commandBox.runCommand(td.task1.getAddCommand());
        assertEquals(commandBox.getCommandInput(), "");
    }

    @Test
    public void commandBox_commandFails_textStays(){
        commandBox.runCommand("invalid command");
        assertEquals(commandBox.getCommandInput(), "invalid command");
    }

}
