package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedTaskManager extends TaskManager {

    private final List<ReadOnlyTaskManager> taskManagerStateList;
    private int currentStatePointer;

    public VersionedTaskManager(ReadOnlyTaskManager initialState) {
        super(initialState);

        taskManagerStateList = new ArrayList<>();
        taskManagerStateList.add(new TaskManager(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        taskManagerStateList.add(new TaskManager(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        taskManagerStateList.subList(currentStatePointer + 1, taskManagerStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(taskManagerStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(taskManagerStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < taskManagerStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTaskManager)) {
            return false;
        }

        VersionedTaskManager otherVersionedTaskManager = (VersionedTaskManager) other;

        // state check
        return super.equals(otherVersionedTaskManager)
                && taskManagerStateList.equals(otherVersionedTaskManager.taskManagerStateList)
                && currentStatePointer == otherVersionedTaskManager.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
