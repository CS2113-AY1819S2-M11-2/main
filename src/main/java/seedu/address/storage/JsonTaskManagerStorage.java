package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyTaskManager;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTaskManagerStorage.class);

    private Path filePath;

    public JsonTaskManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTaskManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException {
        return readTaskManager(filePath);
    }

    /**
     * Similar to {@link #readTaskManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskManager> readTaskManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTaskManager> jsonTaskManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableTaskManager.class);
        if (!jsonTaskManager.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTaskManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, filePath);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyTaskManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTaskManager(ReadOnlyTaskManager taskManager, Path filePath) throws IOException {
        requireNonNull(taskManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskManager(taskManager), filePath);
    }

}
