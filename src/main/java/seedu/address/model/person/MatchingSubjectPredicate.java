package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Person}'s subject matches any of the given subjects.
 * Used to filter persons based on their subject field during a find operation.
 */
public class MatchingSubjectPredicate implements Predicate<Person> {
    private final List<Subject> subjects;

    public MatchingSubjectPredicate(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean test(Person person) {
        return subjects.stream()
                .anyMatch(subject -> person.getSubject().equals(subject));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchingSubjectPredicate)) {
            return false;
        }

        MatchingSubjectPredicate otherMatchingSubjectPredicate = (MatchingSubjectPredicate) other;
        return subjects.equals(otherMatchingSubjectPredicate.subjects);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("subjects", subjects).toString();
    }
}

