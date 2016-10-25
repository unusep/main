# A0139401N  reused
###### /java/seedu/doerList/logic/commands/ClearCommand.java
``` java
package seedu.doerList.logic.commands;

import seedu.doerList.commons.core.EventsCenter;
import seedu.doerList.commons.events.ui.JumpToCategoryEvent;
import seedu.doerList.model.DoerList;
import seedu.doerList.model.category.BuildInCategoryList;

/**
 * Clears the doerList.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "doerList has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(DoerList.getEmptyDoerList());
        BuildInCategoryList.resetBuildInCategoryPredicate();
        EventsCenter.getInstance().post(new JumpToCategoryEvent(BuildInCategoryList.ALL));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
