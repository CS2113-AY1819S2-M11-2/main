package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the TaskManger.
     *
     * @see Model#getTaskManager()
     */
    ReadOnlyTaskManager getTaskManager();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<Task> getFilteredTaskList();
    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user pref's task manager file path.
     */
    Path getTaskManagerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     *
     * @see seedu.address.model.Model#selectedPersonProperty()
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Selected task in the filtered task list.
     * null if no task is selected.
     *
     * @see Model#selectedTaskProperty()
     */
    ReadOnlyProperty<Task> selectedTaskProperty();

    /**
     * Sets the selected person in the filtered person list.
     *
     * @see seedu.address.model.Model#setSelectedPerson(Person)
     */
    void setSelectedPerson(Person person);

    /**
     * Sets the selected person in the filtered person list.
     *
     * @see Model#setSelectedTask(Task)
     */
    void setSelectedTask(Task task);
}
