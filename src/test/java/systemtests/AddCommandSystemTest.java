//package systemtests;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PROJECT;
//import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_TUTORIAL;
//import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_PROJECT;
//import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_TUTORIAL;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PROJECT;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_TUTORIAL;
//import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_PROJECT;
//import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_TUTORIAL;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GRADED;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_UNGRADED;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUTORIAL;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TUTORIAL;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_TUTORIAL;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.testutil.TypicalTasks.SLIDES;
//import static seedu.address.testutil.TypicalTasks.PROJECT;
//import static seedu.address.testutil.TypicalTasks.TUTORIAL;
//import static seedu.address.testutil.TypicalTasks.LECTURE;
//import static seedu.address.testutil.TypicalTasks.PRESENTATION;
//import static seedu.address.testutil.TypicalTasks.SEMINAR;
//import static seedu.address.testutil.TypicalTasks.KEYWORD_MATCHING_MEIER;
//
//import org.junit.Test;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
//import seedu.address.model.Model;
//import seedu.address.model.person.Priority;
//import seedu.address.model.person.Date;
//import seedu.address.model.person.Module;
//import seedu.address.model.person.Name;
//import seedu.address.model.person.Task;
//import seedu.address.model.tag.Tag;
//import seedu.address.testutil.TaskBuilder;
//import seedu.address.testutil.PersonUtil;
//
//public class AddCommandSystemTest extends TaskManagerSystemTest {
//
//    @Test
//    public void add() {
//        Model model = getModel();
//
//        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */
//
//        /* Case: add a task without tags to a non-empty address book, command with leading spaces and trailing spaces
//         * -> added
//         */
//        Task toAdd = PROJECT;
//        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_PROJECT + "  " + MODULE_DESC_PROJECT + " "
//                + DATE_DESC_PROJECT + "   " + PRIORITY_DESC_PROJECT + "   " + TAG_DESC_GRADED + " ";
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: undo adding Project to the list -> Project deleted */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: redo adding Project to the list -> Project added again */
//        command = RedoCommand.COMMAND_WORD;
//        model.addTask(toAdd);
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: add a task with all fields same as another task in the task manager except name -> added */
//        toAdd = new TaskBuilder(PROJECT).withName(VALID_NAME_TUTORIAL).build();
//        command = AddCommand.COMMAND_WORD + NAME_DESC_TUTORIAL + MODULE_DESC_PROJECT + DATE_DESC_PROJECT + PRIORITY_DESC_PROJECT
//                + TAG_DESC_GRADED;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a task with all fields same as another task in the task manager except phone and email
//         * -> added
//         */
//        toAdd = new TaskBuilder(PROJECT).withModule(VALID_MODULE_TUTORIAL).withDate(VALID_DATE_TUTORIAL).build();
//        command = PersonUtil.getAddCommand(toAdd);
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add to empty task manager -> added */
//        deleteAllPersons();
//        assertCommandSuccess(SLIDES);
//
//        /* Case: add a task with tags, command with parameters in random order -> added */
//        toAdd = TUTORIAL;
//        command = AddCommand.COMMAND_WORD + TAG_DESC_GRADED + MODULE_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL + NAME_DESC_TUTORIAL
//                + TAG_DESC_UNGRADED + DATE_DESC_TUTORIAL;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a task, missing tags -> added */
//        assertCommandSuccess(PRESENTATION);
//
//        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */
//
//        /* Case: filters the task list before adding -> added */
//        showPersonsWithName(KEYWORD_MATCHING_MEIER);
//        assertCommandSuccess(SEMINAR);
//
//        /* ------------------------ Perform add operation while a task card is selected --------------------------- */
//
//        /* Case: selects first card in the task list, add a task -> added, card selection remains unchanged */
//        selectPerson(Index.fromOneBased(1));
//        assertCommandSuccess(LECTURE);
//
//        /* ----------------------------------- Perform invalid add operations --------------------------------------- */
//
//        /* Case: add a duplicate task -> rejected */
//        command = PersonUtil.getAddCommand(PRESENTATION);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TASK);
//
//        /* Case: add a duplicate task except with different phone -> rejected */
//        toAdd = new TaskBuilder(PRESENTATION).withModule(VALID_MODULE_TUTORIAL).build();
//        command = PersonUtil.getAddCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TASK);
//
//        /* Case: add a duplicate task except with different email -> rejected */
//        toAdd = new TaskBuilder(PRESENTATION).withDate(VALID_DATE_TUTORIAL).build();
//        command = PersonUtil.getAddCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TASK);
//
//        /* Case: add a duplicate task except with different address -> rejected */
//        toAdd = new TaskBuilder(PRESENTATION).withPriority(VALID_PRIORITY_TUTORIAL).build();
//        command = PersonUtil.getAddCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TASK);
//
//        /* Case: add a duplicate task except with different tags -> rejected */
//        command = PersonUtil.getAddCommand(PRESENTATION) + " " + PREFIX_TAG.getPrefix() + "friends";
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TASK);
//
//        /* Case: missing name -> rejected */
//        command = AddCommand.COMMAND_WORD + MODULE_DESC_PROJECT + DATE_DESC_PROJECT + PRIORITY_DESC_PROJECT;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing phone -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_PROJECT + DATE_DESC_PROJECT + PRIORITY_DESC_PROJECT;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing email -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_PROJECT + MODULE_DESC_PROJECT + PRIORITY_DESC_PROJECT;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing address -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_PROJECT + MODULE_DESC_PROJECT + DATE_DESC_PROJECT;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: invalid keyword -> rejected */
//        command = "adds " + PersonUtil.getPersonDetails(toAdd);
//        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
//
//        /* Case: invalid name -> rejected */
//        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + MODULE_DESC_PROJECT + DATE_DESC_PROJECT + PRIORITY_DESC_PROJECT;
//        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);
//
//        /* Case: invalid phone -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_PROJECT + INVALID_MODULE_DESC + DATE_DESC_PROJECT + PRIORITY_DESC_PROJECT;
//        assertCommandFailure(command, Module.MESSAGE_CONSTRAINTS);
//
//        /* Case: invalid email -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_PROJECT + MODULE_DESC_PROJECT + INVALID_DATE_DESC + PRIORITY_DESC_PROJECT;
//        assertCommandFailure(command, Date.MESSAGE_CONSTRAINTS);
//
//        /* Case: invalid address -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_PROJECT + MODULE_DESC_PROJECT + DATE_DESC_PROJECT + INVALID_PRIORITY_DESC;
//        assertCommandFailure(command, Priority.MESSAGE_CONSTRAINTS);
//
//        /* Case: invalid tag -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_PROJECT + MODULE_DESC_PROJECT + DATE_DESC_PROJECT + PRIORITY_DESC_PROJECT
//                + INVALID_TAG_DESC;
//        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
//    }
//
//    /**
//     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
//     * 1. Command box displays an empty string.<br>
//     * 2. Command box has the default style class.<br>
//     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
//     * {@code toAdd}.<br>
//     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
//     * the current model added with {@code toAdd}.<br>
//     * 5. Browser url and selected card remain unchanged.<br>
//     * 6. Status bar's sync status changes.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code TaskManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see TaskManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandSuccess(Task toAdd) {
//        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(Task)}. Executes {@code command}
//     * instead.
//     * @see AddCommandSystemTest#assertCommandSuccess(Task)
//     */
//    private void assertCommandSuccess(String command, Task toAdd) {
//        Model expectedModel = getModel();
//        expectedModel.addTask(toAdd);
//        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);
//
//        assertCommandSuccess(command, expectedModel, expectedResultMessage);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Task)} except asserts that
//     * the,<br>
//     * 1. Result display box displays {@code expectedResultMessage}.<br>
//     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
//     * {@code expectedModel}.<br>
//     * @see AddCommandSystemTest#assertCommandSuccess(String, Task)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchangedExceptSyncStatus();
//    }
//
//    /**
//     * Executes {@code command} and asserts that the,<br>
//     * 1. Command box displays {@code command}.<br>
//     * 2. Command box has the error style class.<br>
//     * 3. Result display box displays {@code expectedResultMessage}.<br>
//     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
//     * 5. Browser url, selected card and status bar remain unchanged.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code TaskManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see TaskManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
//}
