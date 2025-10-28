package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecommendCommand;

public class RecommendCommandParserTest {
    private final RecommendCommandParser parser = new RecommendCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validIndexOnly_returnsRecommendCommand() {
        RecommendCommand expected = new RecommendCommand(Index.fromOneBased(1), false, false, false);
        assertParseSuccess(parser, "1", expected);
    }

    @Test
    public void parse_validIndexWithSubjectFlag_returnsRecommendCommand() {
        RecommendCommand expected = new RecommendCommand(Index.fromOneBased(1), true, false, false);
        assertParseSuccess(parser, "1 s/", expected);
    }

    @Test
    public void parse_validIndexWithLevelFlag_returnsRecommendCommand() {
        RecommendCommand expected = new RecommendCommand(Index.fromOneBased(1), false, true, false);
        assertParseSuccess(parser, "1 l/", expected);
    }

    @Test
    public void parse_validIndexWithPriceFlag_returnsRecommendCommand() {
        RecommendCommand expected = new RecommendCommand(Index.fromOneBased(1), false, false, true);
        assertParseSuccess(parser, "1 p/", expected);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, RecommendCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, RecommendCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validIndexWithAllFlags_returnsRecommendCommand() {
        RecommendCommand expected = new RecommendCommand(Index.fromOneBased(1), true, true, true);
        assertParseSuccess(parser, "1 s/ l/ p/", expected);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "s/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "l/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "p/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonIntegerIndex_throwsParseException() {
        assertParseFailure(parser, "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_valueAfterPrefix_throwsParseException() {
        assertParseFailure(parser, "1 s/abc", "Subject specifier should not have a value.");
        assertParseFailure(parser, "1 l/123", "Level specifier should not have a value.");
        assertParseFailure(parser, "1 p/xyz", "Price specifier should not have a value.");
    }

    @Test
    public void parse_excessiveWhitespace_returnsRecommendCommand() {
        RecommendCommand expected = new RecommendCommand(Index.fromOneBased(1), true, true, true);
        assertParseSuccess(parser, "  1   s/   l/   p/  ", expected);
    }
}
