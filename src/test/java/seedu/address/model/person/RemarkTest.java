package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object = returns true
        assertTrue(remark.equals(remark));

        // same values = returns true
        assertTrue(remark.equals(new Remark(remark.value)));

        // diff types = returns false
        assertFalse(remark.equals(1));

        // null = returns false
        assertFalse(remark.equals(null));

        // different remark = returns false
        assertFalse(remark.equals(new Remark("Bye")));
    }
}
