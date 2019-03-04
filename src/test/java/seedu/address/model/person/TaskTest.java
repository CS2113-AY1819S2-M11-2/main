package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.address.testutil.TypicalTasks.SLIDES;
import static seedu.address.testutil.TypicalTasks.TUTORIAL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TaskBuilder;

public class TaskTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Task task = new TaskBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        task.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(SLIDES.isSameTask(SLIDES));

        // null -> returns false
        assertFalse(SLIDES.isSameTask(null));

        // different phone and email -> returns false
        Task editedAlice = new TaskBuilder(SLIDES).withModule(VALID_MODULE_TUTORIAL).withDate(VALID_DATE_TUTORIAL).build();
        assertFalse(SLIDES.isSameTask(editedAlice));

        // different name -> returns false
        editedAlice = new TaskBuilder(SLIDES).withName(VALID_NAME_TUTORIAL).build();
        assertFalse(SLIDES.isSameTask(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new TaskBuilder(SLIDES).withDate(VALID_DATE_TUTORIAL).withPriority(VALID_PRIORITY_TUTORIAL)
                .withTags(VALID_TAG_GRADED).build();
        assertTrue(SLIDES.isSameTask(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new TaskBuilder(SLIDES).withModule(VALID_MODULE_TUTORIAL).withPriority(VALID_PRIORITY_TUTORIAL)
                .withTags(VALID_TAG_GRADED).build();
        assertTrue(SLIDES.isSameTask(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new TaskBuilder(SLIDES).withPriority(VALID_PRIORITY_TUTORIAL).withTags(VALID_TAG_GRADED).build();
        assertTrue(SLIDES.isSameTask(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task aliceCopy = new TaskBuilder(SLIDES).build();
        assertTrue(SLIDES.equals(aliceCopy));

        // same object -> returns true
        assertTrue(SLIDES.equals(SLIDES));

        // null -> returns false
        assertFalse(SLIDES.equals(null));

        // different type -> returns false
        assertFalse(SLIDES.equals(5));

        // different task -> returns false
        assertFalse(SLIDES.equals(TUTORIAL));

        // different name -> returns false
        Task editedAlice = new TaskBuilder(SLIDES).withName(VALID_NAME_TUTORIAL).build();
        assertFalse(SLIDES.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new TaskBuilder(SLIDES).withModule(VALID_MODULE_TUTORIAL).build();
        assertFalse(SLIDES.equals(editedAlice));

        // different email -> returns false
        editedAlice = new TaskBuilder(SLIDES).withDate(VALID_DATE_TUTORIAL).build();
        assertFalse(SLIDES.equals(editedAlice));

        // different address -> returns false
        editedAlice = new TaskBuilder(SLIDES).withPriority(VALID_PRIORITY_TUTORIAL).build();
        assertFalse(SLIDES.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TaskBuilder(SLIDES).withTags(VALID_TAG_GRADED).build();
        assertFalse(SLIDES.equals(editedAlice));
    }
}
