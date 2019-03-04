package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's module code in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidModule(String)}
 */
public class Module {


    public static final String MESSAGE_CONSTRAINTS =
            "Module code should be of the format AAXXXX, where A is an alphabet and X is a number";
    public static final String VALIDATION_REGEX = "[a-zA-z]{2}[\\d]{4}";
    public final String value;

    /**
     * Constructs a {@code Module}.
     *
     * @param module A valid module code.
     */
    public Module(String module) {
        requireNonNull(module);
        checkArgument(isValidModule(module), MESSAGE_CONSTRAINTS);
        value = module;
    }

    /**
     * Returns true if a given string is a valid module code.
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
                || (other instanceof Module // instanceof handles nulls
                && value.equals(((Module) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
