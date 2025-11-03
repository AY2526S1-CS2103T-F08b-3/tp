package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UnmatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmatchCommand object
 */
public class UnmatchCommandParser implements Parser<UnmatchCommand> {
    public static final String MESSAGE_INVALID_ID = "IDs must be positive integers. Example: unmatch 1";
    @Override
    public UnmatchCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmatchCommand.MESSAGE_USAGE));
        }
        try {
            int id = Integer.parseInt(trimmed);
            if (id <= 0) {
                throw new NumberFormatException();
            }
            return new UnmatchCommand(id);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_ID);
        }
    }
}
