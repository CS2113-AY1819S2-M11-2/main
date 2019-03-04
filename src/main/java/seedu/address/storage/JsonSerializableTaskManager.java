package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.person.Task;

/**
 * An Immutable TaskManager that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableTaskManager {

    public static final String MESSAGE_DUPLICATE_TASK = "Tasks list contains duplicate task(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTaskManager} with the given tasks.
     */
    @JsonCreator
    public JsonSerializableTaskManager(@JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyTaskManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTaskManager}.
     */
    public JsonSerializableTaskManager(ReadOnlyTaskManager source) {
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code TaskManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TaskManager toModelType() throws IllegalValueException {
        TaskManager taskManager = new TaskManager();
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (taskManager.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            taskManager.addTask(task);
        }
        return taskManager;
    }

}
