package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.dashboard.DeleteTaskCommand;
import seedu.address.logic.commands.diary.DeleteDiaryCommand;
import seedu.address.logic.commands.diary.DeletePageCommand;
import seedu.address.logic.commands.exercise.DeleteExerciseCommand;
import seedu.address.logic.commands.health.DeleteRecordCommand;
import seedu.address.logic.commands.recipe.DeleteRecipeCommand;
import seedu.address.logic.parser.dashboard.DeleteTaskCommandParser;
import seedu.address.logic.parser.diary.DeleteDiaryCommandParser;
import seedu.address.logic.parser.diary.DeletePageCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exercise.DeleteExerciseCommandParser;
import seedu.address.logic.parser.health.DeleteRecordCommandParser;
import seedu.address.logic.parser.recipe.DeleteRecipeCommandParser;

/**
 * Parses input arguments and creates a new DeleteRecipeCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRecipeCommand
     * and returns a DeleteRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case DeleteTaskCommand.VARIANT_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case DeleteRecipeCommand.VARIANT_WORD:
            return new DeleteRecipeCommandParser().parse(arguments);

        case DeleteRecordCommand.VARIANT_WORD:
            return new DeleteRecordCommandParser().parse(arguments);

        case DeleteDiaryCommand.VARIANT_WORD:
            return new DeleteDiaryCommandParser().parse(arguments);

        case DeletePageCommand.VARIANT_WORD:
            return new DeletePageCommandParser().parse(arguments);

        case DeleteExerciseCommand.VARIANT_WORD:
            return new DeleteExerciseCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
