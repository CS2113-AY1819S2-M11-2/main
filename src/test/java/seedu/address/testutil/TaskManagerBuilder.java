package seedu.address.testutil;

import seedu.address.model.TaskManager;
import seedu.address.model.person.Task;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code TaskManager ab = new TaskManagerBuilder().withTask("John", "Doe").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder() {
        taskManager = new TaskManager();
    }

    public TaskManagerBuilder(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Adds a new {@code Task} to the {@code TaskManager} that we are building.
     */
    public TaskManagerBuilder withTask(Task task) {
        taskManager.addTask(task);
        return this;
    }

    public TaskManager build() {
        return taskManager;
    }
}
