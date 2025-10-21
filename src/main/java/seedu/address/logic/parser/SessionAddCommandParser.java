package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SessionAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Price;
import seedu.address.model.person.Session;
import seedu.address.model.person.Subject;

/**
 * Parses input arguments and creates a new SessionAddCommand object.
 * Format: sessionadd INDEX d/DAY t/TIME s/SUBJECT p/PRICE
 */
public class SessionAddCommandParser implements Parser<SessionAddCommand> {

    @Override
    public SessionAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_DAY,
                CliSyntax.PREFIX_TIME,
                CliSyntax.PREFIX_SUBJECT,
                CliSyntax.PREFIX_PRICE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionAddCommand.MESSAGE_USAGE), pe);
        }

        if (arePrefixesPresent(argMultimap,
                CliSyntax.PREFIX_DAY, CliSyntax.PREFIX_TIME,
                CliSyntax.PREFIX_SUBJECT, CliSyntax.PREFIX_PRICE,
                CliSyntax.PREFIX_DURATION)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SessionAddCommand.MESSAGE_USAGE));
        }

        DayOfWeek day = ParserUtil.parseDay(argMultimap.getValue(CliSyntax.PREFIX_DAY).get());
        LocalTime time = ParserUtil.parseTime(argMultimap.getValue(CliSyntax.PREFIX_TIME).get());
        Subject subject = ParserUtil.parseSubject(argMultimap.getValue(CliSyntax.PREFIX_SUBJECT).get());
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(CliSyntax.PREFIX_DURATION).get());

        Price price = ParserUtil.parsePrice(argMultimap.getValue(CliSyntax.PREFIX_PRICE).get());
        if (!price.isSingle()) {
            throw new ParseException("Session price must be a single value (e.g. p/ 50), not a range (e.g. 30-40).");
        }

        Session session = new Session(day, time, duration, subject, price);
        return new SessionAddCommand(index, session);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
