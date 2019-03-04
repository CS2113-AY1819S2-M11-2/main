package seedu.address.model;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUTORIAL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.testutil.TypicalTasks.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Task;
import seedu.address.model.person.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskManagerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TaskManager(), new TaskManager(modelManager.getTaskManager()));
        assertEquals(null, modelManager.getSelectedTask());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTaskManagerFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTaskManagerFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setTaskManagerFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setTaskManagerFilePath(path);
        assertEquals(path, modelManager.getTaskManagerFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasTask(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasTask(SLIDES));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addTask(SLIDES);
        assertTrue(modelManager.hasTask(SLIDES));
    }

    @Test
    public void deletePerson_personIsSelectedAndFirstPersonInFilteredPersonList_selectionCleared() {
        modelManager.addTask(SLIDES);
        modelManager.setSelectedTask(SLIDES);
        modelManager.deleteTask(SLIDES);
        assertEquals(null, modelManager.getSelectedTask());
    }

    @Test
    public void deletePerson_personIsSelectedAndSecondPersonInFilteredPersonList_firstPersonSelected() {
        modelManager.addTask(SLIDES);
        modelManager.addTask(TUTORIAL);
        assertEquals(Arrays.asList(SLIDES, TUTORIAL), modelManager.getFilteredTaskList());
        modelManager.setSelectedTask(TUTORIAL);
        modelManager.deleteTask(TUTORIAL);
        assertEquals(SLIDES, modelManager.getSelectedTask());
    }

    @Test
    public void setPerson_personIsSelected_selectedPersonUpdated() {
        modelManager.addTask(SLIDES);
        modelManager.setSelectedTask(SLIDES);
        Task updatedAlice = new TaskBuilder(SLIDES).withDate(VALID_DATE_TUTORIAL).build();
        modelManager.setTask(SLIDES, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedTask());
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredTaskList().remove(0);
    }

    @Test
    public void setSelectedPerson_personNotInFilteredPersonList_throwsPersonNotFoundException() {
        thrown.expect(TaskNotFoundException.class);
        modelManager.setSelectedTask(SLIDES);
    }

    @Test
    public void setSelectedPerson_personInFilteredPersonList_setsSelectedPerson() {
        modelManager.addTask(SLIDES);
        assertEquals(Collections.singletonList(SLIDES), modelManager.getFilteredTaskList());
        modelManager.setSelectedTask(SLIDES);
        assertEquals(SLIDES, modelManager.getSelectedTask());
    }

    @Test
    public void equals() {
        TaskManager taskManager = new TaskManagerBuilder().withTask(SLIDES).withTask(LAB).build();
        TaskManager differentTaskManager = new TaskManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(taskManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(taskManager, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different taskManager -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTaskManager, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = SLIDES.getName().fullName.split("\\s+");
        modelManager.updateFilteredTaskList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(taskManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTaskManagerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(taskManager, differentUserPrefs)));
    }
}
