package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.Model;

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
            + COMMAND_WORD + " students l/ p/ (sort by level, then price)";

    public static final String MESSAGE_SUCCESS_TUTORS = "Sorted all tutors by %1$s";
    public static final String MESSAGE_SUCCESS_STUDENTS = "Sorted all students by %1$s";

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

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (role.equals("tutors")) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_TUTORS);
        } else {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        }

        model.sortPersons(sortedBy);

        String criteriaDescription = buildCriteriaDescription();
        String successMessage = role.equals("tutors")
                ? String.format(MESSAGE_SUCCESS_TUTORS, criteriaDescription)
                : String.format(MESSAGE_SUCCESS_STUDENTS, criteriaDescription);

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
}
