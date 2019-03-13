package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Task;
import seedu.address.testutil.TaskBuilder;

public class TaskCardTest extends GuiUnitTest {

    /*
    @Test
    public void display() {
    // no tags
    Task taskWithNoTags = new TaskBuilder().withTags(new String[0]).build();
    TaskCard personCard = new TaskCard(taskWithNoTags, 1);
    uiPartRule.setUiPart(personCard);
    assertCardDisplay(personCard, taskWithNoTags, 1);

    // with tags
    Task taskWithTags = new TaskBuilder().build();
    personCard = new TaskCard(taskWithTags, 2);
    uiPartRule.setUiPart(personCard);
    assertCardDisplay(personCard, taskWithTags, 2);
    }
    */

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 0);

        // same task, same index -> returns true
        TaskCard copy = new TaskCard(task, 0);
        assertTrue(taskCard.equals(copy));

        // same object -> returns true
        assertTrue(taskCard.equals(taskCard));

        // null -> returns false
        assertFalse(taskCard.equals(null));

        // different types -> returns false
        assertFalse(taskCard.equals(0));

        // different task, same index -> returns false
        Task differentTask = new TaskBuilder().withName("differentName").build();
        assertFalse(taskCard.equals(new TaskCard(differentTask, 0)));

        // same task, different index -> returns false
        assertFalse(taskCard.equals(new TaskCard(task, 1)));
    }

    /**
     * Asserts that {@code taskCard} displays the details of {@code expectedTask} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, Task expectedTask, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", personCardHandle.getId());

        // verify task details are displayed correctly
        assertCardDisplaysPerson(expectedTask, personCardHandle);
    }
}
