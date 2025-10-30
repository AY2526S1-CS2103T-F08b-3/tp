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
 * Supports optional role filters ("tutors" or "students"),
 * and allows multiple criteria such as name, subject, level, and price.
 */
public class FindCommandParser implements Parser<FindCommand> {

    @Override
    public FindCommand parse(String args) throws ParseException {
        validateNotEmpty(args);
        ArgumentMultimap argMultimap = tokenizeArguments(args);
        Predicate<Person> combinedPredicate = p -> true;
        combinedPredicate = addNamePredicate(argMultimap, combinedPredicate);
        combinedPredicate = addSubjectPredicate(argMultimap, combinedPredicate);
        combinedPredicate = addLevelPredicate(argMultimap, combinedPredicate);
        combinedPredicate = addPricePredicate(argMultimap, combinedPredicate);
        ensureAtLeastOneFilter(argMultimap);
        combinedPredicate = addRolePredicate(argMultimap, combinedPredicate);
        return new FindCommand(combinedPredicate);
    }

    private void validateNotEmpty(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    private ArgumentMultimap tokenizeArguments(String args) {
        return ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_PRICE);
    }

    private Predicate<Person> addRolePredicate(ArgumentMultimap map,
                                               Predicate<Person> combinedPredicate) throws ParseException {
        String preamble = map.getPreamble().trim();
        if (preamble.isEmpty()) {
            return combinedPredicate;
        }
        try {
            String parsedRole = ParserUtil.parseRole(preamble);
            return combinedPredicate.and(new RolePredicate(parsedRole));
        } catch (ParseException e) {
            throw new ParseException(
                    "Invalid role. Please key in either students or tutors.\n"
                            + String.format(FindCommand.MESSAGE_USAGE));
        }
    }

    private Predicate<Person> addNamePredicate(ArgumentMultimap map,
                                               Predicate<Person> combinedPredicate) throws ParseException {
        if (map.getValue(PREFIX_NAME).isEmpty()) {
            return combinedPredicate;
        }
        String rawName = map.getValue(PREFIX_NAME).get().trim();
        if (rawName.isEmpty()) {
            throw new ParseException("Name value after n/ cannot be empty.");
        }
        List<String> nameKeywords = Arrays.asList(rawName.split("\\s+"));
        return combinedPredicate.and(new NameContainsKeywordsPredicate(nameKeywords));
    }

    private Predicate<Person> addSubjectPredicate(ArgumentMultimap map,
                                                  Predicate<Person> combinedPredicate) throws ParseException {
        if (map.getValue(PREFIX_SUBJECT).isEmpty()) {
            return combinedPredicate;
        }
        List<String> allSubjects = map.getAllValues(PREFIX_SUBJECT);
        if (allSubjects.stream().allMatch(s -> s.trim().isEmpty())) {
            throw new ParseException("Subject value after sbj/ cannot be empty.");
        }
        List<Subject> subjects = new ArrayList<>();
        for (String s : allSubjects) {
            for (String token : s.trim().split("\\s+")) {
                if (!token.isEmpty()) {
                    subjects.add(ParserUtil.parseSubject(token));
                }
            }
        }
        return combinedPredicate.and(new MatchingSubjectPredicate(subjects));
    }

    private Predicate<Person> addLevelPredicate(ArgumentMultimap map,
                                                Predicate<Person> combinedPredicate) throws ParseException {
        if (map.getValue(PREFIX_LEVEL).isEmpty()) {
            return combinedPredicate;
        }
        List<String> allLevels = map.getAllValues(PREFIX_LEVEL);
        if (allLevels.stream().allMatch(s -> s.trim().isEmpty())) {
            throw new ParseException("Level value after l/ cannot be empty.");
        }
        List<Level> levels = new ArrayList<>();
        for (String s : allLevels) {
            for (String token : s.trim().split("\\s+")) {
                if (!token.isEmpty()) {
                    levels.add(ParserUtil.parseLevel(token));
                }
            }
        }
        return combinedPredicate.and(new MatchingLevelPredicate(levels));
    }

    private Predicate<Person> addPricePredicate(ArgumentMultimap map,
                                                Predicate<Person> combinedPredicate) throws ParseException {
        if (map.getValue(PREFIX_PRICE).isEmpty()) {
            return combinedPredicate;
        }
        List<String> allPrices = map.getAllValues(PREFIX_PRICE);
        if (allPrices.stream().allMatch(s -> s.trim().isEmpty())) {
            throw new ParseException("Price value after p/ cannot be empty.");
        }
        List<Price> prices = new ArrayList<>();
        for (String s : allPrices) {
            for (String token : s.trim().split("\\s+")) {
                if (!token.isEmpty()) {
                    prices.add(ParserUtil.parsePrice(token));
                }
            }
        }
        return combinedPredicate.and(new MatchingPricePredicate(prices));
    }

    private void ensureAtLeastOneFilter(ArgumentMultimap map) throws ParseException {
        if (map.getValue(PREFIX_NAME).isEmpty()
                && map.getValue(PREFIX_SUBJECT).isEmpty()
                && map.getValue(PREFIX_LEVEL).isEmpty()
                && map.getValue(PREFIX_PRICE).isEmpty()) {
            throw new ParseException("Please ensure one valid prefix is used \n"
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
