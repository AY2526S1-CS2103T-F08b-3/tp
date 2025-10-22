package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link OverlappingPricePredicate}.
 */
public class OverlappingPricePredicateTest {

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

        OverlappingPricePredicate predicateA = new OverlappingPricePredicate(pricesA);
        OverlappingPricePredicate predicateB = new OverlappingPricePredicate(pricesB);

        // same object
        assertTrue(predicateA.equals(predicateA));

        // same values
        assertTrue(predicateA.equals(new OverlappingPricePredicate(List.of(new Price(20, 40)))));

        // different type
        assertFalse(predicateA.equals("String"));

        // null
        assertFalse(predicateA.equals(null));

        // different values
        assertFalse(predicateA.equals(predicateB));
    }

    @Test
    public void testAnyOverlapReturnsTrue() {
        Person person = makePersonWithPrice(new Price(30, 30)); // person's range 30–30
        OverlappingPricePredicate predicate = new OverlappingPricePredicate(
                List.of(new Price(25, 35), new Price(50, 60))
        );
        assertTrue(predicate.test(person)); // 30 overlaps with 25–35
    }

    @Test
    public void testNoOverlapReturnsFalse() {
        Person person = makePersonWithPrice(new Price(10, 10)); // person's range 10–10
        OverlappingPricePredicate predicate = new OverlappingPricePredicate(
                List.of(new Price(20, 30), new Price(40, 50))
        );
        assertFalse(predicate.test(person)); // 10 does not overlap any provided ranges
    }
}
