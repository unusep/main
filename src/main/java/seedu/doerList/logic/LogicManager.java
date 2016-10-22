package seedu.doerList.logic;

import javafx.collections.ObservableList;
import seedu.doerList.commons.core.ComponentManager;
import seedu.doerList.commons.core.LogsCenter;
import seedu.doerList.logic.commands.Command;
import seedu.doerList.logic.commands.CommandResult;
import seedu.doerList.logic.parser.Parser;
import seedu.doerList.model.Model;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.ReadOnlyTask;
import seedu.doerList.storage.Storage;

import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public ObservableList<Category> getCategoryList() {
        return model.getCategoryList();
    }
    
    //@@author A0147978E
    @Override
    public ObservableList<Category> getBuildInCategoryList() {
        return model.getBuildInCategoryList();
    }

    //@@author A0147978E
    @Override
    public void setPredicateForTaskList(Predicate<ReadOnlyTask> predicate) {
        model.updateFilteredTaskList(predicate);
    }
}
