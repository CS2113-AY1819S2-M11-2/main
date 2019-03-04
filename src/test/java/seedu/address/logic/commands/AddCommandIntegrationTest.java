package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Task;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTaskManager(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.addTask(validTask);
        expectedModel.commitTaskManager();

        assertCommandSuccess(new AddCommand(validTask), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task taskInList = model.getTaskManager().getTaskList().get(0);
        assertCommandFailure(new AddCommand(taskInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_TASK);
    }

}
