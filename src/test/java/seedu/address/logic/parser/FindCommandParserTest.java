package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;

/**
 * Final passing test suite for FindCommandParser (with role requirement).
 * This version matches the updated parser that requires specifying tutors/students.
 */
public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_validNameArgs_success() {
        assertDoesNotThrow(() -> {
            var command = parser.parse("tutors n/Alice Bob");
            assertTrue(command instanceof FindCommand);
        });
    }

    @Test
    public void parse_validPriceArgs_success() {
        assertDoesNotThrow(() -> {
            var command = parser.parse("students p/10-50");
            assertTrue(command instanceof FindCommand);
        });
    }

    @Test
    public void parse_emptyArgs_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_noRecognizedPrefixes_failure() {
        assertParseFailure(parser, "tutors random text only",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, "students x/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
