package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LevelTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Level.parse(null));
    }

    @Test
    public void constructor_invalidLevel_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Level.parse(""));
        assertThrows(IllegalArgumentException.class, () -> Level.parse(" "));
        assertThrows(IllegalArgumentException.class, () -> Level.parse("-1"));
        assertThrows(IllegalArgumentException.class, () -> Level.parse("0"));
        assertThrows(IllegalArgumentException.class, () -> Level.parse("2-1")); // reversed range
        assertThrows(IllegalArgumentException.class, () -> Level.parse("1-0"));
        assertThrows(IllegalArgumentException.class, () -> Level.parse("abc"));
        assertThrows(IllegalArgumentException.class, () -> Level.parse("1-a"));
    }

    @Test
    public void isValidLevel() {
        // null level
        assertThrows(NullPointerException.class, () -> Level.isValidLevel(null));

        // invalid levels
        assertFalse(Level.isValidLevel("")); // empty string
        assertFalse(Level.isValidLevel(" ")); // spaces only
        assertFalse(Level.isValidLevel("-1")); // negative
        assertFalse(Level.isValidLevel("0")); // zero
        assertFalse(Level.isValidLevel("2-1")); // reversed range
        assertFalse(Level.isValidLevel("abc")); // non-numeric
        assertFalse(Level.isValidLevel("1-a")); // non-numeric range
        assertFalse(Level.isValidLevel("7")); // above max
        assertFalse(Level.isValidLevel("1-7")); // end above max
        assertFalse(Level.isValidLevel("0-3")); // start below min
        assertFalse(Level.isValidLevel("3-0")); // end below min
        assertFalse(Level.isValidLevel("6-7")); // end above max
        assertFalse(Level.isValidLevel("0-1")); // start below min

        // valid levels (only 1-6 allowed)
        assertTrue(Level.isValidLevel("1"));
        assertTrue(Level.isValidLevel("6"));
        assertTrue(Level.isValidLevel("1-2"));
        assertTrue(Level.isValidLevel("3-3"));
        assertTrue(Level.isValidLevel("5-6"));
        assertTrue(Level.isValidLevel("1 - 6")); // spaces around dash
    }

    @Test
    public void parse_validLevel_success() {
        Level single = Level.parse("3");
        assertTrue(single.isSingle());
        assertEquals("3", single.toString());

        Level range = Level.parse("1-6");
        assertFalse(range.isSingle());
        assertEquals("1-6", range.toString());
    }

    @Test
    public void includes_and_intersects() {
        Level tutorRange = Level.parse("1-3");
        Level studentSingle = Level.parse("2");
        Level studentRange = Level.parse("2-4");
        Level outside = Level.parse("4");

        // includes: tutorRange includes studentSingle
        assertTrue(tutorRange.includes(studentSingle));
        // includes: tutorRange does NOT include studentRange
        assertFalse(tutorRange.includes(studentRange));
        // intersects: tutorRange intersects studentRange
        assertTrue(tutorRange.intersects(studentRange));
        // intersects: tutorRange does NOT intersect outside
        assertFalse(tutorRange.intersects(outside));
    }

    @Test
    public void equals() {
        Level level = Level.parse("2-4");

        // same values -> returns true
        assertTrue(level.equals(Level.parse("2-4")));

        // same object -> returns true
        assertTrue(level.equals(level));

        // null -> returns false
        assertFalse(level.equals(null));

        // different types -> returns false
        assertFalse(level.equals(5.0f));

        // different values -> returns false
        assertFalse(level.equals(Level.parse("1-3")));
    }
}
