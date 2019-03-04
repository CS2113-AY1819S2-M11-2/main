package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Calendar;

/**
 * Represents a Task's date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date must be valid and should only be of the format DD-MM, where D and M are numbers.\n"
                    + "DD must range from 01 to 31 and MM must range from 01 to 12";
    public static final String VALIDATION_REGEX = "[\\d]{2}" + "-" + "[\\d]{2}";

    private static final int DAY_MIN = 1;
    private static final int DAY_MAX_FEB = 28;
    private static final int DAY_MAX_FEB_LEAP = 29;
    private static final int DAY_MAX_30 = 30;
    private static final int DAY_MAX_31 = 31;
    private static final int MONTH_MIN = 1;
    private static final int MONTH_MAX = 12;

    public final String value;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            String[] data = test.split("-");
            int day = Integer.parseInt(data[0]);
            int month = Integer.parseInt(data[1]);
            int year = Calendar.getInstance().get(Calendar.YEAR);

            if(month < MONTH_MIN || month > MONTH_MAX || day < DAY_MIN) {
                return false;
            } else if (month == 2) {
                if ( (year % 400 == 0) || ((year % 4 == 0 && year % 100 != 0))) {
                    if (day > DAY_MAX_FEB_LEAP) {
                        return false;
                    }
                    return true;
                } else {
                    if (day > DAY_MAX_FEB) {
                        return false;
                    }
                    return true;
                }
            } else if (month == 1 || month == 3 || month == 5 || month == 7
                    || month ==8 || month == 10 || month == 12) {
                if (day > DAY_MAX_31) {
                    return false;
                }
                return true;
            } else {
                if (day > DAY_MAX_30) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
