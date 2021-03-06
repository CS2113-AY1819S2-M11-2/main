package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.NICHOLAS;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.User;
import seedu.address.model.task.NameContainsKeywordsPredicate;
import seedu.address.testutil.AccountBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindNameCommand}.
 */
public class FindNameCommandTest {
    private Model model;
    private Model expectedModel = new ModelManager(getTypicalTaskManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        User user = new AccountBuilder(NICHOLAS).build();
        model = new ModelManager(getTypicalTaskManager(), new UserPrefs());
        model.loginUser(user);
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindNameCommand findFirstCommand = new FindNameCommand(firstPredicate);
        FindNameCommand findSecondCommand = new FindNameCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindNameCommand findFirstCommandCopy = new FindNameCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTasksFound() {

        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindNameCommand command = new FindNameCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    /*
    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
    String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
    NameContainsKeywordsPredicate predicate = preparePredicate("Lab Tutorial");
    FindNamenCommand command = new FindNameCommand(predicate);
    expectedModel.updateFilteredTaskList(predicate);
    assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    assertEquals(Arrays.asList(LECTURE), model.getFilteredTaskList());
    }
    */

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
