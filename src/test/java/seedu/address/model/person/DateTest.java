package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidEmail));
    }

    @Test
    public void isValidDate() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank email
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidDate("0110")); // missing '-' symbol

        // invalid parts
        assertFalse(Date.isValidDate("01-30")); // month greater than 12
        assertFalse(Date.isValidDate("01-00")); // month less than 01
        assertFalse(Date.isValidDate("32-01")); // date greater than 31 for month with 31 days
        assertFalse(Date.isValidDate("31-04")); // date greater than 30 for month with 30 days
        assertFalse(Date.isValidDate("30-02")); // date greater than 28 for Feb
        assertFalse(Date.isValidDate("00-10")); // date less than 01

        // valid date
        assertTrue(Date.isValidDate("01-01"));
        assertTrue(Date.isValidDate("31-01"));
        assertTrue(Date.isValidDate("30-04"));
        assertTrue(Date.isValidDate("28-02"));
    }
}
