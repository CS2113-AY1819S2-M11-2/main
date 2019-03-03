package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
 //   private final VersionedTaskManager versionedTaskManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
  //  private final FilteredList<seedu.address.model.task.Task> filteredTasks;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
 //   private final SimpleObjectProperty<seedu.address.model.task.Task> selectedTask = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
   //     versionedTaskManager = new VersionedTaskManager(taskManager);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
   //     filteredTasks = new FilteredList<>(versionedTaskManager.getTaskList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);
    }

    public ModelManager() { this(new AddressBook(), new UserPrefs()); }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

//    @Override
//    public Path getTaskManagerFilePath() { return userPrefs.getTaskManagerFilePath(); }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

//    @Override
//    public void setTaskManagerFilePath(Path taskManagerFilePath) {
//        requireNonNull(taskManagerFilePath);
//        userPrefs.setTaskManagerFilePath(taskManagerFilePath);
//    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

//    @Override
//    public void setTaskManager(ReadOnlyTaskManager taskManager) { versionedTaskManager.resetData(taskManager); }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

//    @Override
//    public ReadOnlyTaskManager getTaskManager() { return versionedTaskManager; }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

//    @Override
//    public boolean hasTask(seedu.address.model.task.Task task) {
//        requireNonNull(task);
//        return versionedTaskManager.hasTask(task);
//    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
    }

//    @Override
//    public void deleteTask (seedu.address.model.task.Task target) {versionedTaskManager.removeTask(target); }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

//    @Override
//    public void addTask(seedu.address.model.task.Task task) {
//        versionedTaskManager.addTask(task);
//        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
//    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.setPerson(target, editedPerson);
    }

//    @Override
//    public void setTask(seedu.address.model.task.Task target, seedu.address.model.task.Task editedTask) {
//        requireAllNonNull(target, editedTask);
//
//        versionedTaskManager.setTask(target, editedTask);
//    }
//    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

//    /**
//     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
//     * {@code versionTaskManager}
//     */
//    @Override
//    public ObservableList<Task> getFilteredTaskList() { return filteredTasks; }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

//    @Override
//    public void updateFilteredTaskList(Predicate<seedu.address.model.task.Task> predicate) {
//        requireNonNull(predicate);
//        filteredTasks.setPredicate(predicate);
//    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

//    @Override
//    public boolean canUndoTaskManager() {
//        return versionedTaskManager.canUndo();
//    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

//    @Override
//    public boolean canRedoTaskManager() {
//        return versionedTaskManager.canRedo();
//    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

//    @Override
//    public void undoTaskManager() {
//        versionedTaskManager.undo();
//    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

//    @Override
//    public void redoTaskManager() {
//        versionedTaskManager.redo();
//    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

//    @Override
//    public void commitTaskManager() {
//        versionedTaskManager.commit();
//    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

//    @Override
//    public ReadOnlyProperty<Task> selectedTaskProperty() {
//        return selectedTask;
//    }

    @Override
    public Person getSelectedPerson() {
        return selectedPerson.getValue();
    }

//    @Override
//    public Task getSelectedTask() {
//        return selectedTask.getValue();
//    }

    @Override
    public void setSelectedPerson(Person person) {
        if (person != null && !filteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

//    @Override
//    public void setSelectedTask(Task task) {
//        if (task != null && !filteredTasks.contains(task)) {
//            throw new TaskNotFoundException();
//        }
//        selectedTask.setValue(task);
//    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

//    private void ensureSelectedTaskIsValid(ListChangeListener.Change<? extends seedu.address.model.task.Task> change) {
//        while (change.next()) {
//            if (selectedTask.getValue() == null) {
//                //null is always a valid selcted task, so we do not ned to check that it is valid anymore.
//                return;
//            }
//
//            boolean wasSelectedTaskReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
//                    && change.getRemoved().contains(selectedTask.getValue());
//            if (wasSelectedTaskReplaced) {
//                //Update selectedTask to its new value.
//                int index = change.getRemoved().indexOf(selectedTask.getValue());
//                selectedTask.set(change.getAddedSubList().get(index));
//                continue;
//            }
//
//            boolean wasSelectedTaskRemoved = change.getRemoved().stream()
//                    .anyMatch(removedTask -> selectedTask.getValue().isSameTask(removedTask));
//            if (wasSelectedTaskRemoved) {
//                //Select the task that came before it in the list,
//                //or clear the selection if there is no such task.
//                selectedTask.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() -1) :null);
//            }
//        }
//    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
