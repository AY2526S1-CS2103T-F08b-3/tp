package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Person}'s level range includes any of the given levels.
 * Used to filter persons based on their level range during a find operation.
 */
public class MatchingLevelPredicate implements Predicate<Person> {
    private final List<Level> levels;

    public MatchingLevelPredicate(List<Level> levels) {
        this.levels = levels;
    }

    @Override
    public boolean test(Person person) {
        return levels.stream()
                .anyMatch(level -> person.getLevel().includes(level));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MatchingLevelPredicate)) {
            return false;
        }

        MatchingLevelPredicate otherMatchingLevelPredicate = (MatchingLevelPredicate) other;
        return levels.equals(otherMatchingLevelPredicate.levels);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("levels", levels).toString();
    }
}

