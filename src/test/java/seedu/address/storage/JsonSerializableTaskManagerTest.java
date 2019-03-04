package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TaskManager;
import seedu.address.testutil.TypicalTasks;

public class JsonSerializableTaskManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTaskManagerTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksTaskManager.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskTaskManager.json");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskTaskManager.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

//    @Test
//    public void toModelType_typicalTasksFile_success() throws Exception {
//        JsonSerializableTaskManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
//                JsonSerializableTaskManager.class).get();
//        TaskManager taskManagerFromFile = dataFromFile.toModelType();
//        TaskManager typicalTasksTaskManager = TypicalTasks.getTypicalTaskManager();
//        assertEquals(taskManagerFromFile, typicalTasksTaskManager);
//    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskManager dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
                JsonSerializableTaskManager.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TASK_FILE,
                JsonSerializableTaskManager.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableTaskManager.MESSAGE_DUPLICATE_TASK);
        dataFromFile.toModelType();
    }

}
