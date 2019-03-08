package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalTasks.LECTURE;
import static seedu.address.testutil.TypicalTasks.PROJECT;
import static seedu.address.testutil.TypicalTasks.TUTORIAL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TaskManagerBuilder;

public class VersionedTaskManagerTest {

    private final ReadOnlyTaskManager taskManagerWithProject = new TaskManagerBuilder().withTask(PROJECT).build();
    private final ReadOnlyTaskManager taskManagerWithTutorial = new TaskManagerBuilder().withTask(TUTORIAL).build();
    private final ReadOnlyTaskManager taskManagerWithLecture = new TaskManagerBuilder().withTask(LECTURE).build();
    private final ReadOnlyTaskManager emptyTaskManager = new TaskManagerBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedTaskManager versionedTaskManager = prepareTaskManagerList(emptyTaskManager);

        versionedTaskManager.commit();
        assertTaskManagerListStatus(versionedTaskManager,
                Collections.singletonList(emptyTaskManager),
                emptyTaskManager,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);

        versionedAddressBook.commit();
        assertTaskManagerListStatus(versionedAddressBook,
                Arrays.asList(emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial),
                taskManagerWithTutorial,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.commit();
        assertTaskManagerListStatus(versionedAddressBook,
                Collections.singletonList(emptyTaskManager),
                emptyTaskManager,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(emptyTaskManager);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(emptyTaskManager);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);

        versionedAddressBook.undo();
        assertTaskManagerListStatus(versionedAddressBook,
                Collections.singletonList(emptyTaskManager),
                taskManagerWithProject,
                Collections.singletonList(taskManagerWithTutorial));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.undo();
        assertTaskManagerListStatus(versionedAddressBook,
                Collections.emptyList(),
                emptyTaskManager,
                Arrays.asList(taskManagerWithProject, taskManagerWithTutorial));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(emptyTaskManager);

        assertThrows(VersionedTaskManager.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertThrows(VersionedTaskManager.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.redo();
        assertTaskManagerListStatus(versionedAddressBook,
                Arrays.asList(emptyTaskManager, taskManagerWithProject),
                taskManagerWithTutorial,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.redo();
        assertTaskManagerListStatus(versionedAddressBook,
                Collections.singletonList(emptyTaskManager),
                taskManagerWithProject,
                Collections.singletonList(taskManagerWithTutorial));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(emptyTaskManager);

        assertThrows(VersionedTaskManager.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTaskManager versionedAddressBook = prepareTaskManagerList(
                emptyTaskManager, taskManagerWithProject, taskManagerWithTutorial);

        assertThrows(VersionedTaskManager.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void equals() {
        VersionedTaskManager versionedAddressBook;
        versionedAddressBook = prepareTaskManagerList(taskManagerWithProject, taskManagerWithTutorial);

        // same values -> returns true
        VersionedTaskManager copy = prepareTaskManagerList(taskManagerWithProject, taskManagerWithTutorial);
        assertTrue(versionedAddressBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedAddressBook.equals(versionedAddressBook));

        // null -> returns false
        assertFalse(versionedAddressBook.equals(null));

        // different types -> returns false
        assertFalse(versionedAddressBook.equals(1));

        // different state list -> returns false
        VersionedTaskManager differentAddressBookList;
        differentAddressBookList = prepareTaskManagerList(taskManagerWithTutorial, taskManagerWithLecture);
        assertFalse(versionedAddressBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedTaskManager differentCurrentStatePointer = prepareTaskManagerList(
                taskManagerWithProject, taskManagerWithTutorial);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);
        assertFalse(versionedAddressBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedAddressBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertTaskManagerListStatus(VersionedTaskManager versionedAddressBook,
                                             List<ReadOnlyTaskManager> expectedStatesBeforePointer,
                                             ReadOnlyTaskManager expectedCurrentState,
                                             List<ReadOnlyTaskManager> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TaskManager(versionedAddressBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedAddressBook.canUndo()) {
            versionedAddressBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTaskManager expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new TaskManager(versionedAddressBook));
            versionedAddressBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTaskManager expectedAddressBook : expectedStatesAfterPointer) {
            versionedAddressBook.redo();
            assertEquals(expectedAddressBook, new TaskManager(versionedAddressBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedAddressBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedAddressBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedTaskManager} with the {@code addressBookStates} added into it, and the
     * {@code VersionedTaskManager#currentStatePointer} at the end of list.
     */
    private VersionedTaskManager prepareTaskManagerList(ReadOnlyTaskManager... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedTaskManager versionedAddressBook = new VersionedTaskManager(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedAddressBook.resetData(addressBookStates[i]);
            versionedAddressBook.commit();
        }

        return versionedAddressBook;
    }

    /**
     * Shifts the {@code versionedAddressBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTaskManager versionedAddressBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedAddressBook.undo();
        }
    }
}
