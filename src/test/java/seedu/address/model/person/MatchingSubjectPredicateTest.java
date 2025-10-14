package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MatchingSubjectPredicate}.
 */
public class MatchingSubjectPredicateTest {

    private Person makePersonWithSubject(Subject subject) {
        return new Person(
                "tutor",
                new Name("Alice"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123 Street"),
                subject,
                new Level(1, 3),
                new Price(30, 50),
                Collections.emptySet()
        );
    }

    @Test
    public void equals() {
        List<Subject> subjectsA = List.of(new Subject("Math"));
        List<Subject> subjectsB = List.of(new Subject("English"));

        MatchingSubjectPredicate predicateA = new MatchingSubjectPredicate(subjectsA);
        MatchingSubjectPredicate predicateB = new MatchingSubjectPredicate(subjectsB);

        // same object
        assertTrue(predicateA.equals(predicateA));

        // same values
        assertTrue(predicateA.equals(new MatchingSubjectPredicate(List.of(new Subject("Math")))));

        // different type
        assertFalse(predicateA.equals("String"));

        // null
        assertFalse(predicateA.equals(null));

        // different subject list
        assertFalse(predicateA.equals(predicateB));
    }

    @Test
    public void testPersonHasMatchingSubjectReturnsTrue() {
        Person person = makePersonWithSubject(new Subject("Math"));
        MatchingSubjectPredicate predicate = new MatchingSubjectPredicate(List.of(new Subject("Math")));
        assertTrue(predicate.test(person)); // subject matches exactly
    }
    @Test
    public void testPersonDifferentSubjectReturnsFalse() {
        Person person = makePersonWithSubject(new Subject("Math"));
        MatchingSubjectPredicate predicate = new MatchingSubjectPredicate(List.of(new Subject("Science")));
        assertFalse(predicate.test(person)); // subject doesn't match
    }

    @Test
    public void testMultipleSubjectsOneMatchesReturnsTrue() {
        Person person = makePersonWithSubject(new Subject("English"));
        MatchingSubjectPredicate predicate = new MatchingSubjectPredicate(
                List.of(new Subject("Math"), new Subject("English"), new Subject("Physics"))
        );
        assertTrue(predicate.test(person)); // English matches
    }

    @Test
    public void testMultipleSubjectsNoneMatchReturnsFalse() {
        Person person = makePersonWithSubject(new Subject("Economics"));
        MatchingSubjectPredicate predicate = new MatchingSubjectPredicate(
                List.of(new Subject("Math"), new Subject("Science"), new Subject("Physics"))
        );
        assertFalse(predicate.test(person)); // no subject match
    }
}
