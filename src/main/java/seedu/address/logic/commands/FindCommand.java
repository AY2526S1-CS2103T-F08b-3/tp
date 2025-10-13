package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in the address book that match
 * the specified search criteria such as role, name, subject,
 * level, or price.
 * <p>
 * The search supports both single values and ranges for level
 * and price fields, and allows combining multiple filters.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons that match the specified criteria.\n"
            + "Format: " + COMMAND_WORD
            + " [ROLE] [n/ NAME] [s/ SUBJECT] [l/ LEVEL (single or range)] [p/ PRICE (single or range)]\n"
            + "Note: You may include multiple values per prefix, and prefix order does not matter.\n"
            + "Example: " + COMMAND_WORD + " tutors n/ Aaron s/ Math English l/ 1-3 p/ 20-50";




    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
