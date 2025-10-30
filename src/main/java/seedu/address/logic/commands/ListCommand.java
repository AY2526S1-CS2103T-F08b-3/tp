package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTORS;

import seedu.address.model.Model;

/**
 * Lists either all students or all tutors in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tutors or students "
            + "and displays them as a list with index numbers.\n"
            + "Parameter: 'tutors' / 'students'\n"
            + "Example: " + COMMAND_WORD + " tutors";

    public static final String MESSAGE_SUCCESS_TUTORS = "Listed all tutors.";
    public static final String MESSAGE_SUCCESS_STUDENTS = "Listed all students.";
    public static final String MESSAGE_SUCCESS_EVERYONE = "Listed all students and tutors.";

    private String role = "";

    public ListCommand(String role) {
        this.role = role;
    }

    public ListCommand() {
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (role.equals("tutors")) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_TUTORS);
            return new CommandResult(MESSAGE_SUCCESS_TUTORS);
        } else if (role.equals("students")) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_STUDENTS);
            return new CommandResult(MESSAGE_SUCCESS_STUDENTS);
        }

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS_EVERYONE);
    }
}
