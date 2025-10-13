package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final Set<String> VALID_FIELDS = new HashSet<>(Arrays.asList("p/", "l/"));

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @param args the user input arguments
     * @return a SortCommand object with the parsed role and sort fields
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] splitArgs = trimmedArgs.split("\\s+");

        if (splitArgs.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String role = splitArgs[0];

        if (!role.equals("tutors") && !role.equals("students")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        List<String> sortCriteria = new ArrayList<>();
        Set<String> seenCriteria = new HashSet<>();

        // add the criteria to the sortCriteria list
        for (int i = 1; i < splitArgs.length; i++) {

            String currentCriteria = splitArgs[i];

            if (!VALID_FIELDS.contains(currentCriteria)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }

            if (seenCriteria.contains(currentCriteria)) {
                throw new ParseException(currentCriteria + " is a duplicate field!\n" + SortCommand.MESSAGE_USAGE);
            }

            sortCriteria.add(currentCriteria);
            seenCriteria.add(currentCriteria);
        }

        if (sortCriteria.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(role, sortCriteria);
    }
}
