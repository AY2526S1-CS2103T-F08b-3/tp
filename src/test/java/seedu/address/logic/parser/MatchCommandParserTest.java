package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.MatchCommandParser.MESSAGE_INVALID_ID;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MatchCommand;

/**
 * Contains unit tests for MatchCommandParser.
 */
public class MatchCommandParserTest {

    private MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void parse_validArgs_returnsMatchCommand() {
        assertParseSuccess(parser, "1 2", new MatchCommand(1, 2));
        assertParseSuccess(parser, "5 10", new MatchCommand(5, 10));
        assertParseSuccess(parser, "100 200", new MatchCommand(100, 200));
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_returnsMatchCommand() {
        assertParseSuccess(parser, "  1   2  ", new MatchCommand(1, 2));
        assertParseSuccess(parser, "\t5\t10\t", new MatchCommand(5, 10));
    }

    @Test
    public void parse_validArgsReversedOrder_returnsMatchCommand() {
        // Order shouldn't matter for matching
        assertParseSuccess(parser, "2 1", new MatchCommand(2, 1));
        assertParseSuccess(parser, "10 5", new MatchCommand(10, 5));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSecondId_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        assertParseFailure(parser, "1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 2 3 4",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFirstId_throwsParseException() {
        assertParseFailure(parser, "a 2",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "-1 2",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "0 2",
                String.format(MESSAGE_INVALID_ID));
    }

    @Test
    public void parse_invalidSecondId_throwsParseException() {
        assertParseFailure(parser, "1 b",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "1 -2",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "1 0",
                String.format(MESSAGE_INVALID_ID));
    }

    @Test
    public void parse_bothIdsInvalid_throwsParseException() {
        assertParseFailure(parser, "a b",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "-1 -2",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "0 0",
                String.format(MESSAGE_INVALID_ID));
    }

    @Test
    public void parse_nonNumericInput_throwsParseException() {
        assertParseFailure(parser, "one two",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "1.5 2.5",
                String.format(MESSAGE_INVALID_ID));
    }

    @Test
    public void parse_specialCharacters_throwsParseException() {
        assertParseFailure(parser, "1! 2@",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "# $",
                String.format(MESSAGE_INVALID_ID));
    }
}
