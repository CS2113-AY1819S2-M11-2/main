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
        assertFalse(Module.isValidModule("2113CS")); // numbers before alphabet
        assertFalse(Module.isValidModule("C2113")); // missing one alphabet
        assertFalse(Module.isValidModule("CS211")); // missing one number

        // valid phone numbers
        assertTrue(Module.isValidModule("CS2113"));
        assertTrue(Module.isValidModule("CS2101"));
    }
}
