package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MatchCommand object
 */
public class MatchCommandParser implements Parser<MatchCommand> {

    @Override
    public MatchCommand parse(String args) throws ParseException {
        String[] parts = args.trim().split("\\s+");
        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }
        try {
            int firstId = Integer.parseInt(parts[0]);
            int secondId = Integer.parseInt(parts[1]);
            if (firstId <= 0 || secondId <= 0) {
                throw new NumberFormatException();
            }
            return new MatchCommand(firstId, secondId);
        } catch (NumberFormatException e) {
            throw new ParseException("IDs must be positive integers. Example: match 12 34");
        }
    }
}
