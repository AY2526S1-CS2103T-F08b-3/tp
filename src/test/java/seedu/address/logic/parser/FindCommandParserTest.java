package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;

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
    public void parse_validSubjectArgs_success() {
        assertDoesNotThrow(() -> {
            var command = parser.parse("students s/Mathematics Science");
            assertTrue(command instanceof FindCommand);
        });
    }

    @Test
    public void parse_validLevelArgs_success() {
        assertDoesNotThrow(() -> {
            var command = parser.parse("tutors l/1-4");
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
    public void parse_missingRole_failure() {
        assertParseFailure(parser, "n/Alice",
                "Please specify if you are trying to find tutors or students.\n"
                        + String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRole_failure() {
        assertParseFailure(parser, "teacher n/Alice",
                "Please specify if you are trying to find tutors or students.\n"
                        + String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyNamePrefix_failure() {
        assertParseFailure(parser, "tutors n/",
                "Name value after n/ cannot be empty.");
    }

    @Test
    public void parse_emptySubjectPrefix_failure() {
        assertParseFailure(parser, "students s/",
                "Subject value after s/ cannot be empty.");
    }

    @Test
    public void parse_emptyLevelPrefix_failure() {
        assertParseFailure(parser, "tutors l/",
                "Level value after l/ cannot be empty.");
    }

    @Test
    public void parse_emptyPricePrefix_failure() {
        assertParseFailure(parser, "students p/",
                "Price value after p/ cannot be empty.");
    }

}
