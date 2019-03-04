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
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableTaskManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTaskManagerTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableTaskManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableTaskManager.class).get();
        TaskManager taskManagerFromFile = dataFromFile.toModelType();
        TaskManager typicalPersonsTaskManager = TypicalPersons.getTypicalAddressBook();
        assertEquals(taskManagerFromFile, typicalPersonsTaskManager);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskManager dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableTaskManager.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableTaskManager.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableTaskManager.MESSAGE_DUPLICATE_TASK);
        dataFromFile.toModelType();
    }

}
