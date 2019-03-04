package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Task;
import seedu.address.model.person.exceptions.DuplicateTaskException;
import seedu.address.testutil.PersonBuilder;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        TaskManager newData = getTypicalAddressBook();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two tasks with the same identity fields
        Task editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Task> newTasks = Arrays.asList(ALICE, editedAlice);
        TaskManagerStub newData = new TaskManagerStub(newTasks);

        thrown.expect(DuplicateTaskException.class);
        taskManager.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskManager.hasTask(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(taskManager.hasTask(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        taskManager.addTask(ALICE);
        assertTrue(taskManager.hasTask(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        taskManager.addTask(ALICE);
        Task editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(taskManager.hasTask(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        taskManager.getTaskList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        taskManager.addListener(listener);
        taskManager.addTask(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        taskManager.addListener(listener);
        taskManager.removeListener(listener);
        taskManager.addTask(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks list can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TaskManagerStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
