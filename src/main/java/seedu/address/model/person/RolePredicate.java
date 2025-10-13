package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Person}'s role matches the given role.
 * Used to filter between tutors and students in a find command.
 */
public class RolePredicate implements Predicate<Person> {
    private final String role;


    public RolePredicate(String role) {
        this.role = role.trim().toLowerCase();
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);

        if (role.equals("tutor") || role.equals("tutors")) {
            return person.isTutor();
        }

        if (role.equals("student") || role.equals("students")) {
            return person.isStudent();
        }

        return true;
    }



    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RolePredicate
                && role.equals(((RolePredicate) other).role));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("role", role)
                .toString();
    }
}
