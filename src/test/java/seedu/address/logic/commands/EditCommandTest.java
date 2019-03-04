package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.model.TaskManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Task;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Task editedTask = new PersonBuilder().build();
        EditCommand.EditTaskDescriptor descriptor = new EditPersonDescriptorBuilder(editedTask).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);
        expectedModel.commitTaskManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastTask);
        Task editedTask = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditTaskDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());
        expectedModel.setTask(lastTask, editedTask);
        expectedModel.commitTaskManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditCommand.EditTaskDescriptor());
        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());
        expectedModel.commitTaskManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        Task editedTask = new PersonBuilder(taskInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);
        expectedModel.commitTaskManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Task firstTask = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand.EditTaskDescriptor descriptor = new EditPersonDescriptorBuilder(firstTask).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit task in filtered list into a duplicate in address book
        Task taskInList = model.getTaskManager().getTaskList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(taskInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditCommand.EditTaskDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskManager().getTaskList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Task editedTask = new PersonBuilder().build();
        Task taskToEdit = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand.EditTaskDescriptor descriptor = new EditPersonDescriptorBuilder(editedTask).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());
        expectedModel.setTask(taskToEdit, editedTask);
        expectedModel.commitTaskManager();

        // edit -> first task edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered task list to show all persons
        expectedModel.undoTaskManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.redoTaskManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditCommand.EditTaskDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Task} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited task in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the task object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Task editedTask = new PersonBuilder().build();
        EditCommand.EditTaskDescriptor descriptor = new EditPersonDescriptorBuilder(editedTask).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Task taskToEdit = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setTask(taskToEdit, editedTask);
        expectedModel.commitTaskManager();

        // edit -> edits second task in unfiltered task list / first task in filtered task list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered task list to show all persons
        expectedModel.undoTaskManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased()), taskToEdit);
        // redo -> edits same second task in unfiltered task list
        expectedModel.redoTaskManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
