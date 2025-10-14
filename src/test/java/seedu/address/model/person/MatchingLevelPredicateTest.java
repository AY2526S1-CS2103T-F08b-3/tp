package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;


/**
 * Unit tests for {@link MatchingLevelPredicate}.
 */
public class MatchingLevelPredicateTest {

    private Person makePersonWithLevel(Level level) {
        return new Person(
                "tutor",
                new Name("Alice"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123 Street"),
                new Subject("Math"),
                level,
                new Price(40, 60),
                Collections.emptySet()
        );
    }

    @Test
    public void equals() {
        List<Level> levelsA = List.of(new Level(1, 3));
        List<Level> levelsB = List.of(new Level(4, 6));

        MatchingLevelPredicate predicateA = new MatchingLevelPredicate(levelsA);
        MatchingLevelPredicate predicateB = new MatchingLevelPredicate(levelsB);

        // same object → true
        assertTrue(predicateA.equals(predicateA));

        // same values → true
        assertTrue(predicateA.equals(new MatchingLevelPredicate(List.of(new Level(1, 3)))));

        // different type → false
        assertFalse(predicateA.equals("String"));

        // null → false
        assertFalse(predicateA.equals(null));

        // different levels → false
        assertFalse(predicateA.equals(predicateB));
    }

    @Test
    public void testPersonLevelIncludedReturnsTrue() {
        Person person = makePersonWithLevel(new Level(2, 5));
        MatchingLevelPredicate predicate = new MatchingLevelPredicate(List.of(new Level(3, 3)));
        assertTrue(predicate.test(person)); // 3 is within 2–5
    }

    @Test
    public void testPersonLevelNotIncludedReturnsFalse() {
        Person person = makePersonWithLevel(new Level(6, 8));
        MatchingLevelPredicate predicate = new MatchingLevelPredicate(List.of(new Level(1, 3)));
        assertFalse(predicate.test(person)); // 1–3 does not overlap 6–8
    }

    @Test
    public void testMultipleLevelsOneMatchesReturnsTrue() {
        Person person = makePersonWithLevel(new Level(3, 4));
        MatchingLevelPredicate predicate = new MatchingLevelPredicate(
                List.of(new Level(1, 2), new Level(3, 3), new Level(10, 12))
        );
        assertTrue(predicate.test(person)); // 3 matches
    }

    @Test
    public void testMultipleLevelsNoneMatchReturnsFalse() {
        Person person = makePersonWithLevel(new Level(10, 12));
        MatchingLevelPredicate predicate = new MatchingLevelPredicate(
                List.of(new Level(1, 3), new Level(4, 6))
        );
        assertFalse(predicate.test(person)); // no overlap
    }
}
