package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.address.testutil.TypicalTasks.SLIDES;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

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
import seedu.address.model.notes.Notes;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;

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
        TaskManager newData = getTypicalTaskManager();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two tasks with the same identity fields
        Task editedAlice = new TaskBuilder(SLIDES).withName("Slides")
                .withPriority("1").withDate("03-03")
                .withModule("CS2113")
                .withTags("ungraded").build();
        List<Task> newTasks = Arrays.asList(SLIDES, editedAlice);
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
        assertFalse(taskManager.hasTask(SLIDES));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        taskManager.addTask(SLIDES);
        assertTrue(taskManager.hasTask(SLIDES));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        taskManager.addTask(SLIDES);
        Task editedAlice = new TaskBuilder(SLIDES).withName("Slides")
                .withPriority("1").withDate("03-03")
                .withModule("CS2113")
                .withTags("ungraded").build();
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
        taskManager.addTask(SLIDES);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        taskManager.addListener(listener);
        taskManager.removeListener(listener);
        taskManager.addTask(SLIDES);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks list can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Notes> notes = FXCollections.observableArrayList();

        TaskManagerStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Notes> getNotesList() {
            return notes;
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
