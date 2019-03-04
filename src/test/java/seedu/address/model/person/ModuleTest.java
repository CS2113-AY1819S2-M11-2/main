package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ModuleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Module(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Module(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Module.isValidModule(null));

        // invalid phone numbers
        assertFalse(Module.isValidModule("")); // empty string
        assertFalse(Module.isValidModule(" ")); // spaces only
        assertFalse(Module.isValidModule("91")); // less than 3 numbers
        assertFalse(Module.isValidModule("phone")); // non-numeric
        assertFalse(Module.isValidModule("9011p041")); // alphabets within digits
        assertFalse(Module.isValidModule("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Module.isValidModule("911")); // exactly 3 numbers
        assertTrue(Module.isValidModule("93121534"));
        assertTrue(Module.isValidModule("124293842033123")); // long phone numbers
    }
}
