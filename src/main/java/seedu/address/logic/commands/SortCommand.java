package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts persons (tutors or students) in the address book by specified field(s).
 * Supports sorting by single or multiple fields in order of priority.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts tutors or students by the specified field(s).\n"
            + "Parameters: ROLE FIELD [FIELD]...\n"
            + "ROLE: tutors or students\n"
            + "FIELD: p/ (price) or l/ (level)\n"
            + "Examples:\n"
            + COMMAND_WORD + " tutors p/ (sort by price only)\n"
            + COMMAND_WORD + " tutors l/ (sort by level only)\n"
            + COMMAND_WORD + " tutors p/ l/ (sort by price, then level)\n"
            + COMMAND_WORD + " students l/ p/ (sort by level, then price)\n"
            + COMMAND_WORD + " reset";

    public static final String MESSAGE_SUCCESS_TUTORS = "Sorted all tutors by %1$s";
    public static final String MESSAGE_SUCCESS_STUDENTS = "Sorted all students by %1$s";
    public static final String MESSAGE_SUCCESS_RESET = "List is reset to the original unfiltered list.";

    private final String role;
    private final List<String> sortedBy;

    /**
     * Creates a SortCommand to sort persons by the specified role and fields.
     *
     * @param role the role to filter and sort ("tutors" or "students")
     * @param sortedBy list of fields to sort by, in order of priority
     */
    public SortCommand(String role, List<String> sortedBy) {
        this.role = role;
        this.sortedBy = sortedBy;
    }

    /**
     * Creates a SortCommand to handle the sort reset command.
     *
     * @param role the reset filter to reset the list to the original unfiltered list.
     */
    public SortCommand(String role) {
        this.role = role;
        this.sortedBy = List.of();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.getAllPersonList().isEmpty()) {
            return new CommandResult("List is empty, there is nothing to sort.");
        }

        Predicate<Person> existingFilter = model.getFilterPredicate();
        Predicate<Person> combinedPredicate;
        if (role.equals("reset")) {
            combinedPredicate = Model.PREDICATE_SHOW_ALL_PERSONS;
        } else if (role.equals("tutors")) {
            combinedPredicate = existingFilter.and(Model.PREDICATE_SHOW_ALL_TUTORS);
        } else {
            combinedPredicate = existingFilter.and(Model.PREDICATE_SHOW_ALL_STUDENTS);
        }

        model.updateFilteredPersonList(combinedPredicate);

        if (model.getFilteredPersonList().isEmpty()) {
            if (role.equals("reset")) {
                return new CommandResult("List is empty.");
            }
            return new CommandResult("No " + role + " found to sort.");
        }

        model.sortPersons(sortedBy);

        String criteriaDescription = buildCriteriaDescription();
        String successMessage;
        if (role.equals("reset")) {
            successMessage = MESSAGE_SUCCESS_RESET;
        } else if (role.equals("tutors")) {
            successMessage = String.format(MESSAGE_SUCCESS_TUTORS, criteriaDescription);
        } else {
            successMessage = String.format(MESSAGE_SUCCESS_STUDENTS, criteriaDescription);
        }
        return new CommandResult(successMessage);
    }

    /**
     * Builds a human-readable description of the sorting criteria.
     *
     * @return a string describing the sort order (e.g., "price, then level")
     */
    public String buildCriteriaDescription() {
        StringBuilder description = new StringBuilder();
        for (int i = 0; i < sortedBy.size(); i++) {
            String criteria = sortedBy.get(i);
            String name = criteria.equals("p/") ? "price" : "level";
            description.append(name);

            if (i < sortedBy.size() - 1) {
                description.append(", then ");
            }
        }
        return description.toString();
    }

    /**
     * Returns true if both SortCommand objects have the same role and sort criteria.
     * This defines equality between two SortCommand objects.
     *
     * @param other the other object to compare to
     * @return true if both objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return role.equals(otherSortCommand.role)
                && sortedBy.equals(otherSortCommand.sortedBy);
    }

    /**
     * Returns a string representation of this SortCommand.
     *
     * @return a string containing the role and sort criteria
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("role", role)
                .add("sortedBy", sortedBy)
                .toString();
    }
}
