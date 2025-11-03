package seedu.address.logic.parser;

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
    public void parse_resetCommand_returnsSortCommand() {
        // valid reset command
        assertParseSuccess(parser, "reset",
                new SortCommand("reset"));

        // reset with extra whitespace
        assertParseSuccess(parser, "  reset  ",
                new SortCommand("reset"));
    }

    @Test
    public void parse_resetWithExtraArgs_throwsParseException() {
        // reset should not have additional arguments
        assertParseFailure(parser, "reset p/",
                "Command must be: sort reset\n");

        assertParseFailure(parser, "reset tutors",
                "Command must be: sort reset\n");
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format("Invalid command format! \n%s", SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingFields_throwsParseException() {
        assertParseFailure(parser, "tutors",
                "Please give a criteria to sort by!\n");

        assertParseFailure(parser, "students",
                "Please give a criteria to sort by!\n");

        assertParseFailure(parser, "tutors  ",
                "Please give a criteria to sort by!\n");
    }

    @Test
    public void parse_invalidRole_throwsParseException() {
        assertParseFailure(parser, "teacher p/",
                "Role given must be tutors/students/reset.\n");

        assertParseFailure(parser, "tutor p/",
                "Role given must be tutors/students/reset.\n");

        assertParseFailure(parser, "student l/",
                "Role given must be tutors/students/reset.\n");

        assertParseFailure(parser, "all p/",
                "Role given must be tutors/students/reset.\n");
    }

    @Test
    public void parse_invalidField_throwsParseException() {
        assertParseFailure(parser, "tutors n/",
                "Invalid parameter: n/ is not a valid parameter for this command\n");

        assertParseFailure(parser, "students e/",
                "Invalid parameter: e/ is not a valid parameter for this command\n");

        assertParseFailure(parser, "tutors price",
                "Invalid parameter: price is not a valid parameter for this command\n");

        assertParseFailure(parser, "students level",
                "Invalid parameter: level is not a valid parameter for this command\n");
    }

    @Test
    public void parse_duplicateFields_throwsParseException() {
        assertParseFailure(parser, "tutors p/ p/",
                "p/ is a duplicate field!\n" + SortCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "students l/ l/",
                "l/ is a duplicate field!\n" + SortCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "tutors p/ l/ p/",
                "p/ is a duplicate field!\n" + SortCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "students l/ p/ l/",
                "l/ is a duplicate field!\n" + SortCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_mixedValidAndInvalidFields_throwsParseException() {
        assertParseFailure(parser, "tutors p/ n/",
                "Invalid parameter: n/ is not a valid parameter for this command\n");

        assertParseFailure(parser, "students l/ e/",
                "Invalid parameter: e/ is not a valid parameter for this command\n");

        assertParseFailure(parser, "tutors n/ p/",
                "Invalid parameter: n/ is not a valid parameter for this command\n");
    }

    @Test
    public void parse_allValidFields_returnsSortCommand() {
        // both fields present
        assertParseSuccess(parser, "tutors p/ l/",
                new SortCommand("tutors", Arrays.asList("p/", "l/")));

        assertParseSuccess(parser, "students l/ p/",
                new SortCommand("students", Arrays.asList("l/", "p/")));
    }

    @Test
    public void parse_caseInsensitiveRole_throwsParseException() {
        // roles should be case-sensitive
        assertParseFailure(parser, "Tutors p/",
                "Role given must be tutors/students/reset.\n");

        assertParseFailure(parser, "STUDENTS l/",
                "Role given must be tutors/students/reset.\n");

        assertParseFailure(parser, "Reset",
                "Role given must be tutors/students/reset.\n");
    }
}
