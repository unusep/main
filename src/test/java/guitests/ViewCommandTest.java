//package guitests;

//import guitests.guihandles.TaskCardHandle;
//import org.junit.Test;
//import seedu.doerList.logic.commands.AddCommand;
//import seedu.doerList.commons.core.Messages;
//import seedu.doerList.testutil.TestTask;
//import seedu.doerList.testutil.TestUtil;

////public class ViewCommandTest extends DoerListGuiTest {
//
//    @Test
//    public void view() {
//        //view one task
//        TestTask[] currentList = td.getTypicalTask();
//        commandBox.runCommand("view 2");
//        int targetIndex = 2;
//        assertViewSuccess(targetIndex, currentList);
//
//        //invalid view command
//        commandBox.runCommand("view");
//        assertResultMessage("This command is invalid");
//        
//        //invalid view index
//        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
//        commandBox.runCommand("view " + targetIndex);
//        assertResultMessage("The task index provided is invalid");
//    }

//    private void assertViewSuccess(int targetIndex, TestTask... currentList) {
//        TestTask taskToBeViewed = currentList[targetIndex-1];
//        assertTrue(tastToBeViewed.isFound());
//    }
//}
