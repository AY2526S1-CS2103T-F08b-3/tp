package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Price.parse(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        // Bug fix #1: Empty strings should throw exception
        assertThrows(IllegalArgumentException.class, () -> Price.parse(""));
        assertThrows(IllegalArgumentException.class, () -> Price.parse(" "));

        // Bug fix #2: Reversed ranges should throw exception
        assertThrows(IllegalArgumentException.class, () -> Price.parse("50-20"));

        // Negative prices
        assertThrows(IllegalArgumentException.class, () -> Price.parse("-1"));
        assertThrows(IllegalArgumentException.class, () -> Price.parse("10--5"));

        // Invalid formats
        assertThrows(IllegalArgumentException.class, () -> Price.parse("abc"));
        assertThrows(IllegalArgumentException.class, () -> Price.parse("10-a"));
        assertThrows(IllegalArgumentException.class, () -> Price.parse("a-20"));
    }

    @Test
    public void isValidPrice() {
        // null price
        assertFalse(Price.isValidPrice(null));

        // Bug fix #1: Empty strings should return false
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only

        // Bug fix #2 & #5: Reversed ranges should return false
        assertFalse(Price.isValidPrice("50-20")); // reversed range
        assertFalse(Price.isValidPrice("100-50")); // reversed range

        // invalid prices
        assertFalse(Price.isValidPrice("0")); // zero is invalid (must be positive)
        assertFalse(Price.isValidPrice("-1")); // negative
        assertFalse(Price.isValidPrice("abc")); // non-numeric
        assertFalse(Price.isValidPrice("10-a")); // non-numeric range
        assertFalse(Price.isValidPrice("a-20")); // non-numeric range
        assertFalse(Price.isValidPrice("10 20")); // space instead of dash
        assertFalse(Price.isValidPrice("10--20")); // double dash

        // valid prices
        assertTrue(Price.isValidPrice("1")); // minimum valid
        assertTrue(Price.isValidPrice("10")); // single value
        assertTrue(Price.isValidPrice("35")); // single value
        assertTrue(Price.isValidPrice("10-20")); // range
        assertTrue(Price.isValidPrice("30-45")); // range
        assertTrue(Price.isValidPrice("20-20")); // same min and max
        assertTrue(Price.isValidPrice("10 - 20")); // spaces around dash
        assertTrue(Price.isValidPrice("100-200")); // large values
    }

    @Test
    public void parse_validPrice_success() {
        // Single value
        Price single = Price.parse("35");
        assertTrue(single.isSingle());
        assertEquals("35", single.toString());

        // Range
        Price range = Price.parse("30-45");
        assertFalse(range.isSingle());
        assertEquals("30-45", range.toString());

        // Spaces around dash
        Price rangeWithSpaces = Price.parse("10 - 20");
        assertEquals("10-20", rangeWithSpaces.toString());

        // Same min and max
        Price sameRange = Price.parse("20-20");
        assertTrue(sameRange.isSingle());
        assertEquals("20", sameRange.toString());
    }

    @Test
    public void includes_tests() {
        Price tutorRange = Price.parse("30-50");
        Price studentBudget = Price.parse("35-40");
        Price outsideBudget = Price.parse("60-70");
        Price partialOverlap = Price.parse("45-60");

        // tutorRange includes studentBudget (30-50 includes 35-40)
        assertTrue(tutorRange.includes(studentBudget));

        // tutorRange does NOT include outsideBudget
        assertFalse(tutorRange.includes(outsideBudget));

        // tutorRange does NOT include partialOverlap (45-60 extends beyond 50)
        assertFalse(tutorRange.includes(partialOverlap));
    }

    @Test
    public void overlaps_tests() {
        Price range1 = Price.parse("20-40");
        Price range2 = Price.parse("30-50");
        Price range3 = Price.parse("60-80");
        Price exactMatch = Price.parse("20-40");

        // range1 overlaps range2 (30-40 is common)
        assertTrue(range1.overlaps(range2));
        assertTrue(range2.overlaps(range1));

        // range1 does NOT overlap range3 (no common values)
        assertFalse(range1.overlaps(range3));
        assertFalse(range3.overlaps(range1));

        // exactMatch overlaps range1 completely
        assertTrue(range1.overlaps(exactMatch));
        assertTrue(exactMatch.overlaps(range1));
    }

    @Test
    public void includesSingle_tests() {
        Price range = Price.parse("20-40");

        // Values within range
        assertTrue(range.includesSingle(20)); // lower bound
        assertTrue(range.includesSingle(30)); // middle
        assertTrue(range.includesSingle(40)); // upper bound

        // Values outside range
        assertFalse(range.includesSingle(19)); // just below
        assertFalse(range.includesSingle(41)); // just above
        assertFalse(range.includesSingle(0)); // far below
        assertFalse(range.includesSingle(100)); // far above
    }

    @Test
    public void getAveragePrice_tests() {
        // Single value
        Price single = Price.parse("30");
        Price avgSingle = single.getAveragePrice();
        assertEquals("30", avgSingle.toString());

        // Range with even average
        Price evenRange = Price.parse("20-40");
        Price avgEven = evenRange.getAveragePrice();
        assertEquals("30", avgEven.toString());

        // Range with odd average (integer division)
        Price oddRange = Price.parse("20-41");
        Price avgOdd = oddRange.getAveragePrice();
        assertEquals("30", avgOdd.toString()); // (20+41)/2 = 30 (integer division)
    }

    @Test
    public void equals() {
        Price price = Price.parse("20-40");

        // same values -> returns true
        assertTrue(price.equals(Price.parse("20-40")));

        // same object -> returns true
        assertTrue(price.equals(price));

        // null -> returns false
        assertFalse(price.equals(null));

        // different types -> returns false
        assertFalse(price.equals(5.0f));

        // different values -> returns false
        assertFalse(price.equals(Price.parse("30-50")));
        assertFalse(price.equals(Price.parse("20-41")));
        assertFalse(price.equals(Price.parse("19-40")));
    }

    @Test
    public void hashCode_tests() {
        Price price1 = Price.parse("20-40");
        Price price2 = Price.parse("20-40");
        Price price3 = Price.parse("30-50");

        // same values should have same hash code
        assertEquals(price1.hashCode(), price2.hashCode());

        // different values will likely have different hash codes
        // (not guaranteed, but highly likely)
        assertTrue(price1.hashCode() != price3.hashCode());
    }

    @Test
    public void toString_tests() {
        // Single value
        assertEquals("30", Price.parse("30").toString());

        // Range
        assertEquals("20-40", Price.parse("20-40").toString());

        // Range that becomes single (same min and max)
        assertEquals("25", Price.parse("25-25").toString());
    }
}
