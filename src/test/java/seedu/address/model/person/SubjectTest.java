package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        // null subject
        assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subjects
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only
        assertFalse(Subject.isValidSubject("123")); // numbers not allowed
        assertFalse(Subject.isValidSubject("Math#")); // special character not allowed
        assertFalse(Subject.isValidSubject("Sci3nce")); // contains digits

        // valid subjects
        assertTrue(Subject.isValidSubject("mathematics"));
        assertTrue(Subject.isValidSubject("english"));
        assertTrue(Subject.isValidSubject("science"));
    }

    @Test
    public void equals() {
        Subject math = new Subject("mathematics");

        // same values -> returns true
        assertTrue(math.equals(new Subject("mathematics")));

        // same object -> returns true
        assertTrue(math.equals(math));

        // null -> returns false
        assertFalse(math.equals(null));

        // different types -> returns false
        assertFalse(math.equals(5.0f));

        // different values -> returns false
        assertFalse(math.equals(new Subject("English")));
    }

    @Test
    public void toString_returnsSubjectString() {
        Subject subject = new Subject("Mathematics");
        assertTrue(subject.toString().equals("Mathematics"));
    }

    @Test
    public void hashCode_sameSubject_sameHash() {
        Subject s1 = new Subject("english");
        Subject s2 = new Subject("english");
        assertTrue(s1.hashCode() == s2.hashCode());
    }
}
