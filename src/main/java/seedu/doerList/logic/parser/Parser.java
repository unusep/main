package seedu.doerList.logic.parser;

import static seedu.doerList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doerList.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.commons.util.StringUtil;
import seedu.doerList.logic.commands.*;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    private static final Pattern TASK_INDEX_ARGS_IGNORE_OTHERS = Pattern.compile("(?<targetIndex>.+?)\\s+");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_TITLE_FORMAT = Pattern.compile("-t(?<title>[^-<]+)");
    private static final Pattern TASK_DATA_DESCRIPTION_FORMAT = Pattern.compile("-d(?<description>[^-<]+)");
    private static final Pattern TASK_DATA_STARTTIME_FORMAT = Pattern.compile("<(?<startTime>.+)->");
    private static final Pattern TASK_DATA_ENDTIME_FORMAT = Pattern.compile("->(?<endTime>.+)>");
    private static final Pattern TASK_DATA_CATEGORIES_FORMAT = Pattern.compile("-c(?<categories>[^-<]+)");

    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case ViewCommand.COMMAND_WORD:
            return prepareView(arguments);
            
        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher titleMatcher = TASK_DATA_TITLE_FORMAT.matcher(args.trim());
        final Matcher descriptionMatcher = TASK_DATA_DESCRIPTION_FORMAT.matcher(args.trim());
        final Matcher startTimeMatcher = TASK_DATA_STARTTIME_FORMAT.matcher(args.trim());
        final Matcher endTimeMatcher = TASK_DATA_ENDTIME_FORMAT.matcher(args.trim());
        final Matcher categoriesMatcher = TASK_DATA_CATEGORIES_FORMAT.matcher(args.trim());
        // Validate arg string format (can be only with title)
        if (!titleMatcher.find()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    titleMatcher.group("title"),
                    descriptionMatcher.find() ? descriptionMatcher.group("description") : null,
                    startTimeMatcher.find() ? startTimeMatcher.group("startTime") : null,
                    endTimeMatcher.find() ? endTimeMatcher.group("endTime") : null,
                    getTagsFromArgs(categoriesMatcher.find() ? categoriesMatcher.group("categories") : null)
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Extracts the new person's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments == null) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the edit person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        try {
            final int index = findDisplayedIndexInArgs(args);
            final Matcher titleMatcher = TASK_DATA_TITLE_FORMAT.matcher(args.trim());
            final Matcher descriptionMatcher = TASK_DATA_DESCRIPTION_FORMAT.matcher(args.trim());
            final Matcher startTimeMatcher = TASK_DATA_STARTTIME_FORMAT.matcher(args.trim());
            final Matcher endTimeMatcher = TASK_DATA_ENDTIME_FORMAT.matcher(args.trim());
            final Matcher categoriesMatcher = TASK_DATA_CATEGORIES_FORMAT.matcher(args.trim());
            
            return new EditCommand(
            		index,
            		titleMatcher.find() ? titleMatcher.group("title") : null,
            		descriptionMatcher.find() ? descriptionMatcher.group("description") : null,
            		startTimeMatcher.find() ? startTimeMatcher.group("startTime") : null,
            		endTimeMatcher.find() ? endTimeMatcher.group("endTime") : null,
            		getTagsFromArgs(categoriesMatcher.find() ? categoriesMatcher.group("categories") : null)
                );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Find a single index number in Displayed args.
     *
     * @param args arguments string to find a index number
     * @return the parsed index number
     * @throws ParseException if no region of the args string could be found for the index
     * @throws NumberFormatException the args string region is not a valid number
     */
    private int findDisplayedIndexInArgs(String args) throws NumberFormatException {
        final Matcher matcher = TASK_INDEX_ARGS_IGNORE_OTHERS.matcher(args.trim());
        return Integer.parseInt(matcher.group("targetIndex"));
    }

    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareView(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        return new ViewCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}