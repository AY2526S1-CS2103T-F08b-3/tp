package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Level;
import seedu.address.model.person.MatchingLevelPredicate;
import seedu.address.model.person.MatchingPricePredicate;
import seedu.address.model.person.MatchingSubjectPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Price;
import seedu.address.model.person.RolePredicate;
import seedu.address.model.person.Subject;

/**
 * Parses user input and creates a new {@code FindCommand}.
 * <p>
 * This parser supports optional role filters ("tutors" or "students"),
 * and allows multiple criteria such as name, subject, level, and price.
 * Both level and price may be single values or ranges.
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments and constructs a {@code FindCommand}.
     *
     * @param args full user input string after the command word
     * @return a new {@code FindCommand} with the combined predicate
     * @throws ParseException if the input format or any field value is invalid
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_PRICE);

        Predicate<Person> combinedPredicate = p -> true;

        int slashIndex = trimmedArgs.indexOf('/');

        if (slashIndex > 1) {
            int roleEndIndex = slashIndex - 2;

            String possibleRole = trimmedArgs.substring(0, roleEndIndex).trim();

            try {
                String parsedRole = ParserUtil.parseRole(possibleRole);
                combinedPredicate = (new RolePredicate(parsedRole)).and(combinedPredicate);
            } catch (ParseException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), e);
            }
        }


        // Name
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().trim().split("\\s+");
            Predicate<Person> namePredicate =
                    new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
            combinedPredicate = combinedPredicate.and(namePredicate);
        }

        // Subject
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            List<String> subjectKeywords = argMultimap.getAllValues(PREFIX_SUBJECT).stream()
                    .flatMap(s -> Arrays.stream(s.trim().split("\\s+")))
                    .toList();
            List<Subject> subjects = new ArrayList<>();
            for (String keyword : subjectKeywords) {
                subjects.add(ParserUtil.parseSubject(keyword));
            }
            Predicate<Person> subjectPredicate = new MatchingSubjectPredicate(subjects);
            combinedPredicate = combinedPredicate.and(subjectPredicate);
        }

        // Level
        if (argMultimap.getValue(PREFIX_LEVEL).isPresent()) {
            List<String> levelStrings = argMultimap.getAllValues(PREFIX_LEVEL).stream()
                    .flatMap(s -> Arrays.stream(s.trim().split("\\s+")))
                    .toList();
            List<Level> levels = new ArrayList<>();
            for (String levelString : levelStrings) {
                levels.add(ParserUtil.parseLevel(levelString)); // throws ParseException if invalid
            }

            Predicate<Person> levelPredicate = new MatchingLevelPredicate(levels);
            combinedPredicate = combinedPredicate.and(levelPredicate);
        }

        // Price
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            List<String> priceStrings = argMultimap.getAllValues(PREFIX_PRICE).stream()
                    .flatMap(s -> Arrays.stream(s.trim().split("\\s+")))
                    .toList();
            List<Price> prices = new ArrayList<>();
            for (String priceString : priceStrings) {
                prices.add(ParserUtil.parsePrice(priceString));
            }

            Predicate<Person> pricePredicate = new MatchingPricePredicate(prices);
            combinedPredicate = combinedPredicate.and(pricePredicate);
        }
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                && argMultimap.getValue(PREFIX_SUBJECT).isEmpty()
                && argMultimap.getValue(PREFIX_LEVEL).isEmpty()
                && argMultimap.getValue(PREFIX_PRICE).isEmpty()) {

            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new FindCommand(combinedPredicate);
    }
}

