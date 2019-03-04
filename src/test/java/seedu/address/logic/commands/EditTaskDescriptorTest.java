package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GRADED;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_PROJECT);
        assertTrue(DESC_PROJECT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PROJECT.equals(DESC_PROJECT));

        // null -> returns false
        assertFalse(DESC_PROJECT.equals(null));

        // different types -> returns false
        assertFalse(DESC_PROJECT.equals(5));

        // different values -> returns false
        assertFalse(DESC_PROJECT.equals(DESC_TUTORIAL));

        // different name -> returns false
        EditTaskDescriptor editedAmy;
        editedAmy = new EditTaskDescriptorBuilder(DESC_PROJECT).withName(VALID_NAME_TUTORIAL).build();
        assertFalse(DESC_PROJECT.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_PROJECT).withPhone(VALID_MODULE_TUTORIAL).build();
        assertFalse(DESC_PROJECT.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_PROJECT).withEmail(VALID_DATE_TUTORIAL).build();
        assertFalse(DESC_PROJECT.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_PROJECT).withAddress(VALID_PRIORITY_TUTORIAL).build();
        assertFalse(DESC_PROJECT.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_PROJECT).withTags(VALID_TAG_GRADED).build();
        assertFalse(DESC_PROJECT.equals(editedAmy));
    }
}
