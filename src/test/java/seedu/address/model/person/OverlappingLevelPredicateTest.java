package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link OverlappingLevelPredicate}.
 */
public class OverlappingLevelPredicateTest {

    private Person makePersonWithLevel(Level level) {
        return new Person(
                "tutor",
                new Name("Alice"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123 Street"),
                new Subject("Math"),
                level,
                new Price(10, 10),
                Collections.emptySet()
        );
    }

    @Test
    public void equals() {
        List<Level> levelsA = List.of(new Level(1, 3));
        List<Level> levelsB = List.of(new Level(4, 6));

        OverlappingLevelPredicate predicateA = new OverlappingLevelPredicate(levelsA);
        OverlappingLevelPredicate predicateB = new OverlappingLevelPredicate(levelsB);

        // same object
        assertTrue(predicateA.equals(predicateA));

        // same values
        assertTrue(predicateA.equals(new OverlappingLevelPredicate(List.of(new Level(1, 3)))));

        // different type
        assertFalse(predicateA.equals("String"));

        // null
        assertFalse(predicateA.equals(null));

        // different values
        assertFalse(predicateA.equals(predicateB));
    }

    @Test
    public void testAnyOverlapReturnsTrue() {
        Person person = makePersonWithLevel(new Level(2, 2)); // person's range 2–2
        OverlappingLevelPredicate predicate = new OverlappingLevelPredicate(
                List.of(new Level(1, 3), new Level(5, 6))
        );
        assertTrue(predicate.test(person)); // 2 overlaps with 1–3
    }

    @Test
    public void testNoOverlapReturnsFalse() {
        Person person = makePersonWithLevel(new Level(3, 4));
        OverlappingLevelPredicate predicate = new OverlappingLevelPredicate(
                List.of(new Level(1, 2), new Level(5, 6))
        );
        assertFalse(predicate.test(person));
    }

    @Test
    public void testMultipleLevelRangesOneMatchesReturnsTrue() {
        Person person = makePersonWithLevel(new Level(4, 4));
        OverlappingLevelPredicate predicate = new OverlappingLevelPredicate(
                List.of(new Level(1, 2), new Level(3, 5), new Level(5, 6))
        );
        assertTrue(predicate.test(person)); // 4 is within 3–5
    }
}
