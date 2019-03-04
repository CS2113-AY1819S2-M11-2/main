package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TaskManager;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_PROJECT = "Project";
    public static final String VALID_NAME_TUTORIAL = "Tutorial";
    public static final String VALID_MODULE_PROJECT = "CS2113";
    public static final String VALID_MODULE_TUTORIAL = "CG2023";
    public static final String VALID_DATE_PROJECT = "03-03";
    public static final String VALID_DATE_TUTORIAL = "16-02";
    public static final String VALID_PRIORITY_PROJECT = "1";
    public static final String VALID_PRIORITY_TUTORIAL = "2";
    public static final String VALID_TAG_GRADED = "graded";
    public static final String VALID_TAG_UNGRADED = "ungraded";

    public static final String NAME_DESC_PROJECT = " " + PREFIX_NAME + VALID_NAME_PROJECT;
    public static final String NAME_DESC_TUTORIAL = " " + PREFIX_NAME + VALID_NAME_TUTORIAL;
    public static final String MODULE_DESC_PROJECT = " " + PREFIX_MODULE + VALID_MODULE_PROJECT;
    public static final String MODULE_DESC_TUTORIAL = " " + PREFIX_MODULE + VALID_MODULE_TUTORIAL;
    public static final String DATE_DESC_PROJECT = " " + PREFIX_DATE + VALID_DATE_PROJECT;
    public static final String DATE_DESC_TUTORIAL = " " + PREFIX_DATE + VALID_DATE_TUTORIAL;
    public static final String PRIORITY_DESC_PROJECT = " " + PREFIX_PRIORITY + VALID_PRIORITY_PROJECT;
    public static final String PRIORITY_DESC_TUTORIAL = " " + PREFIX_PRIORITY + VALID_PRIORITY_TUTORIAL;
    public static final String TAG_DESC_GRADED = " " + PREFIX_TAG + VALID_TAG_UNGRADED;
    public static final String TAG_DESC_UNGRADED = " " + PREFIX_TAG + VALID_TAG_GRADED;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_MODULE_DESC = " " + PREFIX_MODULE + "CS21a3"; // Module must be in the form AAXXXX
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "01-20"; // Month must between 1 to 12
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY; // empty string not allowed for priority
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTaskDescriptor DESC_PROJECT;
    public static final EditCommand.EditTaskDescriptor DESC_TUTORIAL;

    static {
        DESC_PROJECT = new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECT)
                .withPhone(VALID_MODULE_PROJECT).withEmail(VALID_DATE_PROJECT).withAddress(VALID_PRIORITY_PROJECT)
                .withTags(VALID_TAG_UNGRADED).build();
        DESC_TUTORIAL = new EditTaskDescriptorBuilder().withName(VALID_NAME_TUTORIAL)
                .withPhone(VALID_MODULE_TUTORIAL).withEmail(VALID_DATE_TUTORIAL).withAddress(VALID_PRIORITY_TUTORIAL)
                .withTags(VALID_TAG_GRADED, VALID_TAG_UNGRADED).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered task list and selected task in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TaskManager expectedTaskManager = new TaskManager(actualModel.getTaskManager());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());
        Task expectedSelectedTask = actualModel.getSelectedTask();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTaskManager, actualModel.getTaskManager());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
            assertEquals(expectedSelectedTask, actualModel.getSelectedTask());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s task manager.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s task manager.
     */
    public static void deleteFirstTask(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        model.deleteTask(firstTask);
        model.commitTaskManager();
    }

}
