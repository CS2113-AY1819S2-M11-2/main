package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskModule;
import seedu.address.model.task.TaskName;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String name;
    private final String module;
    private final String date;
    private final String priority;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("name") String name, @JsonProperty("module") String module,
                           @JsonProperty("date") String date, @JsonProperty("priority") String priority,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.module = module;
        this.date = date;
        this.priority = priority;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        name = source.getTaskName().fullName;
        module = source.getTaskModule().value;
        date = source.getTaskDate().value;
        priority = source.getPriority().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> TaskTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            TaskTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskName.class.getSimpleName()));
        }
        if (!TaskName.isValidName(name)) {
            throw new IllegalValueException(TaskName.MESSAGE_CONSTRAINTS);
        }
        final TaskName modelName = new TaskName(name);

        if (module == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Module.class.getSimpleName()));
        }
        if (!TaskModule.isValidModule(module)) {
            throw new IllegalValueException(TaskModule.MESSAGE_CONSTRAINTS);
        }
        final TaskModule modelModule = new TaskModule(module);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDate.class.getSimpleName()));
        }
        if (!TaskDate.isValidDate(date)) {
            throw new IllegalValueException(TaskDate.MESSAGE_CONSTRAINTS);
        }
        final TaskDate modelDate = new TaskDate(date);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(priority);

        final Set<Tag> modelTags = new HashSet<>(TaskTags);
        return new Task(modelName, modelModule, modelDate, modelPriority, modelTags);
    }

}
