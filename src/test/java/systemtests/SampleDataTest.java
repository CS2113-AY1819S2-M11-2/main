package systemtests;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.address.model.TaskManager;
import seedu.address.model.person.Task;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends TaskManagerSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected TaskManager getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertListMatching(getPersonListPanel(), expectedList);
    }
}
