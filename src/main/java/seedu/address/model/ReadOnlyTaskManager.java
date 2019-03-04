package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.person.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Task> getTaskList();

}
