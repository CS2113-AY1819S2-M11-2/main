package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the priority book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final TaskName taskName;
    private final TaskModule taskModule;

    // Data fields
    private final TaskDate taskDate;
    private final Priority priority;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, TaskModule taskModule, TaskDate taskDate, Priority priority, Set<Tag> tags) {
        requireAllNonNull(taskName, taskModule, taskDate, priority, tags);
        this.taskName = taskName;
        this.taskModule = taskModule;
        this.taskDate = taskDate;
        this.priority = priority;
        this.tags.addAll(tags);
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public TaskModule getTaskModule() {
        return taskModule;
    }

    public TaskDate getTaskDate() {
        return taskDate;
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same taskName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getTaskName().equals(getTaskName())
                && (otherTask.getTaskModule().equals(getTaskModule()) || otherTask.getTaskDate().equals(getTaskDate()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getTaskName().equals(getTaskName())
                && otherTask.getTaskModule().equals(getTaskModule())
                && otherTask.getTaskDate().equals(getTaskDate())
                && otherTask.getPriority().equals(getPriority())
                && otherTask.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, taskModule, taskDate, priority, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" TaskModule: ")
                .append(getTaskModule())
                .append(" TaskDate: ")
                .append(getTaskDate())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
