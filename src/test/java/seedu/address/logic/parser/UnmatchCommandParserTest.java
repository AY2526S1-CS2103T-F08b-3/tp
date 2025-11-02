package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.UnmatchCommandParser.MESSAGE_INVALID_ID;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnmatchCommand;

/**
 * Contains unit tests for UnmatchCommandParser.
 */
public class UnmatchCommandParserTest {

    private UnmatchCommandParser parser = new UnmatchCommandParser();

    @Test
    public void parse_validArgs_returnsUnmatchCommand() {
        assertParseSuccess(parser, "1", new UnmatchCommand(1));
        assertParseSuccess(parser, "5", new UnmatchCommand(5));
        assertParseSuccess(parser, "100", new UnmatchCommand(100));
    }

    @Test
    public void parse_validArgsWithWhitespace_returnsUnmatchCommand() {
        assertParseSuccess(parser, "  1  ", new UnmatchCommand(1));
        assertParseSuccess(parser, "\t5\t", new UnmatchCommand(5));
        assertParseSuccess(parser, "   100   ", new UnmatchCommand(100));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmatchCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmatchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "1 2 3",
                String.format(MESSAGE_INVALID_ID));
    }

    @Test
    public void parse_invalidId_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_ID));
    }

    @Test
    public void parse_nonNumericInput_throwsParseException() {
        assertParseFailure(parser, "one",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "1.5",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "abc123",
                String.format(MESSAGE_INVALID_ID));
    }

    @Test
    public void parse_specialCharacters_throwsParseException() {
        assertParseFailure(parser, "1!",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "@#$",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "1@2",
                String.format(MESSAGE_INVALID_ID));
    }

    @Test
    public void parse_negativeNumber_throwsParseException() {
        assertParseFailure(parser, "-5",
                String.format(MESSAGE_INVALID_ID));

        assertParseFailure(parser, "-100",
                String.format(MESSAGE_INVALID_ID));
    }

    @Test
    public void parse_zero_throwsParseException() {
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_ID));
    }
}
