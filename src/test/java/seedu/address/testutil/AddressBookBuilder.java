package seedu.address.testutil;

import seedu.address.model.TaskManager;
import seedu.address.model.person.Task;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TaskManager ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TaskManager taskManager;

    public AddressBookBuilder() {
        taskManager = new TaskManager();
    }

    public AddressBookBuilder(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * Adds a new {@code Task} to the {@code TaskManager} that we are building.
     */
    public AddressBookBuilder withPerson(Task task) {
        taskManager.addTask(task);
        return this;
    }

    public TaskManager build() {
        return taskManager;
    }
}
