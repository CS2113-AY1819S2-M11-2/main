package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.TaskManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyTaskManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitTaskManager();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTaskManager_success() {
        Model model = new ModelManager(getTypicalTaskManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTaskManager(), new UserPrefs());
        expectedModel.setTaskManager(new TaskManager());
        expectedModel.commitTaskManager();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
