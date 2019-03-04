package seedu.address.model.person;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.address.testutil.TypicalTasks.SLIDES;
import static seedu.address.testutil.TypicalTasks.TUTORIAL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicateTaskException;
import seedu.address.model.person.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;

public class UniqueTaskListTest {
    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(SLIDES));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueTaskList.add(SLIDES);
        assertTrue(uniqueTaskList.contains(SLIDES));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(SLIDES);
        Task editedAlice = new TaskBuilder(SLIDES).withPriority(VALID_PRIORITY_TUTORIAL).withTags(VALID_TAG_GRADED)
                .build();
        assertTrue(uniqueTaskList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueTaskList.add(SLIDES);
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.add(SLIDES);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTask(null, SLIDES);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTask(SLIDES, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(TaskNotFoundException.class);
        uniqueTaskList.setTask(SLIDES, SLIDES);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueTaskList.add(SLIDES);
        uniqueTaskList.setTask(SLIDES, SLIDES);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(SLIDES);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueTaskList.add(SLIDES);
        Task editedAlice = new TaskBuilder(SLIDES).withPriority(VALID_PRIORITY_TUTORIAL).withTags(VALID_TAG_GRADED)
                .build();
        uniqueTaskList.setTask(SLIDES, editedAlice);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(editedAlice);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueTaskList.add(SLIDES);
        uniqueTaskList.setTask(SLIDES, TUTORIAL);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TUTORIAL);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueTaskList.add(SLIDES);
        uniqueTaskList.add(TUTORIAL);
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.setTask(SLIDES, TUTORIAL);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(TaskNotFoundException.class);
        uniqueTaskList.remove(SLIDES);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueTaskList.add(SLIDES);
        uniqueTaskList.remove(SLIDES);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTasks((UniqueTaskList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueTaskList.add(SLIDES);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TUTORIAL);
        uniqueTaskList.setTasks(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTasks((List<Task>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(SLIDES);
        List<Task> taskList = Collections.singletonList(TUTORIAL);
        uniqueTaskList.setTasks(taskList);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TUTORIAL);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Task> listWithDuplicateTasks = Arrays.asList(SLIDES, SLIDES);
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.setTasks(listWithDuplicateTasks);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asUnmodifiableObservableList().remove(0);
    }
}
