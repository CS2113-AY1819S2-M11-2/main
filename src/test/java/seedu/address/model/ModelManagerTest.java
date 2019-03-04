package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

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
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

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
        assertFalse(modelManager.hasTask(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addTask(ALICE);
        assertTrue(modelManager.hasTask(ALICE));
    }

    @Test
    public void deletePerson_personIsSelectedAndFirstPersonInFilteredPersonList_selectionCleared() {
        modelManager.addTask(ALICE);
        modelManager.setSelectedTask(ALICE);
        modelManager.deleteTask(ALICE);
        assertEquals(null, modelManager.getSelectedTask());
    }

    @Test
    public void deletePerson_personIsSelectedAndSecondPersonInFilteredPersonList_firstPersonSelected() {
        modelManager.addTask(ALICE);
        modelManager.addTask(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredTaskList());
        modelManager.setSelectedTask(BOB);
        modelManager.deleteTask(BOB);
        assertEquals(ALICE, modelManager.getSelectedTask());
    }

    @Test
    public void setPerson_personIsSelected_selectedPersonUpdated() {
        modelManager.addTask(ALICE);
        modelManager.setSelectedTask(ALICE);
        Task updatedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setTask(ALICE, updatedAlice);
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
        modelManager.setSelectedTask(ALICE);
    }

    @Test
    public void setSelectedPerson_personInFilteredPersonList_setsSelectedPerson() {
        modelManager.addTask(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredTaskList());
        modelManager.setSelectedTask(ALICE);
        assertEquals(ALICE, modelManager.getSelectedTask());
    }

    @Test
    public void equals() {
        TaskManager taskManager = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
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
        String[] keywords = ALICE.getName().fullName.split("\\s+");
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
