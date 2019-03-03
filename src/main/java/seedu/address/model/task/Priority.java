//package seedu.address.model.task;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;
//
///**
// * Represents a Person's address in the address book.
// * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
// */
//public class Priority {
//
//    public static final String MESSAGE_CONSTRAINTS =
//            "Priority should only contain 1 number";
//
//    public static final String VALIDATION_REGEX = "[123]";
//
//    public final String value;
//
//    /**
//     * Constructs an {@code Priority}.
//     *
//     * @param priority A valid priority.
//     */
//    public Priority(String priority) {
//        requireNonNull(priority);
//        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
//        value = priority;
//    }
//
//    /**
//     * Returns true if a given string is a valid email.
//     */
//    public static boolean isValidPriority(String test) {
//        return test.matches(VALIDATION_REGEX);
//    }
//
//    @Override
//    public String toString() {
//        return value;
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof Priority // instanceof handles nulls
//                && value.equals(((Priority) other).value)); // state check
//    }
//
//    @Override
//    public int hashCode() {
//        return value.hashCode();
//    }
//
//}
