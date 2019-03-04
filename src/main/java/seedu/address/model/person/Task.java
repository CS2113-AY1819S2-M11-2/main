package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Name name;
    private final Module module;
    private final Date date;

    // Data fields
    private final Priority priority;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Module module, Date date, Priority priority, Set<Tag> tags) {
        requireAllNonNull(name, module, date, priority, tags);
        this.name = name;
        this.module = module;
        this.date = date;
        this.priority = priority;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Module getModule() {
        return module;
    }

    public Date getDate() {
        return date;
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
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getName().equals(getName())
                && (otherTask.getModule().equals(getModule()) || otherTask.getDate().equals(getDate()));
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
        return otherTask.getName().equals(getName())
                && otherTask.getModule().equals(getModule())
                && otherTask.getDate().equals(getDate())
                && otherTask.getPriority().equals(getPriority())
                && otherTask.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, module, date, priority, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Module: ")
                .append(getModule())
                .append(" Date: ")
                .append(getDate())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
