package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SessionDeleteCommand;

public class SessionDeleteCommandParserTest {

    private final SessionDeleteCommandParser parser = new SessionDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsSessionDeleteCommand() {
        SessionDeleteCommand expectedCommand = new SessionDeleteCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedCommand);

        expectedCommand = new SessionDeleteCommand(Index.fromOneBased(5));
        assertParseSuccess(parser, "5", expectedCommand);
    }

    @Test
    public void parse_validArgsWithWhitespace_returnsSessionDeleteCommand() {
        SessionDeleteCommand expectedCommand = new SessionDeleteCommand(Index.fromOneBased(2));
        assertParseSuccess(parser, "  2  ", expectedCommand);
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonNumericArg_throwsParseException() {
        assertParseFailure(parser, "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, "-3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleNumbers_throwsParseException() {
        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionDeleteCommand.MESSAGE_USAGE));
    }
}
