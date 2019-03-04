package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalTasks.SLIDES;
import static seedu.address.testutil.TypicalTasks.PRESENTATION;
import static seedu.address.testutil.TypicalTasks.SEMINAR;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;

public class JsonTaskManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTaskManagerStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyTaskManager> readAddressBook(String filePath) throws Exception {
        return new JsonTaskManagerStorage(Paths.get(filePath)).readTaskManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("notJsonFormatTaskManager.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidTaskTaskManager.json");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidTaskTaskManager.json");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.json");
        TaskManager original = getTypicalTaskManager();
        JsonTaskManagerStorage jsonAddressBookStorage = new JsonTaskManagerStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveTaskManager(original, filePath);
        ReadOnlyTaskManager readBack = jsonAddressBookStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(PRESENTATION);
        original.removeTask(SLIDES);
        jsonAddressBookStorage.saveTaskManager(original, filePath);
        readBack = jsonAddressBookStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        // Save and read without specifying file path
        original.addTask(SEMINAR);
        jsonAddressBookStorage.saveTaskManager(original); // file path not specified
        readBack = jsonAddressBookStorage.readTaskManager().get(); // file path not specified
        assertEquals(original, new TaskManager(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) {
        try {
            new JsonTaskManagerStorage(Paths.get(filePath))
                    .saveTaskManager(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new TaskManager(), null);
    }
}
