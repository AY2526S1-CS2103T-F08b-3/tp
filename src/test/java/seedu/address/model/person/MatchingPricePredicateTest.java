package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MatchingPricePredicate}.
 */
public class MatchingPricePredicateTest {

    private Person makePersonWithPrice(Price price) {
        return new Person(
                "tutor",
                new Name("Alice"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123 Street"),
                new Subject("Math"),
                new Level(1, 3),
                price,
                Collections.emptySet()
        );
    }

    @Test
    public void equals() {
        List<Price> pricesA = List.of(new Price(20, 40));
        List<Price> pricesB = List.of(new Price(50, 80));

        MatchingPricePredicate predicateA = new MatchingPricePredicate(pricesA);
        MatchingPricePredicate predicateB = new MatchingPricePredicate(pricesB);

        // same object
        assertTrue(predicateA.equals(predicateA));

        // same values
        assertTrue(predicateA.equals(new MatchingPricePredicate(List.of(new Price(20, 40)))));

        // different type
        assertFalse(predicateA.equals("String"));

        // null
        assertFalse(predicateA.equals(null));

        // different values
        assertFalse(predicateA.equals(predicateB));
    }

    @Test
    public void testPersonPriceWithinRangeReturnsTrue() {
        Person person = makePersonWithPrice(new Price(30, 30)); // exact value
        MatchingPricePredicate predicate = new MatchingPricePredicate(List.of(new Price(20, 40)));
        assertTrue(predicate.test(person)); // 30 within 20–40
    }

    @Test
    public void testPersonPriceBelowRangeReturnsFalse() {
        Person person = makePersonWithPrice(new Price(10, 10)); // too low
        MatchingPricePredicate predicate = new MatchingPricePredicate(List.of(new Price(20, 40)));
        assertFalse(predicate.test(person));
    }

    @Test
    public void testPersonPriceAboveRangeReturnsFalse() {
        Person person = makePersonWithPrice(new Price(50, 50)); // too high
        MatchingPricePredicate predicate = new MatchingPricePredicate(List.of(new Price(10, 40)));
        assertFalse(predicate.test(person));
    }

    @Test
    public void testMultiplePriceRangesOneMatchesReturnsTrue() {
        Person person = makePersonWithPrice(new Price(30, 30));
        MatchingPricePredicate predicate = new MatchingPricePredicate(
                List.of(new Price(10, 20), new Price(25, 35), new Price(50, 80))
        );
        assertTrue(predicate.test(person)); // 30 is within 25–35
    }

    @Test
    public void testMultiplePriceRangesNoneMatchReturnsFalse() {
        Person person = makePersonWithPrice(new Price(90, 90));
        MatchingPricePredicate predicate = new MatchingPricePredicate(
                List.of(new Price(10, 20), new Price(25, 35), new Price(50, 80))
        );
        assertFalse(predicate.test(person)); // 90 above all
    }
}
