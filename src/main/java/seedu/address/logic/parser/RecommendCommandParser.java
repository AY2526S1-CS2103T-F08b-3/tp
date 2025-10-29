package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.RecommendCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RecommendCommand object
 */
public class RecommendCommandParser implements Parser<RecommendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecommendCommand
     * and returns a RecommendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecommendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_PRICE);

        String userArg = args.trim(); // user index (positive integer)
        if (userArg.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
        }

        String indexStr = argMultimap.getPreamble();
        Index index;

        if (StringUtil.isInvalidIndexInteger(indexStr)) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        try {
            index = ParserUtil.parseIndex(indexStr);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE), pe);
        }

        // Check that prefixes have no value
        boolean useSubject = false;
        boolean useLevel = false;
        boolean usePrice = false;
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            String subjectValue = argMultimap.getValue(PREFIX_SUBJECT).get().trim();
            if (!subjectValue.isEmpty()) {
                throw new ParseException("Subject specifier should not have a value.");
            }
            useSubject = true;
        }
        if (argMultimap.getValue(PREFIX_LEVEL).isPresent()) {
            String levelValue = argMultimap.getValue(PREFIX_LEVEL).get().trim();
            if (!levelValue.isEmpty()) {
                throw new ParseException("Level specifier should not have a value.");
            }
            useLevel = true;
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            String priceValue = argMultimap.getValue(PREFIX_PRICE).get().trim();
            if (!priceValue.isEmpty()) {
                throw new ParseException("Price specifier should not have a value.");
            }
            usePrice = true;
        }

        return new RecommendCommand(index, useSubject, useLevel, usePrice);
    }
}
