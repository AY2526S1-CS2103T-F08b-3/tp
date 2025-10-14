package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

/**
 * Contains unit tests for SortCommandParser.
 */
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgsSingleField_returnsSortCommand() {
        // sort tutors by price
        assertParseSuccess(parser, "tutors p/",
                new SortCommand("tutors", Arrays.asList("p/")));

        // sort tutors by level
        assertParseSuccess(parser, "tutors l/",
                new SortCommand("tutors", Arrays.asList("l/")));

        // sort students by price
        assertParseSuccess(parser, "students p/",
                new SortCommand("students", Arrays.asList("p/")));

        // sort students by level
        assertParseSuccess(parser, "students l/",
                new SortCommand("students", Arrays.asList("l/")));
    }

    @Test
    public void parse_validArgsMultipleFields_returnsSortCommand() {
        // sort tutors by price then level
        assertParseSuccess(parser, "tutors p/ l/",
                new SortCommand("tutors", Arrays.asList("p/", "l/")));

        // sort tutors by level then price
        assertParseSuccess(parser, "tutors l/ p/",
                new SortCommand("tutors", Arrays.asList("l/", "p/")));

        // sort students by price then level
        assertParseSuccess(parser, "students p/ l/",
                new SortCommand("students", Arrays.asList("p/", "l/")));

        // sort students by level then price
        assertParseSuccess(parser, "students l/ p/",
                new SortCommand("students", Arrays.asList("l/", "p/")));
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_returnsSortCommand() {
        // extra whitespace before and after role
        assertParseSuccess(parser, "  tutors   p/  ",
                new SortCommand("tutors", Arrays.asList("p/")));

        // extra whitespace between fields
        assertParseSuccess(parser, "students  p/   l/  ",
                new SortCommand("students", Arrays.asList("p/", "l/")));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingRole_throwsParseException() {
        assertParseFailure(parser, "p/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingFields_throwsParseException() {
        assertParseFailure(parser, "tutors",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "students",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRole_throwsParseException() {
        assertParseFailure(parser, "teacher p/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "tutor p/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "student l/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidField_throwsParseException() {
        assertParseFailure(parser, "tutors n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "students e/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "tutors price",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateFields_throwsParseException() {
        assertParseFailure(parser, "tutors p/ p/",
                "p/ is a duplicate field!\n" + SortCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "students l/ l/",
                "l/ is a duplicate field!\n" + SortCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "tutors p/ l/ p/",
                "p/ is a duplicate field!\n" + SortCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_mixedValidAndInvalidFields_throwsParseException() {
        assertParseFailure(parser, "tutors p/ n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "students l/ e/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArguments_throwsParseException() {
        // more than just role and fields
        assertParseFailure(parser, "tutors students p/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyRole_throwsParseException() {
        assertParseFailure(parser, "tutors ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
