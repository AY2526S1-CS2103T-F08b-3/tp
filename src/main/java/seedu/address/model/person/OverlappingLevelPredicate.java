package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Person}'s level range overlaps with any of the given levels.
 * Used to filter persons based on their level range during a recommend operation.
 */
public class OverlappingLevelPredicate implements Predicate<Person> {
    private final List<Level> levels;

    public OverlappingLevelPredicate(List<Level> levels) {
        this.levels = levels;
    }

    @Override
    public boolean test(Person person) {
        return levels.stream()
                .anyMatch(level -> person.getLevel().intersects(level));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OverlappingLevelPredicate)) {
            return false;
        }

        OverlappingLevelPredicate otherOverlappingLevelPredicate = (OverlappingLevelPredicate) other;
        return levels.equals(otherOverlappingLevelPredicate.levels);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("levels", levels).toString();
    }
}
