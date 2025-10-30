package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SessionAddCommand;
import seedu.address.model.person.Price;
import seedu.address.model.person.Session;
import seedu.address.model.person.Subject;

/**
 * Contains unit tests for {@code SessionAddCommandParser}.
 */
public class SessionAddCommandParserTest {

    private final SessionAddCommandParser parser = new SessionAddCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        String input = "1 d/Monday t/15:30 dur/02:00 sbj/Mathematics p/40";
        Session expectedSession = new Session(
                DayOfWeek.MONDAY,
                LocalTime.of(15, 30),
                Duration.ofHours(2),
                new Subject("Mathematics"),
                new Price(40, 40)
        );
        SessionAddCommand expectedCommand = new SessionAddCommand(Index.fromOneBased(1), expectedSession);
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_differentDayAndTime_success() {
        String input = "2 d/Wednesday t/10:15 dur/01:30 sbj/Science p/55";
        Session expectedSession = new Session(
                DayOfWeek.WEDNESDAY,
                LocalTime.of(10, 15),
                Duration.ofMinutes(90),
                new Subject("Science"),
                new Price(55, 55)
        );
        SessionAddCommand expectedCommand = new SessionAddCommand(Index.fromOneBased(2), expectedSession);
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_extraWhitespace_success() {
        String input = "   3   d/Friday   t/09:00   dur/00:45   sbj/English   p/25   ";
        Session expectedSession = new Session(
                DayOfWeek.FRIDAY,
                LocalTime.of(9, 0),
                Duration.ofMinutes(45),
                new Subject("English"),
                new Price(25, 25)
        );
        SessionAddCommand expectedCommand = new SessionAddCommand(Index.fromOneBased(3), expectedSession);
        assertParseSuccess(parser, input, expectedCommand);
    }
    @Test
    public void parse_missingDuration_failure() {
        String input = "1 d/Monday t/15:30 sbj/Mathematics p/40";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDay_failure() {
        String input = "1 d/Mon t/15:30 dur/01:00 sbj/Mathematics p/40";
        assertParseFailure(parser, input, "Invalid day! Use a full day name (e.g. Monday, Tuesday, Wednesday).");
    }

    @Test
    public void parse_invalidTime_failure() {
        String input = "1 d/Monday t/25:00 dur/01:00 sbj/Mathematics p/40";
        assertParseFailure(parser, input, "Invalid time format! Use HH:mm (e.g. 15:30).");
    }

    @Test
    public void parse_invalidDuration_failure() {
        String input = "1 d/Monday t/15:30 dur/99:99 sbj/Mathematics p/40";
        assertParseFailure(parser, input, "Invalid duration! Hours must be >= 0 and minutes between 00–59.");
    }

    @Test
    public void parse_priceRangeInsteadOfSingle_failure() {
        String input = "1 d/Monday t/15:30 dur/01:30 sbj/Mathematics p/30-40";
        assertParseFailure(parser, input,
                "Session price must be a single value (e.g. p/ 50), not a range (e.g. 30-40).");
    }

    @Test
    public void parse_invalidIndex_failure() {
        String input = "zero d/Monday t/15:30 dur/01:30 sbj/Mathematics p/40";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSubject_failure() {
        String input = "1 d/Monday t/15:30 dur/01:30 p/40";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrice_failure() {
        String input = "1 d/Monday t/15:30 dur/01:30 sbj/Mathematics";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionAddCommand.MESSAGE_USAGE));
    }

}
