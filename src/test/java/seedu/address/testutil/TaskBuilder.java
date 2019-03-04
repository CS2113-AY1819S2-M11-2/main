package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Priority;
import seedu.address.model.person.Date;
import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.person.Module;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Project";
    public static final String DEFAULT_MODULE = "CS2113";
    public static final String DEFAULT_DATE = "03-03";
    public static final String DEFAULT_PRIORITY = "1";

    private Name name;
    private Module module;
    private Date date;
    private Priority priority;
    private Set<Tag> tags;

    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        module = new Module(DEFAULT_MODULE);
        date = new Date(DEFAULT_DATE);
        priority = new Priority(DEFAULT_PRIORITY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        module = taskToCopy.getModule();
        date = taskToCopy.getDate();
        priority = taskToCopy.getPriority();
        tags = new HashSet<>(taskToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String address) {
        this.priority = new Priority(address);
        return this;
    }

    /**
     * Sets the {@code Module} of the {@code Task} that we are building.
     */
    public TaskBuilder withModule(String phone) {
        this.module = new Module(phone);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Task} that we are building.
     */
    public TaskBuilder withDate(String email) {
        this.date = new Date(email);
        return this;
    }

    public Task build() {
        return new Task(name, module, date, priority, tags);
    }

}
