package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Priority(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid addresses
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only

        // valid addresses
        assertTrue(Priority.isValidPriority("Blk 456, Den Road, #01-355"));
        assertTrue(Priority.isValidPriority("-")); // one character
        assertTrue(Priority.isValidPriority("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
