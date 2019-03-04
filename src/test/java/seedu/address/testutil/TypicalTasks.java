package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskManager;
import seedu.address.model.person.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task SLIDES = new TaskBuilder().withName("Slides")
            .withPriority("1").withDate("03-03")
            .withModule("CS2113")
            .withTags("ungraded").build();
    public static final Task LAB = new TaskBuilder().withName("Lab")
            .withPriority("2")
            .withDate("21-01").withModule("CG2023")
            .withTags("graded").build();
    public static final Task LECTURE = new TaskBuilder().withName("LECTURE").withModule("CG2027")
            .withDate("05-03").withPriority("3").build();

    // Manually added
    public static final Task PRESENTATION = new TaskBuilder().withName("Presentation").withModule("CS2101")
            .withDate("03-10").withPriority("3").build();
    public static final Task SEMINAR = new TaskBuilder().withName("Seminar").withModule("PL8001")
            .withDate("20-07").withPriority("3").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task PROJECT = new TaskBuilder().withName(VALID_NAME_PROJECT).withModule(VALID_MODULE_PROJECT)
            .withDate(VALID_DATE_PROJECT).withPriority(VALID_PRIORITY_PROJECT).withTags(VALID_TAG_UNGRADED).build();
    public static final Task TUTORIAL = new TaskBuilder().withName(VALID_NAME_TUTORIAL).withModule(VALID_MODULE_TUTORIAL)
            .withDate(VALID_DATE_TUTORIAL).withPriority(VALID_PRIORITY_TUTORIAL).withTags(VALID_TAG_GRADED, VALID_TAG_UNGRADED)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Lab"; // A keyword that matches LAB

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns an {@code TaskManager} with all the typical tasks.
     */
    public static TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        for (Task task : getTypicalTasks()) {
            tm.addTask(task);
        }
        return tm;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(SLIDES, LAB, LECTURE));
    }
}
