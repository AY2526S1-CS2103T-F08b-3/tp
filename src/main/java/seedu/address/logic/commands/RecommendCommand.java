package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Level;
import seedu.address.model.person.MatchingSubjectPredicate;
import seedu.address.model.person.OverlappingLevelPredicate;
import seedu.address.model.person.OverlappingPricePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;

/**
 * Recommends and lists tutors or students in the address book that match
 * the requirements of the specified user, such as budget, subject, level, or price.
 */
public class RecommendCommand extends Command {

    public static final String COMMAND_WORD = "recommend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Recommends persons that match the specified criteria and displays them as a list.\n"
            + "If no criteria is specified, persons that match the subject, level, "
            + "and price requirements of the specified user will be recommended.\n"
            + "Parameters: "
            + "INDEX "
            + "[" + PREFIX_SUBJECT + "] "
            + "[" + PREFIX_LEVEL + "] "
            + "[" + PREFIX_PRICE + "]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRICE;

    public static final String MESSAGE_SUCCESS_TUTORS = "Recommended tutors based on your requirements!";
    public static final String MESSAGE_SUCCESS_STUDENTS = "Recommended students based on your requirements!";
    public static final String MESSAGE_NO_MATCH_TUTORS = "No tutors match your requirements.";
    public static final String MESSAGE_NO_MATCH_STUDENTS = "No students match your requirements.";

    private final Index index;
    private final boolean useSubject;
    private final boolean useLevel;
    private final boolean usePrice;
    private Predicate<Person> predicate;

    /**
     * @param index of the user in the filtered person list to recommend matches for
     * @param useSubject whether to use subject requirement
     * @param useLevel whether to use level requirement
     * @param usePrice whether to use price requirement
     */
    public RecommendCommand(Index index, boolean useSubject, boolean useLevel, boolean usePrice) {
        requireAllNonNull(index);
        this.index = index;
        this.useSubject = useSubject;
        this.useLevel = useLevel;
        this.usePrice = usePrice;
    }

    @Override
    public CommandResult execute(Model model) {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person user = lastShownList.get(index.getZeroBased());
        predicate = p -> true;
        Subject userSubject = user.getSubject();
        Level userLevel = user.getLevel();
        Price userPrice = user.getPrice();

        // If no flags are set, use all requirements
        boolean filterSubject = useSubject;
        boolean filterLevel = useLevel;
        boolean filterPrice = usePrice;
        if (!useSubject && !useLevel && !usePrice) {
            filterSubject = true;
            filterLevel = true;
            filterPrice = true;
        }

        if (filterSubject && userSubject != null) {
            predicate = predicate.and(new MatchingSubjectPredicate(List.of(userSubject)));
        }
        if (filterLevel && userLevel != null) {
            predicate = predicate.and(new OverlappingLevelPredicate(List.of(userLevel)));
        }
        if (filterPrice && userPrice != null) {
            predicate = predicate.and(new OverlappingPricePredicate(List.of(userPrice)));
        }

        if (user.isStudent()) {
            Predicate<Person> tutorPredicate = p -> p.isTutor() && predicate.test(p);
            model.updateFilteredPersonList(tutorPredicate);

            if (model.getFilteredPersonList().isEmpty()) {
                model.updateFilteredPersonList(p -> true); // show all persons
                return new CommandResult(MESSAGE_NO_MATCH_TUTORS);
            }
            return new CommandResult(MESSAGE_SUCCESS_TUTORS);
        } else if (user.isTutor()) {
            Predicate<Person> studentPredicate = p -> p.isStudent() && predicate.test(p);
            model.updateFilteredPersonList(studentPredicate);

            if (model.getFilteredPersonList().isEmpty()) {
                model.updateFilteredPersonList(p -> true); // show all persons
                return new CommandResult(MESSAGE_NO_MATCH_STUDENTS);
            }
            return new CommandResult(MESSAGE_SUCCESS_STUDENTS);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecommendCommand)) {
            return false;
        }
        RecommendCommand otherRecommendCommand = (RecommendCommand) other;
        return index.equals(otherRecommendCommand.index)
            && useSubject == otherRecommendCommand.useSubject
            && useLevel == otherRecommendCommand.useLevel
            && usePrice == otherRecommendCommand.usePrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("useSubject", useSubject)
            .add("useLevel", useLevel)
            .add("usePrice", usePrice)
            .toString();
    }
}
