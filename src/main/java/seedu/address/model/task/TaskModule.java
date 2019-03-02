package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidModule(String)}
 */
public class TaskModule {


    public static final String MESSAGE_CONSTRAINTS =
            "TaskModule code should be of the format MMXXXX, where M is an alpahabet and X is a number";
    public static final String VALIDATION_REGEX = "[a-zA-Z]{2}[\\d]{4}";
    public final String value;

    /**
     * Constructs a {@code TaskModule}.
     *
     * @param module A valid module.
     */
    public TaskModule(String module) {
        requireNonNull(module);
        checkArgument(isValidModule(module), MESSAGE_CONSTRAINTS);
        value = module;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidModule(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskModule // instanceof handles nulls
                && value.equals(((TaskModule) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
