package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Task;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Task taskWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(taskWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, taskWithNoTags, 1);

        // with tags
        Task taskWithTags = new PersonBuilder().build();
        personCard = new PersonCard(taskWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, taskWithTags, 2);
    }

    @Test
    public void equals() {
        Task task = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(task, 0);

        // same task, same index -> returns true
        PersonCard copy = new PersonCard(task, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different task, same index -> returns false
        Task differentTask = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentTask, 0)));

        // same task, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(task, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedTask} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Task expectedTask, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify task details are displayed correctly
        assertCardDisplaysPerson(expectedTask, personCardHandle);
    }
}
